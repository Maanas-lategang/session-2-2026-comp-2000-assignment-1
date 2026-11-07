public class PirateShip extends Ship {

    public PirateShip(Position position) {
        super(position);
        movementStrategy = new HuntMinersStrategy();
    }

    protected void performAction(Space space) {
        MiningShip target = space.findNearbyMiningShip(position);

        if (target != null && target.cargo > 0) {
            int stolen = Math.min(5, target.cargo);
            target.cargo -= stolen;
            cargo += stolen;
            fuel += 2;
        }
    }

    public char getSymbol() {
        return 'P';
    }
}
