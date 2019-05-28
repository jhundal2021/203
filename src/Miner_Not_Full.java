import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Miner_Not_Full extends AbstractMiner{

    public Miner_Not_Full(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceLimit){
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() ||
                !this.moveTo(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.getResourceLimit())
        {
            Miner_Full miner = this.getPosition().createMinerFull(this.getId(), this.getResourceLimit(),
                    this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
    public void task(WorldModel world, Entity target, EventScheduler scheduler){
        this.resourceCount += 1;
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }
}
