package breakout;

import javafx.scene.image.ImageView;

public class Ball extends Sprite {

  private final ImageView imageView;
  private String className;
  private final int index;
  public int number_of_lives;

  public Ball(int xCoord, int yCoord, int width, int height, String IMAGE, String type,
      int number_of_lives) {
    super(xCoord, yCoord, width, height, IMAGE, "", type);
    imageView = setImageView(IMAGE);
    index = 0;
    this.number_of_lives = number_of_lives;
  }

  public void setNumber_of_lives(int lives) {
    number_of_lives = lives;
  }
//
//  public void update(){
//
//    this.getImageView().setX(this.getImageView().getX() - 30 * 0.05);
//  }
}
