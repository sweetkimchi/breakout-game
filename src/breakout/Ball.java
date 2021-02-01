package breakout;

import javafx.scene.image.ImageView;

public class Ball extends Sprite {

  public int number_of_lives;

  public Ball(int xCoord, int yCoord, int width, int height, String IMAGE, String type,
      int number_of_lives) {
    super(xCoord, yCoord, width, height, IMAGE, "", type);
    ImageView imageView = setImageView(IMAGE);
    this.number_of_lives = number_of_lives;
  }
}
