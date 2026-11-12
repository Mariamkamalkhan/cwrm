package com.cwrm.strategy;

/**
 * Calculates reward points according to the type of recyclable material.
 * Each material has a fixed points-per-kilogram rate; unrecognised
 * materials fall back to a minimum default rate.
 */
public class MaterialRewardStrategy implements RewardStrategy {

    private static final int PAPER_POINTS_PER_KG = 2;
    private static final int PLASTIC_POINTS_PER_KG = 3;
    private static final int ALUMINIUM_POINTS_PER_KG = 5;
    private static final int GLASS_POINTS_PER_KG = 2;
    private static final int DEFAULT_POINTS_PER_KG = 1;

    @Override
    public int calculatePoints(String material, double quantity) {
        validateMaterial(material);
        validateQuantity(quantity);

        int pointsPerKg = pointsPerKgFor(material.trim().toLowerCase());
        return (int) Math.floor(pointsPerKg * quantity);
    }

    /**
     * Resolves the points-per-kilogram rate for a normalised material name.
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

    private void validateMaterial(String material) {
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Material cannot be empty.");
        }
    }

    private void validateQuantity(double quantity) {
        if (quantity <= 0 || Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }
    }
}
