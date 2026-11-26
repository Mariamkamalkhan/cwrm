package com.cwrm.model;

import java.time.LocalDate;

/**
 * Represents a recycling or bulky-waste pickup request.
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
     * @param builder builder containing validated request information
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

    /**
     * Returns the request's unique identifier.
     *
     * @return the request ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Returns the resident who submitted the request.
     *
     * @return the resident
     */
    public Resident getResident() {
        return resident;
    }

    /**
     * Returns the requested waste type.
     *
     * @return the waste type
     */
    public String getWasteType() {
        return wasteType;
    }

    /**
     * Returns the optional request description.
     *
     * @return the description, or null if not provided
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the pickup address.
     *
     * @return the pickup address
     */
    public String getPickupAddress() {
        return pickupAddress;
    }

    /**
     * Returns the preferred pickup date.
     *
     * @return the preferred date
     */
    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    /**
     * Returns the current collection status.
     *
     * @return the request status
     */
    public CollectionStatus getStatus() {
        return status;
    }

    /**
     * Returns the collector assigned to this request.
     *
     * @return the assigned collector, or null if unassigned
     */
    public WasteCollector getAssignedCollector() {
        return assignedCollector;
    }

    /**
     * Assigns a collector and changes the request status to ASSIGNED.
     *
     * @param collector the collector to assign
     * @throws IllegalArgumentException if collector is null
     */
    public void assignCollector(WasteCollector collector) {
        if (collector == null) {
            throw new IllegalArgumentException("Collector cannot be null.");
        }
        this.assignedCollector = collector;
        this.status = CollectionStatus.ASSIGNED;
    }

    /**
     * Marks an assigned request as completed.
     *
     * @throws IllegalStateException if the request is not assigned
     */
    public void markCompleted() {
        if (status != CollectionStatus.ASSIGNED) {
            throw new IllegalStateException(
                    "Only assigned requests can be completed.");
        }
        this.status = CollectionStatus.COMPLETED;
    }

    /**
     * Cancels a request unless it has already been completed.
     *
     * @throws IllegalStateException if the request is completed
     */
    public void cancelRequest() {
        if (status == CollectionStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Completed requests cannot be cancelled.");
        }
        this.status = CollectionStatus.CANCELLED;
    }
}
