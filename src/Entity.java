import java.util.List;
import processing.core.PImage;

public interface Entity {
   String getId();
   Point getPosition();
   void newpos(Point point);
   List<PImage> getImages();
   PImage getCurrentImage();
}

