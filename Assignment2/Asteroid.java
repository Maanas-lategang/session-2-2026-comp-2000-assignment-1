public class Asteroid extends SpaceEntity {
    private int minerals;

    public Asteroid(Position position) {
        super(position);
        minerals = 20;
    }

    public void act(Space space) {
        age++;
    }

    public int mine() {
        int amount = minerals;
        minerals = 0;
        deactivate();
        return amount;
    }

    public char getSymbol() {
        return '*';
    }
}
