package breakout;

import javafx.scene.image.Image;

public class Paddle extends Sprite{
  public int lives;
  public Paddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives){
    super(xCoord, yCoord, width, height, IMAGE, "paddle");
  }
}
