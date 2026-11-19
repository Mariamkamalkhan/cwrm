package com.cwrm.service;

import com.cwrm.model.PickupRequest;
import com.cwrm.model.Resident;
import com.cwrm.strategy.RewardStrategy;

/**
 * CollectionService holds the core business rule of CWRM: when a collector
 * logs a completed pickup, the resident who booked it earns reward points.
 *
 * Single Responsibility: this class only coordinates the collection
 * workflow. It does not build requests (PickupRequestBuilder), calculate
 * points (RewardStrategy) or store a point balance (Resident).
 *
 * The RewardStrategy is supplied through the constructor (Dependency
 * Injection), so the service is never in an invalid, uninitialised state
 * and the active reward rule can be swapped without editing this class.
 */
public class CollectionService {

    private RewardStrategy rewardStrategy;

    /**
     * Creates a collection service that awards points with the given rule.
     *
     * Precondition: rewardStrategy is not null.
     * Postcondition: the service is ready to process completed collections.
     *
     * @param rewardStrategy the reward rule used to calculate points
     * @throws IllegalArgumentException if rewardStrategy is null
     */
    public CollectionService(RewardStrategy rewardStrategy) {
        this.rewardStrategy = requireStrategy(rewardStrategy);
    }

    /**
     * Switches the active reward rule at runtime, for example when a
     * recycling campaign starts or ends.
     *
     * @param rewardStrategy the new reward rule to apply
     * @throws IllegalArgumentException if rewardStrategy is null
     */
    public void setRewardStrategy(RewardStrategy rewardStrategy) {
        this.rewardStrategy = requireStrategy(rewardStrategy);
    }

    /**
     * Records a completed collection and credits the resident's points.
     *
     * Precondition: request is not null and has been assigned to a
     * collector; weightKg is a positive, finite number.
     * Postcondition: the request is COMPLETED and the resident's balance
     * has increased by the returned number of points. If a precondition
     * fails, nothing changes: points are calculated (and weightKg checked)
     * before the request's status is updated.
     *
     * Cyclomatic complexity: 2 (1 + one if).
     *
     * @param request  the assigned pickup request being completed
     * @param weightKg the collected weight in kilograms
     * @return the number of reward points awarded (may be 0)
     * @throws IllegalArgumentException if request is null or weightKg is invalid
     * @throws IllegalStateException    if the request is not currently assigned
     */
    public int completeCollection(PickupRequest request, double weightKg) {
        if (request == null) {
            throw new IllegalArgumentException("Pickup request cannot be null.");
        }
        int points = rewardStrategy.calculatePoints(request.getWasteType(), weightKg);
        request.markCompleted();
        creditResident(request.getResident(), points);
        return points;
    }

    /**
     * Adds points to the resident's balance. A collection worth zero points
     * leaves the balance unchanged, because Resident only accepts positive
     * amounts. Cyclomatic complexity: 2 (1 + one if).
     */
    private void creditResident(Resident resident, int points) {
        if (points > 0) {
            resident.addRewardPoints(points);
        }
    }

    /**
     * Barricade for the injected dependency. Cyclomatic complexity: 2.
     */
    private RewardStrategy requireStrategy(RewardStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Reward strategy cannot be null.");
        }
        return strategy;
    }
}