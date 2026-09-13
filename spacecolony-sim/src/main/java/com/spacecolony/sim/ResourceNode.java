package com.spacecolony.sim;

import java.awt.Color;

/**
 * A stationary mineral deposit. Grows richer with age and occasionally
 * seeds a new deposit nearby, mirroring how a plant spreads.
 */
public class ResourceNode extends Unit {

    private static final int SPREAD_CHANCE_PERCENT = 5;
    private static final int MAX_SPREAD_CYCLES = 50;

    public ResourceNode(Position position) {
        super(position);
    }

    @Override
    public void act(Colony world) {
        incrementCycles();
        trySpread(world);
    }

    @Override
    public int getFuelValue() {
        return 10 + getCycles() / 5;
    }

    private void trySpread(Colony world) {
        if (getCycles() > MAX_SPREAD_CYCLES) {
            return;
        }
        if (world.getRandom().nextInt(100) >= SPREAD_CHANCE_PERCENT) {
            return;
        }
        Position candidate = new Position(
                getPosition().getX() + world.getRandom().nextInt(3) - 1,
                getPosition().getY() + world.getRandom().nextInt(3) - 1);
        try {
            world.addUnit(UnitFactory.createResourceNode(candidate));
        } catch (InvalidSectorException e) {
            // Sector occupied or out of bounds; the deposit fails to spread this cycle.
        }
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public char getSymbol() {
        return '*';
    }
}
