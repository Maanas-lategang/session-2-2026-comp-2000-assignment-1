public abstract class SpaceEntity implements Locatable {
    protected Position position;
    protected boolean active;
    protected int age;

    public SpaceEntity(Position position) {
        this.position = position;
        this.active = true;
        this.age = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public abstract void act(Space space);

    public abstract char getSymbol();
}
