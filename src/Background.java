import java.util.List;
import processing.core.PImage;

final class Background
{
   private final String id;
   private final List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
      this.imageIndex = 0;
   }

   public List<PImage> getImages() {
      return images;
   }

   public String getId() {
      return id;
   }

   public PImage getCurrentImage()
   {return (this.images.get(this.imageIndex));
   }
}
