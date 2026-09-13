package com.spacecolony.sim;

/**
 * Thrown when the colony's unit count grows beyond what the simulation
 * is willing to sustain.
 */
public class ResourceExhaustionException extends ColonyException {

    public ResourceExhaustionException(int unitCount) {
        super("Colony exceeded sustainable unit count: " + unitCount);
    }
}
