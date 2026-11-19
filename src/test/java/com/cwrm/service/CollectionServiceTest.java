package com.cwrm.service;

import com.cwrm.model.CollectionStatus;
import com.cwrm.model.PickupRequest;
import com.cwrm.model.PickupRequestBuilder;
import com.cwrm.model.Resident;
import com.cwrm.model.WasteCollector;
import com.cwrm.strategy.CampaignRewardStrategy;
import com.cwrm.strategy.MaterialRewardStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CollectionServiceTest {

    private Resident resident;
    private CollectionService service;

    private PickupRequest newRequest(String wasteType) {
        resident = new Resident("RES001", "Test Resident",
                "resident@example.com", "0123456789");
        return new PickupRequestBuilder()
                .setRequestId("REQ001")
                .setResident(resident)
                .setWasteType(wasteType)
                .setPickupAddress("Petaling Jaya")
                .setPreferredDate(LocalDate.now().plusDays(1))
                .build();
    }

    private PickupRequest newAssignedRequest(String wasteType) {
        PickupRequest request = newRequest(wasteType);
        request.assignCollector(new WasteCollector("COL001", "Test Collector",
                "collector@example.com", "0123456788"));
        return request;
    }

    // Test 1 - assertAll: one completion updates status, return value and balance
    @Test
    void completeCollection_assignedPlasticRequest_completesAndCreditsResident() {
        service = new CollectionService(new MaterialRewardStrategy());
        PickupRequest request = newAssignedRequest("plastic");

        int points = service.completeCollection(request, 2.0);

        assertAll("completed collection",
                () -> assertEquals(6, points),
                () -> assertEquals(6, resident.getRewardPoints()),
                () -> assertEquals(CollectionStatus.COMPLETED, request.getStatus())
        );
    }

    // Test 2 - @ParameterizedTest: campaign rule applied through the service
    @ParameterizedTest
    @CsvSource({
            "1.0, 10",
            "2.5, 25",
            "4.0, 40"
    })
    void completeCollection_campaignStrategy_creditsCampaignPoints(
            double weightKg, int expectedPoints) {
        service = new CollectionService(new CampaignRewardStrategy(10));
        PickupRequest request = newAssignedRequest("paper");

        service.completeCollection(request, weightKg);

        assertEquals(expectedPoints, resident.getRewardPoints());
    }

    // Test 3 - swapping the strategy at runtime changes the rule used
    @Test
    void setRewardStrategy_campaignStarts_appliesNewRule() {
        service = new CollectionService(new MaterialRewardStrategy());
        service.setRewardStrategy(new CampaignRewardStrategy(10));
        PickupRequest request = newAssignedRequest("plastic");

        assertEquals(20, service.completeCollection(request, 2.0));
    }

    // Test 4 - a request that was never assigned cannot be completed
    @Test
    void completeCollection_requestNotAssigned_throwsIllegalStateException() {
        service = new CollectionService(new MaterialRewardStrategy());
        PickupRequest request = newRequest("plastic");

        assertThrows(IllegalStateException.class,
                () -> service.completeCollection(request, 2.0));
        assertEquals(0, resident.getRewardPoints());
    }

    // Test 5 - completing twice is rejected, preventing double rewards
    @Test
    void completeCollection_alreadyCompleted_throwsIllegalStateException() {
        service = new CollectionService(new MaterialRewardStrategy());
        PickupRequest request = newAssignedRequest("plastic");
        service.completeCollection(request, 2.0);

        assertThrows(IllegalStateException.class,
                () -> service.completeCollection(request, 2.0));
        assertEquals(6, resident.getRewardPoints());
    }

    // Test 6 - an invalid weight leaves the request untouched
    @Test
    void completeCollection_negativeWeight_leavesRequestAssigned() {
        service = new CollectionService(new MaterialRewardStrategy());
        PickupRequest request = newAssignedRequest("plastic");

        assertThrows(IllegalArgumentException.class,
                () -> service.completeCollection(request, -1.0));
        assertEquals(CollectionStatus.ASSIGNED, request.getStatus());
    }

    // Test 7 - a zero-point collection still completes without error
    @Test
    void completeCollection_zeroPointCollection_completesWithBalanceUnchanged() {
        service = new CollectionService(new MaterialRewardStrategy());
        PickupRequest request = newAssignedRequest("paper");

        int points = service.completeCollection(request, 0.3);

        assertEquals(0, points);
        assertEquals(0, resident.getRewardPoints());
        assertEquals(CollectionStatus.COMPLETED, request.getStatus());
    }

    // Test 8 - the service rejects a missing request at its boundary
    @Test
    void completeCollection_nullRequest_throwsIllegalArgumentException() {
        service = new CollectionService(new MaterialRewardStrategy());

        assertThrows(IllegalArgumentException.class,
                () -> service.completeCollection(null, 2.0));
    }

    // Test 9 - the service can never be created without a reward rule
    @Test
    void constructor_nullStrategy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CollectionService(null));
    }
}