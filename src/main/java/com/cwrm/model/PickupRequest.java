package com.cwrm.model;

import java.time.LocalDate;

/**
 * Represents a recycling or bulky-waste pickup request.
 * Instances are constructed via {@link PickupRequestBuilder}.
 */
public class PickupRequest {

    private final String requestId;
    private final Resident resident;
    private final String wasteType;
    private final String description;
    private final String pickupAddress;
    private final LocalDate preferredDate;
    private CollectionStatus status;
    private WasteCollector assignedCollector;

    /**
     * Creates a pickup request using the supplied builder.
     *
     * @param builder builder containing request information
     */
    PickupRequest(PickupRequestBuilder builder) {
        this.requestId = builder.getRequestId();
        this.resident = builder.getResident();
        this.wasteType = builder.getWasteType();
        this.description = builder.getDescription();
        this.pickupAddress = builder.getPickupAddress();
        this.preferredDate = builder.getPreferredDate();
        this.status = CollectionStatus.PENDING;
        this.assignedCollector = null;
    }

    public String getRequestId() {
        return requestId;
    }

    public Resident getResident() {
        return resident;
    }

    public String getWasteType() {
        return wasteType;
    }

    public String getDescription() {
        return description;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public WasteCollector getAssignedCollector() {
        return assignedCollector;
    }

    public void assignCollector(WasteCollector collector) {
        if (collector == null) {
            throw new IllegalArgumentException("Collector cannot be null.");
        }
        this.assignedCollector = collector;
        this.status = CollectionStatus.ASSIGNED;
    }

    public void markCompleted() {
        if (status != CollectionStatus.ASSIGNED) {
            throw new IllegalStateException("Only assigned requests can be completed.");
        }
        this.status = CollectionStatus.COMPLETED;
    }

    public void cancelRequest() {
        if (status == CollectionStatus.COMPLETED) {
            throw new IllegalStateException("Completed requests cannot be cancelled.");
        }
        this.status = CollectionStatus.CANCELLED;
    }
}
