public class Activity implements Action{

    private ActionEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(ActionEntity entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public static Activity createActivityAction(ActionEntity entity, WorldModel world, ImageStore imageStore)
    {
      return new Activity(entity, world, imageStore);
    }

    public void executeAction(EventScheduler scheduler)
    {
        (entity).executeActivity(this.world,
                        this.imageStore, scheduler);
    }

    public Entity getEntity() {
        return entity;
    }

    public WorldModel getWorld() {
        return world;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }
}
