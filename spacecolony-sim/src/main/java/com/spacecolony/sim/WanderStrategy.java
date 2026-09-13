package com.spacecolony.sim;

/**
 * Moves one step in a random direction each cycle.
 */
public class WanderStrategy implements NavigationStrategy {

    @Override
    public Position nextMove(Vehicle self, Colony world) {
        int dx = world.getRandom().nextInt(3) - 1;
        int dy = world.getRandom().nextInt(3) - 1;
        return new Position(self.getPosition().getX() + dx, self.getPosition().getY() + dy);
    }
}
