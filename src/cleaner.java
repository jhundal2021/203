import processing.core.PImage;

import java.util.List;

public class cleaner extends AbstractMover {
    public cleaner(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {}
    public void task(WorldModel world, Entity target, EventScheduler scheduler){}
}
