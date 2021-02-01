package breakout;

import javafx.scene.image.Image;

public class PowerUp extends Sprite {

  private String IMAGE;

  public PowerUp(int xCoord, int yCoord, int width, int height, String IMAGE,
      String LOW_HEALTH_IMAGE, String type) {
    super(xCoord, yCoord, width, height, IMAGE, LOW_HEALTH_IMAGE, "powerup");
    this.IMAGE = IMAGE;
  }

  public String getIMAGE(){
    return this.IMAGE;
  }

}
