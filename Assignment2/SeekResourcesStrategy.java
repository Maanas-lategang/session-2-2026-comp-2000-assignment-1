// Strategy pattern: mining ships move towards the nearest asteroid.
public class SeekResourcesStrategy implements MovementStrategy {
    private MovementStrategy randomMovement = new RandomFlyStrategy();

    public Position nextMove(Ship ship, Space space) {
        Asteroid nearest = null;
        int shortestDistance = Integer.MAX_VALUE;
        Position current = ship.getPosition();

        for (SpaceEntity entity : space.getEntities()) {
            if (entity instanceof Asteroid && entity.isActive()) {
                Asteroid asteroid = (Asteroid) entity;
                int distance = Math.abs(current.getX() - asteroid.getPosition().getX())
                             + Math.abs(current.getY() - asteroid.getPosition().getY());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearest = asteroid;
                }
            }
        }

        if (nearest == null) {
            return randomMovement.nextMove(ship, space);
        }

        int x = current.getX() + Integer.compare(nearest.getPosition().getX(), current.getX());
        int y = current.getY() + Integer.compare(nearest.getPosition().getY(), current.getY());
        return new Position(x, y);
    }
}
