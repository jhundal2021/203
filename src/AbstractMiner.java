import processing.core.PImage;
import java.util.List;
public abstract class AbstractMiner extends AbstractMover {
    private int resourceLimit;
    protected int resourceCount;

    public AbstractMiner(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit() {
        return resourceLimit;
    }


    //    private Predicate<Point> canPassThrough(WorldModel world){ return point -> (!world.isOccupied(point) && world.withinBounds(point)); }
//
//    private BiPredicate<Point, Point> withinReach(){ return (point, point2) -> (Point.adjacent(point, point2)); }
//
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }
}