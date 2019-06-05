import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 1300;
   private static final int VIEW_HEIGHT = 900;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "gaia.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         view.shiftView(dx, dy);
      }
   }

   private static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   private static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         world.load(in, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         if (entity instanceof ActionEntity) {
            ((ActionEntity) entity).scheduleActions(scheduler, world, imageStore);
         }
      }
   }

   private static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   private int x, y;
   public void mouseClicked(processing.event.MouseEvent e)
   {
      x = e.getX()/TILE_WIDTH;
      y = e.getY()/TILE_HEIGHT;
      smoke smoke1 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, 0), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke2 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, 1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke3 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, 2), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke6 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(1, 1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke7 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(1, 2), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke9 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(2, 0), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke10 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(2, 1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke16 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, 1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke17 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, 2), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke19 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(-1, 0), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke20 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(-1, 1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke30 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(0, -1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke33 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(1, 0), imageStore.getImageList(WorldModel.SMOKE_KEY));
      smoke smoke34 = smoke.createSmoke(WorldModel.SMOKE_KEY, distanceFromMouse(1, -1), imageStore.getImageList(WorldModel.SMOKE_KEY));
      world.addEntity(smoke1);
      world.addEntity(smoke2);
      world.addEntity(smoke3);
      world.addEntity(smoke6);
      world.addEntity(smoke7);
      world.addEntity(smoke9);
      world.addEntity(smoke10);
      world.addEntity(smoke16);
      world.addEntity(smoke17);
      world.addEntity(smoke19);
      world.addEntity(smoke20);
      world.addEntity(smoke30);
      world.addEntity(smoke33);
      world.addEntity(smoke34);
      reaper Reaper1 = reaper.createReaper(WorldModel.REAPER_KEY, distanceFromMouse(2, -1), imageStore.getImageList(WorldModel.REAPER_KEY), 0, 0);
      reaper Reaper2 = reaper.createReaper(WorldModel.REAPER_KEY, distanceFromMouse(2, 2), imageStore.getImageList(WorldModel.REAPER_KEY), 0, 0);
      reaper Reaper3 = reaper.createReaper(WorldModel.REAPER_KEY, distanceFromMouse(-1, 2), imageStore.getImageList(WorldModel.REAPER_KEY), 0, 0);
      reaper Reaper4 = reaper.createReaper(WorldModel.REAPER_KEY, distanceFromMouse(-1, -1), imageStore.getImageList(WorldModel.REAPER_KEY), 0, 0);
      world.addEntity(Reaper1);
      world.addEntity(Reaper2);
      world.addEntity(Reaper3);
      world.addEntity(Reaper4);
      (Reaper1).scheduleActions(scheduler, world, imageStore);
      (Reaper2).scheduleActions(scheduler, world, imageStore);
      (Reaper3).scheduleActions(scheduler, world, imageStore);
      (Reaper4).scheduleActions(scheduler, world, imageStore);
   }

   private Point getMousePoint(){return new Point(x, y);}

   public Point distanceFromMouse(int x, int y){return new Point(getMousePoint().x + x, getMousePoint().y + y);}

   private boolean inProximity(Entity thing, Point pos){
      return(thing.getPosition().x > pos.x && thing.getPosition().y > pos.y);
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
