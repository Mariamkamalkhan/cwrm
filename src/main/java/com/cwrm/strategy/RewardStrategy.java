package com.cwrm.strategy;

/**
 * Strategy interface for calculating recycling reward points.
 * Implementations define different rules for how points are awarded
 * (e.g. by material type, or by a time-limited campaign bonus).
 */
public interface RewardStrategy {

    /**
     * Calculates the reward points earned for a recycling submission.
     *
     * @param material the recyclable material type (e.g. "plastic")
     * @param quantity the quantity in kilograms
     * @return the number of reward points earned
     * @throws IllegalArgumentException if material is null/blank or
     *         quantity is not a positive finite number
     */
    int calculatePoints(String material, double quantity);
}
