import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class knight extends AbstractMover {
    public knight(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public static knight createKnight(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        return new knight(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> minerTarget = world.findNearest(this.getPosition(), Miner_Not_Full.class);
        Optional<Entity> miner2Target = world.findNearest(this.getPosition(), Miner_Full.class);
        long nextPeriod = this.getActionPeriod();
        if (minerTarget.isPresent()){
            this.moveTo(world, minerTarget.get(), scheduler, imageStore);
        }
        if (miner2Target.isPresent()) {
            this.moveTo(world, miner2Target.get(), scheduler, imageStore);
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    private Predicate<Point> canPassThrough(WorldModel world) {
        return point -> (!world.isOccupied(point) && world.withinBounds(point));
    }

    private BiPredicate<Point, Point> withinReach() {
        return (pt1, pt2) -> Point.adjacent(pt1, pt2);
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy p = new AStarPathingStrategy();
        List<Point> path = p.computePath(getPosition(), destPos, canPassThrough(world), withinReach(), p.CARDINAL_NEIGHBORS);
        if (path == null || path.size() == 0) {
            return getPosition();
        }
        return path.get(1);
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler, ImageStore images) {
        if (Point.adjacent(this.getPosition(), target.getPosition()) && world.getOccupant(target.getPosition()).isPresent() && target.getClass().equals(Miner_Not_Full.class) && !target.getCurrentImage().equals("ghost")) {
            Miner_Not_Full ghost = (Miner_Not_Full) (world.getOccupant(target.getPosition()).get());
            ghost.transformGhost(world, scheduler, images);
            return true;
        }
        else if (Point.adjacent(this.getPosition(), target.getPosition()) && world.getOccupant(target.getPosition()).isPresent() && target.getClass().equals(Miner_Full.class) && !target.getCurrentImage().equals("ghost")){
            Miner_Full ghost2 = (Miner_Full) (world.getOccupant(target.getPosition()).get());
            ghost2.transformGhost(world, scheduler, images);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public void task (WorldModel world, Entity target, EventScheduler scheduler){
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }
}
