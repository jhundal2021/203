import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class AbstractMover extends AbstractAnimatedEntity implements Position {
    public AbstractMover(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod); }

    private Predicate<Point> canPassThrough(WorldModel world){ return point -> (!world.isOccupied(point) && world.withinBounds(point)); }

    private BiPredicate<Point, Point> withinReach(){ return (pt1, pt2) -> Point.adjacent(pt1, pt2);}

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy p = new SingleStepPathingStrategy();
        //PathingStrategy p = new AStarPathingStrategy();
        List<Point> path = p.computePath(getPosition(), destPos, canPassThrough(world), withinReach(), p.CARDINAL_NEIGHBORS);
        if (path == null || path.size() == 0) {
            return getPosition();
        }
        return path.get(0); //singleStep
        //return path.get(1);
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            task(world, target, scheduler);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    protected abstract void task(WorldModel world, Entity target, EventScheduler scheduler);
}