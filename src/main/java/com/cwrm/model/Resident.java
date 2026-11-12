package com.cwrm.model;

/**
 * A resident who can submit pickup requests and earn reward points.
 */
public class Resident extends User {

    private int rewardPoints;

    public Resident(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
        this.rewardPoints = 0;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void addRewardPoints(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points must be positive.");
        }
        rewardPoints += points;
    }
}
