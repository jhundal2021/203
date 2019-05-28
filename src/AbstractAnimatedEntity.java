import processing.core.PImage;

import java.util.List;

public abstract class AbstractAnimatedEntity extends AbstractActiveEntity implements AnimatedEntity{
    private int animationPeriod;
    public AbstractAnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.getImages().size();
    }
    public int getAnimationPeriod() {
        return animationPeriod;
    }
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}
