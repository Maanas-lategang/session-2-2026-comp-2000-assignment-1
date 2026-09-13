package com.spacecolony.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * The simulation grid. Owns every unit, advances the simulation one cycle
 * at a time, and answers spatial queries on behalf of units and
 * strategies.
 */
public class Colony {

    private static final int MAX_UNITS = 500;

    private final int width;
    private final int height;
    private final Map<Position, Unit> grid = new HashMap<>();
    private final List<Unit> units = new ArrayList<>();
    private final Random random = new Random();

    public Colony(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void tick() throws ResourceExhaustionException {
        if (units.size() > MAX_UNITS) {
            throw new ResourceExhaustionException(units.size());
        }
        List<Unit> snapshot = new ArrayList<>(units);
        for (Unit unit : snapshot) {
            if (unit.isActive()) {
                unit.act(this);
            }
        }
        units.removeIf(u -> !u.isActive());
        grid.entrySet().removeIf(e -> !e.getValue().isActive());
    }

    public void moveUnit(Unit unit, Position target) throws InvalidSectorException {
        boolean occupiedByOther = grid.containsKey(target) && grid.get(target) != unit;
        if (!inBounds(target) || occupiedByOther) {
            throw new InvalidSectorException(target);
        }
        grid.remove(unit.getPosition());
        unit.setPosition(target);
        grid.put(target, unit);
    }

    public void addUnit(Unit unit) throws InvalidSectorException {
        Position pos = unit.getPosition();
        if (!inBounds(pos) || grid.containsKey(pos)) {
            throw new InvalidSectorException(pos);
        }
        grid.put(pos, unit);
        units.add(unit);
    }

    @SuppressWarnings("unchecked")
    public <T extends Unit> Optional<T> findNearest(Position pos, Class<T> type, int radius) {
        T nearest = null;
        int bestDist = Integer.MAX_VALUE;
        for (Unit unit : units) {
            if (!type.isInstance(unit) || !unit.isActive()) {
                continue;
            }
            int dist = pos.distanceTo(unit.getPosition());
            if (dist <= radius && dist < bestDist) {
                bestDist = dist;
                nearest = (T) unit;
            }
        }
        return Optional.ofNullable(nearest);
    }

    public long countOfType(Class<? extends Unit> type) {
        return units.stream().filter(type::isInstance).count();
    }

    public boolean inBounds(Position p) {
        return p.getX() >= 0 && p.getX() < width && p.getY() >= 0 && p.getY() < height;
    }

    public Random getRandom() {
        return random;
    }

    public List<Unit> getUnits() {
        return Collections.unmodifiableList(units);
    }
}
