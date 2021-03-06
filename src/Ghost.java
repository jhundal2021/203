import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Ghost extends AbstractMover {
    public Ghost(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> gateTarget = world.findNearest(this.getPosition(), Gate.class);
        long nextPeriod = this.getActionPeriod();
        if (gateTarget.isPresent()) {
            this.moveTo(world, gateTarget.get(), scheduler);
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public void task (WorldModel world, Entity target, EventScheduler scheduler){}
}
