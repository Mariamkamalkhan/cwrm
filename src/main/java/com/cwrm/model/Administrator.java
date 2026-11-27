package com.cwrm.model;

/**
 * Represents a council administrator who monitors collection activities.
 */
public class Administrator extends User {

    /**
     * Creates an administrator account.
     *
     * @param userId the administrator's unique identifier
     * @param fullName the administrator's full name
     * @param email the administrator's email address
     * @param phoneNumber the administrator's contact number
     */
    public Administrator(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
    }
}
