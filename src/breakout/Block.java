package breakout;

import javafx.scene.image.Image;

public class Block extends Sprite{
  public int lives;
  private boolean move;
  private String LOW_HEALTH_IMAGE;

  public Block(int xCoord, int yCoord, int width, int height, String FULL_HEALTH_IMAGE, String LOW_HEALTH_IMAGE,String type, int lives){

    super(xCoord, yCoord, width, height, FULL_HEALTH_IMAGE, LOW_HEALTH_IMAGE, type);
    this.LOW_HEALTH_IMAGE = LOW_HEALTH_IMAGE;
    this.lives = lives;
  }

  public int getLives(){
    return this.lives;
  }


}
