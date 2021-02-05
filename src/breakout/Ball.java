/**
 * Purpose: Declares Ball sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the Ball object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create Balls as shown below.
 * ```
 * private void makeBall() {
 *     Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", numberOfLives);
 *     ball.upload_image_files();
 *     root.getChildren().add(ball.getImageView());
 *     ballMap.add(ball);
 *   }
 * ```
 * Any Other Detail: Default type is "ball."
 * @author Ji Yun Hyo
 */

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
