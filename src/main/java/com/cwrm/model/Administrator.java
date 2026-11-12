package com.cwrm.model;

/**
 * A council administrator who monitors collection activity and records.
 */
public class Administrator extends User {

    public Administrator(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
    }
}
