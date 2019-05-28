import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Miner_Full extends AbstractMiner{

    public Miner_Full(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceLimit){
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                Blacksmith.class);

        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Miner_Not_Full miner = this.getPosition().createMinerNotFull(this.getId(), this.getResourceLimit(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
    public void task(WorldModel world, Entity target, EventScheduler scheduler){}
}

