package com.cwrm.strategy;

/**
 * Calculates reward points using a recycling campaign bonus rate.
 * Allows new campaigns to be introduced without modifying any other
 * reward strategy (Open/Closed Principle): this class is a second,
 * interchangeable implementation of the RewardStrategy class contract,
 * added without a single line of MaterialRewardStrategy being touched.
 */
public class CampaignRewardStrategy implements RewardStrategy {

    private final int bonusPointsPerKg;

    /**
     * Creates a campaign strategy with a fixed bonus rate.
     *
     * Precondition: bonusPointsPerKg is zero or positive.
     * Postcondition: a valid, immutable CampaignRewardStrategy exists;
     * the object can never be observed in an invalid state.
     *
     * @param bonusPointsPerKg the bonus points awarded per kilogram
     * @throws IllegalArgumentException if bonusPointsPerKg is negative
     */
    public CampaignRewardStrategy(int bonusPointsPerKg) {
        if (bonusPointsPerKg < 0) {
            throw new IllegalArgumentException("Bonus points cannot be negative.");
        }
        this.bonusPointsPerKg = bonusPointsPerKg;
    }

    /**
     * Cyclomatic complexity: 1 (no decision points; validation is
     * delegated to the barricade routines below).
     */
    @Override
    public int calculatePoints(String material, double quantity) {
        // Barricade: sanitise all incoming data at the public boundary,
        // the same contract enforced by MaterialRewardStrategy, so every
        // RewardStrategy implementation behaves consistently for callers.
        validateMaterial(material);
        validateQuantity(quantity);
        return (int) Math.floor(bonusPointsPerKg * quantity);
    }

    /**
     * Part of the class's Barricade: rejects a null or blank material
     * before any calculation is attempted. Cyclomatic complexity: 2.
     */
    private void validateMaterial(String material) {
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Material cannot be empty.");
        }
    }

    /**
     * Part of the class's Barricade: rejects a non-positive or
     * non-finite quantity before any calculation is attempted.
     * Cyclomatic complexity: 2.
     */
    private void validateQuantity(double quantity) {
        if (quantity <= 0 || Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }
    }
}
