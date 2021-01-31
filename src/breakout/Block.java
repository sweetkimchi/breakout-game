package breakout;

import javafx.scene.image.Image;

public class Block extends Sprite{
  public int lives;
  private boolean move;
  public Block(int xCoord, int yCoord, int width, int height, String IMAGE, String type, int lives){
    super(xCoord, yCoord, width, height, IMAGE, type);
    this.lives = lives;
  }

  public int getLives(){
    return this.lives;
  }


}
