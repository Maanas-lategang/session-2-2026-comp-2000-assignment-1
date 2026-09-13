package com.spacecolony.sim;

import java.awt.Color;

/**
 * Base type for everything that can exist on the colony grid: resource
 * nodes and vehicles alike.
 */
public abstract class Unit implements Locatable, Refuelable {

    private Position position;
    private boolean active;
    private int cycles;

    protected Unit(Position position) {
        this.position = position;
        this.active = true;
        this.cycles = 0;
    }

    public abstract void act(Colony world);

    public abstract Color getColor();

    public abstract char getSymbol();

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public void markDepleted() {
        deactivate();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    protected void setPosition(Position position) {
        this.position = position;
    }

    public int getCycles() {
        return cycles;
    }

    protected void incrementCycles() {
        this.cycles++;
    }
}
