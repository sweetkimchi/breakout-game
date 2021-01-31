package breakout;

import javafx.scene.image.Image;

public class Paddle extends Sprite{
  public Paddle(int xCoord, int yCoord, int width, int height, String IMAGE){
    super(xCoord, yCoord, width, height, IMAGE, "paddle");
  }
}
