package com.spacecolony.sim;

import java.awt.Color;

/**
 * A mining vehicle that wanders the grid scavenging {@link ResourceNode}s
 * for fuel and deploys new harvesters once well-stocked.
 */
public class Harvester extends Vehicle {

    private static final int STARTING_FUEL = 40;
    private static final int UPGRADE_THRESHOLD = 60;
    private static final int MAX_CYCLES = 80;
    private static final int SCAVENGE_RADIUS = 3;

    public Harvester(Position position) {
        super(position, STARTING_FUEL, UPGRADE_THRESHOLD, MAX_CYCLES, new WanderStrategy());
    }

    @Override
    protected void scavenge(Colony world) {
        world.findNearest(getPosition(), ResourceNode.class, SCAVENGE_RADIUS).ifPresent(node -> {
            addFuel(node.getFuelValue());
            node.markDepleted();
        });
    }

    @Override
    protected void tryUpgrade(Colony world) {
        spendFuel(getUpgradeThreshold() / 2);
        try {
            world.addUnit(UnitFactory.createHarvester(getPosition()));
        } catch (InvalidSectorException e) {
            // No free sector to deploy a new harvester this cycle.
        }
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    @Override
    public char getSymbol() {
        return 'h';
    }
}
