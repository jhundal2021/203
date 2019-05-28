import processing.core.PImage;

import java.util.List;

public abstract class AbstractActiveEntity extends AbstractEntity implements ActionEntity{
    private int actionPeriod;

    public AbstractActiveEntity(String id, Point position, List<PImage> images, int actionPeriod){
        super(id, position, images, 0);
        this.actionPeriod = actionPeriod;
    }
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public int getActionPeriod() {
        return actionPeriod;
    }
}
