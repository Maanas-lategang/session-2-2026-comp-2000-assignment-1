// Strategy pattern: pirate ships chase the nearest miner carrying minerals.
public class HuntMinersStrategy implements MovementStrategy {
    private MovementStrategy randomMovement = new RandomFlyStrategy();

    public Position nextMove(Ship ship, Space space) {
        MiningShip nearest = null;
        int shortestDistance = Integer.MAX_VALUE;
        Position current = ship.getPosition();

        for (SpaceEntity entity : space.getEntities()) {
            if (entity instanceof MiningShip && entity.isActive()) {
                MiningShip miner = (MiningShip) entity;
                if (miner.getCargo() > 0) {
                    int distance = Math.abs(current.getX() - miner.getPosition().getX())
                                 + Math.abs(current.getY() - miner.getPosition().getY());
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        nearest = miner;
                    }
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
