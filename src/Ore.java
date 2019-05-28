import processing.core.PImage;
import java.util.Random;
import java.util.List;

public class Ore extends AbstractActiveEntity {
    private String BLOB_KEY = "blob";
    private String BLOB_ID_SUFFIX = " -- blob";
    private int BLOB_PERIOD_SCALE = 4;
    private int BLOB_ANIMATION_MIN = 50;
    private int BLOB_ANIMATION_MAX = 150;

    private static Random rand = new Random();

    public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = pos.createOreBlob(this.getId() + BLOB_ID_SUFFIX,
                this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        ((ActionEntity) blob).scheduleActions(scheduler, world, imageStore);
    }
}
