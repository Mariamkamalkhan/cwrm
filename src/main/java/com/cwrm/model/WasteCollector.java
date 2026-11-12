package com.cwrm.model;

/**
 * A waste collector who is assigned pickup requests and records
 * completed collections.
 */
public class WasteCollector extends User {

    public WasteCollector(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
    }
}
