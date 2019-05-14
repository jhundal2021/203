public class Animation implements Action{
    private AnimatedEntity entity;
    private int repeatCount;

    public Animation(AnimatedEntity entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public static Animation createAnimationAction(AnimatedEntity entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }

    public void executeAction(EventScheduler scheduler)
    {
        (entity).nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    (entity).getAnimationPeriod());
        }
    }

    public Entity getEntity() {
        return entity;
    }
}
