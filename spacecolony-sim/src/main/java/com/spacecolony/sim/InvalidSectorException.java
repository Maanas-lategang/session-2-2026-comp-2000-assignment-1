package com.spacecolony.sim;

/**
 * Thrown when a unit attempts to occupy or move to a sector that is out
 * of bounds or already occupied.
 */
public class InvalidSectorException extends ColonyException {

    private final Position attempted;

    public InvalidSectorException(Position attempted) {
        super("Invalid or occupied sector: " + attempted);
        this.attempted = attempted;
    }

    public Position getAttempted() {
        return attempted;
    }
}
