public abstract class Ship extends SpaceEntity {
    protected int fuel;
    protected int cargo;
    protected MovementStrategy movementStrategy;

    public Ship(Position position) {
        super(position);
        fuel = 100;
        cargo = 0;
        movementStrategy = new RandomFlyStrategy();
    }

    public final void act(Space space) {
        Position next = movementStrategy.nextMove(this, space);
        space.moveEntity(this, next);

        fuel--;
        performAction(space);

        if (fuel <= 0) {
            deactivate();
        }
    }

    protected abstract void performAction(Space space);

    // A solar storm can reduce fuel without changing the normal ship actions.
    public void loseFuel(int amount) {
        fuel = Math.max(0, fuel - Math.max(0, amount));
        if (fuel == 0) {
            deactivate();
        }
    }

    public int getFuel() {
        return fuel;
    }

    public int getCargo() {
        return cargo;
    }
}
