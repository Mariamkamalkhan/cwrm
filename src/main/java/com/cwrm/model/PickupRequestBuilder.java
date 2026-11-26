package com.cwrm.model;

import java.time.LocalDate;

/**
 * Builder for creating validated PickupRequest objects.
 */
public class PickupRequestBuilder {
    private String requestId;
    private Resident resident;
    private String wasteType;
    private String description;
    private String pickupAddress;
    private LocalDate preferredDate;

    /**
     * Sets the request ID.
     *
     * @param requestId the unique request identifier
     * @return this builder
     */
    public PickupRequestBuilder setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * Sets the resident who submitted the request.
     *
     * @param resident the request's resident
     * @return this builder
     */
    public PickupRequestBuilder setResident(Resident resident) {
        this.resident = resident;
        return this;
    }

    /**
     * Sets the requested waste type.
     *
     * @param wasteType the waste type
     * @return this builder
     */
    public PickupRequestBuilder setWasteType(String wasteType) {
        this.wasteType = wasteType;
        return this;
    }

    /**
     * Sets an optional description.
     *
     * @param description additional request details
     * @return this builder
     */
    public PickupRequestBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the pickup address.
     *
     * @param pickupAddress the collection address
     * @return this builder
     */
    public PickupRequestBuilder setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
        return this;
    }

    /**
     * Sets the preferred pickup date.
     *
     * @param preferredDate the preferred date
     * @return this builder
     */
    public PickupRequestBuilder setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
        return this;
    }

    /**
     * Builds a pickup request after validating its required fields.
     *
     * @return the validated pickup request
     * @throws IllegalArgumentException if a required field is invalid
     */
    public PickupRequest build() {
        requireText(requestId, "Request ID");
        if (resident == null) {
            throw new IllegalArgumentException("Resident is required.");
        }
        requireText(wasteType, "Waste type");
        requireText(pickupAddress, "Pickup address");
        validatePreferredDate();
        return new PickupRequest(this);
    }

    /**
     * Checks that a required text field contains non-blank text.
     *
     * @param value the value to check
     * @param fieldName the field's display name
     * @throws IllegalArgumentException if the value is null or blank
     */
    private static void requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }

    /**
     * Checks that the preferred pickup date is present and not in the past.
     *
     * @throws IllegalArgumentException if the date is missing or in the past
     */
    private void validatePreferredDate() {
        if (preferredDate == null) {
            throw new IllegalArgumentException("Preferred date is required.");
        }
        if (preferredDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Preferred date cannot be in the past.");
        }
    }

    // Package-private accessors used by PickupRequest's constructor.

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
