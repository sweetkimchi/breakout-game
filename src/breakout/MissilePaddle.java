package breakout;

import javafx.scene.shape.Rectangle;

public class MissilePaddle extends Paddle {

  public int lives;

  public MissilePaddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives,
      Rectangle paddle) {
    super(xCoord, yCoord, width, height, IMAGE, lives, paddle);
    this.lives = lives;
  }


}
