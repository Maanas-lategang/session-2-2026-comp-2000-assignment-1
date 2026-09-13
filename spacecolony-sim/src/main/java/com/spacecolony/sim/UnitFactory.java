package com.spacecolony.sim;

/**
 * Centralised construction point for every unit type, so callers never
 * invoke a concrete constructor directly.
 */
public final class UnitFactory {

    private UnitFactory() {
    }

    public static ResourceNode createResourceNode(Position position) {
        return new ResourceNode(position);
    }

    public static Harvester createHarvester(Position position) {
        return new Harvester(position);
    }

    public static Raider createRaider(Position position) {
        return new Raider(position);
    }
}
