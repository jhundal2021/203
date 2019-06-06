import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Point
{
   public final int x;
   public final int y;

   private static final String QUAKE_ID = "quake";
   private final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
              (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
   }

   public static int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public Blacksmith createBlacksmith(String id, List<PImage> images)
   {
      return new Blacksmith(id, this, images);
   }

   public Miner_Full createMinerFull(String id, int resourceLimit, int actionPeriod, int animationPeriod,
                                        List<PImage> images)
   {
      return new Miner_Full(id, this, images, actionPeriod, animationPeriod, resourceLimit);
   }

   public Miner_Not_Full createMinerNotFull(String id, int resourceLimit, int actionPeriod, int animationPeriod,
                                           List<PImage> images)
   {
      return new Miner_Not_Full(id, this, images, actionPeriod, animationPeriod, resourceLimit);
   }

   public Obstacle createObstacle(String id, List<PImage> images)
   {
      return new Obstacle(id, this, images);
   }

   public Gate createGate(String id, List<PImage> images)
   {
      return new Gate(id, this, images);
   }

   public Ore_Blob createOreBlob(String id, int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Ore_Blob(id, this, images, actionPeriod, animationPeriod);
   }

   public Ore createOre(String id, int actionPeriod, List<PImage> images)
   {
      return new Ore(id, this, images, actionPeriod);
   }

   public Vein createVein(String id, int actionPeriod, List<PImage> images)
   {
      return new Vein(id, this, images, actionPeriod);
   }

   public Ghost createGhost(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
      return new Ghost(id, position, images, actionPeriod, animationPeriod);
   }

   public HappyReaper createHappy(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
      return new HappyReaper(id, position, images, actionPeriod, animationPeriod);
   }

   public Quake createQuake(List<PImage> images)
   {
      return new Quake(QUAKE_ID, this, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public static Optional<Entity> nearestEntity(List<Entity> entities, Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

         for (Entity other : entities)
         {
            int otherDistance = Point.distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }
}
