package com.cwrm.model;

/**
 * Represents a user of the Community Waste and Recycling Manager.
 */
public abstract class User {
    private final String userId;
    private String fullName;
    private String email;
    private String phoneNumber;

    /**
     * Creates a user account.
     *
     * @param userId the user's unique identifier
     * @param fullName the user's full name
     * @param email the user's email address
     * @param phoneNumber the user's contact number
     */
    public User(String userId, String fullName, String email, String phoneNumber) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the user's unique identifier.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the user's full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Updates the user's full name.
     *
     * @param fullName the new full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the user's email address.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's contact number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the user's contact number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
