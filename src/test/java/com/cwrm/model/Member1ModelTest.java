package com.cwrm.model;

import com.cwrm.strategy.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Member1ModelTest {

    private Resident createResident() {
        return new Resident(
            "RES001",
            "Test Resident",
            "resident@example.com",
            "0123456789"
        );
    }

    // Test 1: Resident can earn reward points
    @Test
    void residentShouldEarnRewardPoints() {
        Resident resident = createResident();
        resident.addRewardPoints(10);
        assertEquals(10, resident.getRewardPoints());
    }

    // Test 2: Material strategy calculates points correctly
    @Test
    void materialStrategyShouldCalculatePoints() {
        RewardStrategy strategy = new MaterialRewardStrategy();
        int points = strategy.calculatePoints("Plastic", 2.0);
        assertEquals(6, points);
    }

    // Test 3: Campaign strategy calculates bonus points correctly
    @Test
    void campaignStrategyShouldCalculateBonusPoints() {
        RewardStrategy strategy = new CampaignRewardStrategy(10);
        int points = strategy.calculatePoints("Plastic", 2.0);
        assertEquals(20, points);
    }

    // Test 4: Builder creates a valid pickup request
    @Test
    void builderShouldCreateValidPickupRequest() {
        Resident resident = createResident();
        PickupRequest request = new PickupRequestBuilder()
            .setRequestId("REQ001")
            .setResident(resident)
            .setWasteType("Plastic")
            .setPickupAddress("Petaling Jaya")
            .setPreferredDate(LocalDate.now().plusDays(1))
            .setDescription("Plastic bottles")
            .build();

        assertEquals("REQ001", request.getRequestId());
        assertEquals(resident, request.getResident());
        assertEquals("Plastic", request.getWasteType());
        assertEquals(CollectionStatus.PENDING, request.getStatus());
    }

    // Test 5: Builder rejects a missing required field
    @Test
    void builderShouldRejectMissingRequestId() {
        Resident resident = createResident();
        PickupRequestBuilder builder = new PickupRequestBuilder()
            .setResident(resident)
            .setWasteType("Plastic")
            .setPickupAddress("Petaling Jaya")
            .setPreferredDate(LocalDate.now().plusDays(1));

        assertThrows(
            IllegalArgumentException.class,
            builder::build
        );
    }

    // Test 6: Assigning a collector changes request status
    @Test
    void assigningCollectorShouldChangeRequestStatus() {
        Resident resident = createResident();
        WasteCollector collector = new WasteCollector(
            "COL001",
            "Test Collector",
            "collector@example.com",
            "0123456788"
        );
        PickupRequest request = new PickupRequestBuilder()
            .setRequestId("REQ002")
            .setResident(resident)
            .setWasteType("Paper")
            .setPickupAddress("Petaling Jaya")
            .setPreferredDate(LocalDate.now().plusDays(1))
            .build();

        request.assignCollector(collector);
        assertEquals(CollectionStatus.ASSIGNED, request.getStatus());
        assertEquals(collector, request.getAssignedCollector());
    }
}
