package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DotPaddle extends Paddle{
  public DotPaddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives){
    super(xCoord, yCoord, width, height, IMAGE, lives);
  }
}
