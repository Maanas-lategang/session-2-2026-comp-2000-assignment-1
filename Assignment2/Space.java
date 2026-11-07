import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Space {
    private int width;
    private int height;
    private List<SpaceEntity> entities;
    private Random random;

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
        entities = new ArrayList<>();
        random = new Random();
    }

    public void addEntity(SpaceEntity entity) {
        entities.add(entity);
    }

    public void tick() {
        for (SpaceEntity entity : new ArrayList<>(entities)) {
            if (entity.isActive()) {
                entity.act(this);
            }
        }

        entities.removeIf(entity -> !entity.isActive());

        if (random.nextInt(10) == 0) {
            addEntity(new Asteroid(randomPosition()));
        }
    }

    public void moveEntity(SpaceEntity entity, Position target) {
        int x = Math.max(0, Math.min(width - 1, target.getX()));
        int y = Math.max(0, Math.min(height - 1, target.getY()));

        entity.setPosition(new Position(x, y));
    }

    public Asteroid findNearbyAsteroid(Position position) {
        for (SpaceEntity entity : entities) {
            if (entity instanceof Asteroid && entity.isActive()
                    && isNearby(position, entity.getPosition())) {
                return (Asteroid) entity;
            }
        }
        return null;
    }

    public MiningShip findNearbyMiningShip(Position position) {
        for (SpaceEntity entity : entities) {
            if (entity instanceof MiningShip && entity.isActive()
                    && isNearby(position, entity.getPosition())) {
                return (MiningShip) entity;
            }
        }
        return null;
    }

    private boolean isNearby(Position a, Position b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());

        return dx <= 1 && dy <= 1;
    }

    public Position randomPosition() {
        return new Position(
                random.nextInt(width),
                random.nextInt(height)
        );
    }

    public List<SpaceEntity> getEntities() {
        return entities;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
