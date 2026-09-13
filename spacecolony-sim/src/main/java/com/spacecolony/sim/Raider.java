package com.spacecolony.sim;

import java.awt.Color;

/**
 * A hostile vehicle that pursues {@link Harvester}s to strip their fuel
 * and evades other raiders competing for the same prey.
 */
public class Raider extends Vehicle {

    private static final int STARTING_FUEL = 60;
    private static final int UPGRADE_THRESHOLD = 90;
    private static final int MAX_CYCLES = 100;
    private static final int SCAVENGE_RADIUS = 4;

    public Raider(Position position) {
        super(position, STARTING_FUEL, UPGRADE_THRESHOLD, MAX_CYCLES,
                new PursueOrEvadeStrategy(Raider.class, Harvester.class));
    }

    @Override
    protected void scavenge(Colony world) {
        world.findNearest(getPosition(), Harvester.class, SCAVENGE_RADIUS).ifPresent(target -> {
            addFuel(target.getFuelValue());
            target.markDepleted();
        });
    }

    @Override
    protected void tryUpgrade(Colony world) {
        spendFuel(getUpgradeThreshold() / 2);
        try {
            world.addUnit(UnitFactory.createRaider(getPosition()));
        } catch (InvalidSectorException e) {
            // No free sector to deploy a new raider this cycle.
        }
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public char getSymbol() {
        return 'r';
    }
}
