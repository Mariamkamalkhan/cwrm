package com.cwrm.model;

/**
 * Represents the current status of a waste collection request.
 */
public enum CollectionStatus {
    /** Request has been submitted and is awaiting assignment. */
    PENDING,
    /** Request has been assigned to a waste collector. */
    ASSIGNED,
    /** Collection has been completed. */
    COMPLETED,
    /** Request has been cancelled. */
    CANCELLED
}
