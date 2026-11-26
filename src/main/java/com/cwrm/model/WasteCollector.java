package com.cwrm.model;

/**
 * Represents a waste collector who handles assigned pickup requests.
 */
public class WasteCollector extends User {

    /**
     * Creates a waste-collector account.
     *
     * @param userId the collector's unique identifier
     * @param fullName the collector's full name
     * @param email the collector's email address
     * @param phoneNumber the collector's contact number
     */
    public WasteCollector(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
    }
}
