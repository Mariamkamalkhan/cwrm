package com.cwrm.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RewardStrategyTest {

    // Test 1 - @ParameterizedTest: material-to-points mapping
    @ParameterizedTest
    @CsvSource({
        "paper, 2.0, 4",
        "plastic, 2.0, 6",
        "aluminium, 2.0, 10",
        "glass, 2.0, 4",
        "unknown, 2.0, 2"
    })
    void calculatePoints_variousMaterials_returnsExpectedPoints(
            String material, double quantity, int expected) {
        RewardStrategy strategy = new MaterialRewardStrategy();
        assertEquals(expected, strategy.calculatePoints(material, quantity));
    }

    // Test 2 - @ParameterizedTest: case-insensitivity and whitespace handling
    @ParameterizedTest
    @CsvSource({
        "PLASTIC, 3",
        "' Plastic ', 3",
        "plastic, 3"
    })
    void calculatePoints_materialCaseAndSpacing_isNormalised(
            String material, int pointsPerKg) {
        RewardStrategy strategy = new MaterialRewardStrategy();
        assertEquals(pointsPerKg, strategy.calculatePoints(material, 1.0));
    }

    // Test 3 - @ParameterizedTest: campaign strategy scales with bonus rate
    @ParameterizedTest
    @CsvSource({
        "5, 2.0, 10",
        "10, 2.0, 20",
        "0, 2.0, 0"
    })
    void calculatePoints_campaignBonusRates_returnsScaledPoints(
            int bonusPerKg, double quantity, int expected) {
        RewardStrategy strategy = new CampaignRewardStrategy(bonusPerKg);
        assertEquals(expected, strategy.calculatePoints("plastic", quantity));
    }

    // Test 4 - assertThrows (group's 2nd, Member 1 already has 1): invalid quantity
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, Double.NaN, Double.POSITIVE_INFINITY})
    void calculatePoints_invalidQuantity_throwsIllegalArgumentException(double badQuantity) {
        RewardStrategy strategy = new MaterialRewardStrategy();
        assertThrows(IllegalArgumentException.class,
            () -> strategy.calculatePoints("plastic", badQuantity));
    }

    // Test 5 - constructor validation
    @Test
    void campaignRewardStrategy_negativeBonus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new CampaignRewardStrategy(-5));
    }

    // Test 6 - assertAll: multiple independent checks in one test
    @Test
    void calculatePoints_acrossMaterials_allMatchExpectedIndependently() {
        RewardStrategy strategy = new MaterialRewardStrategy();
        assertAll("material point calculations",
            () -> assertEquals(4, strategy.calculatePoints("paper", 2.0)),
            () -> assertEquals(6, strategy.calculatePoints("plastic", 2.0)),
            () -> assertEquals(10, strategy.calculatePoints("aluminium", 2.0)),
            () -> assertThrows(IllegalArgumentException.class,
                    () -> strategy.calculatePoints("", 2.0))
        );
    }

    // Test 7 - DELIBERATELY FAILING: exposes a real rounding limitation.
    // Math.floor() truncates small quantities to zero points (e.g. 0.3kg of
    // paper = floor(0.6) = 0), which can discourage residents from reporting
    // small recycling amounts. Kept failing on purpose to document this
    // known limitation; a future version should apply a minimum of 1 point.
    @Test
    void calculatePoints_smallPaperQuantity_awardsAtLeastOnePoint() {
        RewardStrategy strategy = new MaterialRewardStrategy();
        int points = strategy.calculatePoints("paper", 0.3);
        assertTrue(points >= 1,
                "A resident who recycles any positive quantity should earn at least 1 point");
    }

    // Test 8 - CampaignRewardStrategy enforces the same barricade as
    // MaterialRewardStrategy, so the class contract holds for every
    // RewardStrategy implementation, not just one.
    @Test
    void calculatePoints_campaignStrategyWithBlankMaterial_throwsIllegalArgumentException() {
        RewardStrategy strategy = new CampaignRewardStrategy(5);
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculatePoints("   ", 2.0));
    }
}