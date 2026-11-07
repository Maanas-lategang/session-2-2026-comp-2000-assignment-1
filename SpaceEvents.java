import java.util.Random;

// Random events make the simulation different each time it runs.
public class SpaceEvents {
    private Random random = new Random();
    private String lastEvent = "No event yet";

    public String getLastEvent() {
        return lastEvent;
    }

    public void update(Space space) {
        int chance = random.nextInt(100);
        if (chance < 3) {
            addResources(space);
        } else if (chance < 5) {
            solarStorm(space);
        }
    }

    // Public methods also make these events easy to test separately.
    public void addResources(Space space) {
        for (int i = 0; i < 4; i++) {
            space.addEntity(new Asteroid(space.randomPosition()));
        }
        lastEvent = "Resource discovery: 4 new asteroids";
    }

    public void solarStorm(Space space) {
        int affected = 0;
        for (SpaceEntity entity : space.getEntities()) {
            if (entity instanceof Ship && entity.isActive()) {
                ((Ship) entity).loseFuel(8);
                affected++;
            }
        }
        lastEvent = "Solar storm: " + affected + " ships lost fuel";
    }
}
