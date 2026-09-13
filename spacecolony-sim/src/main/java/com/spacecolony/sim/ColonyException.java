package com.spacecolony.sim;

/**
 * Root of the checked exception hierarchy for simulation-level failures.
 */
public abstract class ColonyException extends Exception {

    protected ColonyException(String message) {
        super(message);
    }
}
