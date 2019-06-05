import processing.core.PImage;

import java.util.List;

public class smoke extends AbstractEntity{ public smoke(String id, Point position, List<PImage> images){ super(id, position, images, 0); }
    public static smoke createSmoke(String id, Point position, List<PImage> images)
    {
        return new smoke(id, position, images);
    }
}
