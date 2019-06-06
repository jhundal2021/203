import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class HappyReaper extends AbstractMover {
    public HappyReaper(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> smithTarget = world.findNearest(this.getPosition(), Blacksmith.class);
        long nextPeriod = this.getActionPeriod();
        if (smithTarget.isPresent()){
            this.moveTo(world, smithTarget.get(), scheduler);
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public void task (WorldModel world, Entity target, EventScheduler scheduler){}
}