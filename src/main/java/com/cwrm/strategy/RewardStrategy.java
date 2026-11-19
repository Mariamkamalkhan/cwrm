package com.cwrm.strategy;

/**
 * RewardStrategy defines the class contract shared by every reward-point
 * calculation rule in the system (Strategy pattern). Callers depend only
 * on this interface, never on a concrete implementation, achieving the
 * Loose Coupling needed to add new rules without ripple effects on
 * existing client code.
 */
public interface RewardStrategy {

    /**
     * Calculates the reward points earned for a recycling submission.
     *
     * Precondition: material is non-null and non-blank; quantity is a
     * positive, finite number.
     * Postcondition: returns a non-negative integer number of points
     * earned for the given material and quantity.
     *
     * @param material the recyclable material type (e.g. "plastic")
     * @param quantity the quantity in kilograms
     * @return the number of reward points earned
     * @throws IllegalArgumentException if a precondition is violated
     */
    int calculatePoints(String material, double quantity);
}