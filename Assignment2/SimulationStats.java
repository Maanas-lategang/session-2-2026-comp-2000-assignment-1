// Streams and lambdas summarise the simulation without changing its entities.
public class SimulationStats {
    private Space space;

    public SimulationStats(Space space) {
        this.space = space;
    }

    public long getMinerCount() {
        return space.getEntities().stream()
                .filter(entity -> entity instanceof MiningShip && entity.isActive())
                .count();
    }

    public long getPirateCount() {
        return space.getEntities().stream()
                .filter(entity -> entity instanceof PirateShip && entity.isActive())
                .count();
    }

    public long getAsteroidCount() {
        return space.getEntities().stream()
                .filter(entity -> entity instanceof Asteroid && entity.isActive())
                .count();
    }

    public int getTotalCargo() {
        return space.getEntities().stream()
                .filter(entity -> entity instanceof Ship && entity.isActive())
                .mapToInt(entity -> ((Ship) entity).getCargo())
                .sum();
    }

    public double getAverageFuel() {
        return space.getEntities().stream()
                .filter(entity -> entity instanceof Ship && entity.isActive())
                .mapToInt(entity -> ((Ship) entity).getFuel())
                .average().orElse(0);
    }

    public String getSummary() {
        return String.format("Miners: %d   Pirates: %d   Asteroids: %d   Cargo: %d   Average fuel: %.1f",
                getMinerCount(), getPirateCount(), getAsteroidCount(),
                getTotalCargo(), getAverageFuel());
    }
}
