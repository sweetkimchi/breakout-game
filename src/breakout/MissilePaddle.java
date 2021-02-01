package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class MissilePaddle extends Paddle{
  public MissilePaddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives, Rectangle paddle){
    super(xCoord, yCoord, width, height, IMAGE, lives, paddle);
  }
}
