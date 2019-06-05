import processing.core.PImage;

import java.util.List;

public class Ghost extends AbstractMover {
    public Ghost(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {}
    public void task (WorldModel world, Entity target, EventScheduler scheduler){
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }
}
