package breakout;

import java.awt.Rectangle;
import java.util.Objects;
import javafx.scene.image.Image;

public class Sprite extends Rectangle {
  private static Image image;
  public Sprite(int width, int height, int yCoord, int xCoord, Image image){
      super(xCoord, yCoord, width, height);
  }

  public void update(){

  }
}
