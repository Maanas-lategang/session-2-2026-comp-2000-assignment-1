public class MiningShip extends Ship {

    public MiningShip(Position position) {
        super(position);
        movementStrategy = new SeekResourcesStrategy();
    }

    protected void performAction(Space space) {
        Asteroid asteroid = space.findNearbyAsteroid(position);

        if (asteroid != null) {
            cargo += asteroid.mine();
            fuel += 10;
        }
    }

    public char getSymbol() {
        return 'M';
    }
}
