package com.spacecolony.sim;

import java.util.Optional;

/**
 * Steers a vehicle away from the nearest threat, or toward the nearest
 * target if no threat is in range, falling back to wandering otherwise.
 */
public class PursueOrEvadeStrategy implements NavigationStrategy {

    private final Class<? extends Unit> threatType;
    private final Class<? extends Unit> targetType;
    private static final int SENSE_RADIUS = 5;

    public PursueOrEvadeStrategy(Class<? extends Unit> threatType, Class<? extends Unit> targetType) {
        this.threatType = threatType;
        this.targetType = targetType;
    }

    @Override
    public Position nextMove(Vehicle self, Colony world) {
        Optional<? extends Unit> threat = world.findNearest(self.getPosition(), threatType, SENSE_RADIUS)
                .filter(u -> u != self);
        if (threat.isPresent()) {
            return stepAway(self.getPosition(), threat.get().getPosition());
        }

        Optional<? extends Unit> target = world.findNearest(self.getPosition(), targetType, SENSE_RADIUS);
        if (target.isPresent()) {
            return stepToward(self.getPosition(), target.get().getPosition());
        }

        return new WanderStrategy().nextMove(self, world);
    }

    private Position stepToward(Position from, Position to) {
        int dx = Integer.compare(to.getX(), from.getX());
        int dy = Integer.compare(to.getY(), from.getY());
        return new Position(from.getX() + dx, from.getY() + dy);
    }

    private Position stepAway(Position from, Position threatPos) {
        int dx = Integer.compare(from.getX(), threatPos.getX());
        int dy = Integer.compare(from.getY(), threatPos.getY());
        return new Position(from.getX() + dx, from.getY() + dy);
    }
}
