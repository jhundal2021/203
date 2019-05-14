import processing.core.PImage;
import java.util.List;

public abstract class AbstractMiner extends AbstractAnimatedEntity implements Position{
    protected int resourceLimit;
    protected int resourceCount;

    public AbstractMiner(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }
}
