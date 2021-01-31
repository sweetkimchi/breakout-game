package breakout;

import javafx.scene.image.Image;

public class Block extends Sprite{
  public Block(int xCoord, int yCoord, int width, int height, String IMAGE){
    super(xCoord, yCoord, width, height, IMAGE, "block");
  }
}
