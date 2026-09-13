package com.spacecolony.sim;

/**
 * Anything that can be scavenged by a vehicle for fuel.
 */
public interface Refuelable {

    int getFuelValue();

    void markDepleted();
}
