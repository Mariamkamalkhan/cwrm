package com.cwrm.model;

/**
 * Represents a resident who can submit pickup requests and earn reward points.
 */
public class Resident extends User {
    private int rewardPoints;

    /**
     * Creates a resident account with an initial reward balance of zero.
     *
     * @param userId the resident's unique identifier
     * @param fullName the resident's full name
     * @param email the resident's email address
     * @param phoneNumber the resident's contact number
     */
    public Resident(String userId, String fullName, String email, String phoneNumber) {
        super(userId, fullName, email, phoneNumber);
        this.rewardPoints = 0;
    }

    /**
     * Returns the resident's current reward-point balance.
     *
     * @return the reward-point balance
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Adds positive reward points to the resident's balance.
     *
     * @param points the number of points to add
     * @throws IllegalArgumentException if points is not positive
     */
    public void addRewardPoints(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points must be positive.");
        }
        rewardPoints += points;
    }
}
