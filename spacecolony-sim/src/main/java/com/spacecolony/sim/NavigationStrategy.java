package com.spacecolony.sim;

/**
 * Pluggable movement behaviour for a {@link Vehicle}.
 */
public interface NavigationStrategy {

    Position nextMove(Vehicle self, Colony world);
}
