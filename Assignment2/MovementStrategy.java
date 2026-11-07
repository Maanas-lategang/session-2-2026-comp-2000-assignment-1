import java.util.Random;

public interface MovementStrategy {
    Position nextMove(Ship ship, Space space);
}

class RandomFlyStrategy implements MovementStrategy {
    private Random random = new Random();

    public Position nextMove(Ship ship, Space space) {
        int x = ship.getPosition().getX() + random.nextInt(3) - 1;
        int y = ship.getPosition().getY() + random.nextInt(3) - 1;

        return new Position(x, y);
    }
}
