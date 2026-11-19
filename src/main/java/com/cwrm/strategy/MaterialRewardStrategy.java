package com.cwrm.strategy;

/**
 * Calculates reward points according to the type of recyclable material.
 * Each material has a fixed points-per-kilogram rate; unrecognised
 * materials fall back to a minimum default rate.
 *
 * All rates are named constants rather than magic numbers, keeping the
 * routine self-documenting and its decision logic the single location
 * to change if a rate is revised (Open/Closed Principle: new rates or
 * materials are added without modifying calculatePoints itself).
 */
public class MaterialRewardStrategy implements RewardStrategy {

    private static final int PAPER_POINTS_PER_KG = 2;
    private static final int PLASTIC_POINTS_PER_KG = 3;
    private static final int ALUMINIUM_POINTS_PER_KG = 5;
    private static final int GLASS_POINTS_PER_KG = 2;
    private static final int DEFAULT_POINTS_PER_KG = 1;

    /**
     * Cyclomatic complexity: 1 (no decision points in this routine body;
     * validation and lookup are delegated to their own single-decision
     * routines below, keeping each piece intellectually manageable).
     */
    @Override
    public int calculatePoints(String material, double quantity) {
        // Barricade: sanitise all incoming data at the public boundary so
        // the private routines below may assume the data is already clean.
        validateMaterial(material);
        validateQuantity(quantity);

        int pointsPerKg = pointsPerKgFor(material.trim().toLowerCase());
        return (int) Math.floor(pointsPerKg * quantity);
    }

    /**
     * Resolves the points-per-kilogram rate for a normalised material name.
     *
     * Cyclomatic complexity: 5 (one decision point per case label, plus
     * the implicit default), well under the threshold of 10 at which a
     * routine should be split further.
     *
     * @param normalisedMaterial the material name, already trimmed and
     *        lower-cased
     * @return the points-per-kilogram rate for that material
     */
    private int pointsPerKgFor(String normalisedMaterial) {
        switch (normalisedMaterial) {
            case "paper":
                return PAPER_POINTS_PER_KG;
            case "plastic":
                return PLASTIC_POINTS_PER_KG;
            case "aluminium":
                return ALUMINIUM_POINTS_PER_KG;
            case "glass":
                return GLASS_POINTS_PER_KG;
            default:
                return DEFAULT_POINTS_PER_KG;
        }
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