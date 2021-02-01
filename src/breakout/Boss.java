package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Block {

  public int lives;

  public Boss(int xCoord, int yCoord, int width, int height, String IMAGE, String type, int lives) {
    super(xCoord, yCoord, width, height, IMAGE, "", type, lives);
    this.lives = lives;
  }
}
