import processing.core.PImage;

import java.util.List;

public abstract class AbstractEntity implements Entity{
    private String id;
    private Point position;
    private List<PImage> images;
    protected int imageIndex;
    public AbstractEntity(String id, Point position, List<PImage> images, int imageIndex){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point point) {
        position = point;
    }

    public List<PImage> getImages() { return images; }

    public PImage getCurrentImage() { return (this.images.get(this.imageIndex)); }
}
