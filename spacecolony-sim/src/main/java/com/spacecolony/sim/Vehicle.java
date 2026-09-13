package com.spacecolony.sim;

/**
 * Base type for mobile units that consume fuel, navigate the grid via a
 * {@link NavigationStrategy}, and can deploy new vehicles once they have
 * enough fuel banked.
 */
public abstract class Vehicle extends Unit {

    private int fuel;
    private final int upgradeThreshold;
    private final int maxCycles;
    private final NavigationStrategy navigationStrategy;

    protected Vehicle(Position position, int fuel, int upgradeThreshold, int maxCycles,
                       NavigationStrategy navigationStrategy) {
        super(position);
        this.fuel = fuel;
        this.upgradeThreshold = upgradeThreshold;
        this.maxCycles = maxCycles;
        this.navigationStrategy = navigationStrategy;
    }

    @Override
    public final void act(Colony world) {
        incrementCycles();
        fuel--;

        if (fuel <= 0 || getCycles() > maxCycles) {
            deactivate();
            return;
        }

        Position target = navigationStrategy.nextMove(this, world);
        if (target != null) {
            try {
                world.moveUnit(this, target);
            } catch (InvalidSectorException e) {
                // Target sector is blocked or out of bounds; hold position this cycle.
            }
        }

        scavenge(world);

        if (fuel >= upgradeThreshold) {
            tryUpgrade(world);
        }
    }

    protected abstract void scavenge(Colony world);

    protected abstract void tryUpgrade(Colony world);

    public int getFuel() {
        return fuel;
    }

    protected void addFuel(int amount) {
        this.fuel += amount;
    }

    protected void spendFuel(int amount) {
        this.fuel = Math.max(0, this.fuel - amount);
    }

    @Override
    public int getFuelValue() {
        return fuel / 2;
    }

    protected int getUpgradeThreshold() {
        return upgradeThreshold;
    }
}
