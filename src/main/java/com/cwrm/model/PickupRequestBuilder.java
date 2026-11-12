package com.cwrm.model;

import java.time.LocalDate;

/**
 * Builder for creating {@link PickupRequest} objects. Separates the
 * construction of a pickup request (mandatory + optional fields) from
 * its representation.
 */
public class PickupRequestBuilder {

    private String requestId;
    private Resident resident;
    private String wasteType;
    private String description;
    private String pickupAddress;
    private LocalDate preferredDate;

    public PickupRequestBuilder setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public PickupRequestBuilder setResident(Resident resident) {
        this.resident = resident;
        return this;
    }

    public PickupRequestBuilder setWasteType(String wasteType) {
        this.wasteType = wasteType;
        return this;
    }

    public PickupRequestBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public PickupRequestBuilder setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
        return this;
    }

    public PickupRequestBuilder setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
        return this;
    }

    public PickupRequest build() {
        if (requestId == null || requestId.isBlank()) {
            throw new IllegalArgumentException("Request ID is required.");
        }
        if (resident == null) {
            throw new IllegalArgumentException("Resident is required.");
        }
        if (wasteType == null || wasteType.isBlank()) {
            throw new IllegalArgumentException("Waste type is required.");
        }
        if (pickupAddress == null || pickupAddress.isBlank()) {
            throw new IllegalArgumentException("Pickup address is required.");
        }
        if (preferredDate == null) {
            throw new IllegalArgumentException("Preferred date is required.");
        }
        if (preferredDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Preferred date cannot be in the past.");
        }
        return new PickupRequest(this);
    }

    // Package-private getters used by PickupRequest constructor
    String getRequestId() {
        return requestId;
    }

    Resident getResident() {
        return resident;
    }

    String getWasteType() {
        return wasteType;
    }

    String getDescription() {
        return description;
    }

    String getPickupAddress() {
        return pickupAddress;
    }

    LocalDate getPreferredDate() {
        return preferredDate;
    }
}
