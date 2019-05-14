import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends AbstractActiveEntity{
    private static Random rand = new Random();
    private String ORE_KEY = "ore";
    private String ORE_ID_PREFIX = "ore -- ";
    private int ORE_CORRUPT_MIN = 20000;
    private int ORE_CORRUPT_MAX = 30000;

    public Vein(String id, Point position, List<PImage> images, int actionPeriod){
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Entity ore = openPt.get().createOre(ORE_ID_PREFIX + this.id, ORE_CORRUPT_MIN +
                            rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ((ActionEntity)ore).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }
}
