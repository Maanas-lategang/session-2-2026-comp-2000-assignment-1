package com.spacecolony.sim;

import java.util.Random;

/**
 * Small demo that seeds a colony and runs it forward a number of cycles,
 * printing periodic status lines and a rolling event log at the end.
 */
public class Main {

    public static void main(String[] args) {
        Colony colony = new Colony(20, 20);
        Random random = colony.getRandom();

        for (int i = 0; i < 15; i++) {
            placeUnit(colony, random, UnitFactory::createResourceNode);
        }
        for (int i = 0; i < 8; i++) {
            placeUnit(colony, random, UnitFactory::createHarvester);
        }
        for (int i = 0; i < 3; i++) {
            placeUnit(colony, random, UnitFactory::createRaider);
        }

        RingLog<String> eventLog = new RingLog<>(10);

        for (int cycle = 1; cycle <= 50; cycle++) {
            try {
                colony.tick();
            } catch (ResourceExhaustionException e) {
                eventLog.add("Cycle " + cycle + ": " + e.getMessage());
                break;
            }
            if (cycle % 10 == 0) {
                String status = String.format(
                        "Cycle %d - nodes: %d, harvesters: %d, raiders: %d",
                        cycle,
                        colony.countOfType(ResourceNode.class),
                        colony.countOfType(Harvester.class),
                        colony.countOfType(Raider.class));
                eventLog.add(status);
                System.out.println(status);
            }
        }

        System.out.println("\nRecent events:");
        for (String event : eventLog) {
            System.out.println(" - " + event);
        }
    }

    private interface UnitCreator {
        Unit create(Position position);
    }

    private static void placeUnit(Colony colony, Random random, UnitCreator creator) {
        for (int attempt = 0; attempt < 10; attempt++) {
            Position candidate = new Position(random.nextInt(20), random.nextInt(20));
            try {
                colony.addUnit(creator.create(candidate));
                return;
            } catch (InvalidSectorException ignored) {
                // Sector already taken; try another spot.
            }
        }
    }
}
