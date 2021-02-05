/**
 * Purpose: Declares Paddle sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the Paddle object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create paddles as shown below.
 * ```
 * private void createPaddle(String IMAGE_FILE, int xCoord, int yCoord) {
 *     myPaddle = new Rectangle(xCoord, yCoord, 150, 20);
 *     //create Paddle
 *     Paddle paddle = new Paddle(xCoord, yCoord, 150,
 *         20, IMAGE_FILE, 3, myPaddle);
 *     paddleMap.add(paddle);
 *     root.getChildren().add(paddle.getRectangle());
 *   }
 * ```
 * Any Other Detail: Default type is "paddle."
 * @author Ji Yun Hyo
 */


package breakout;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paddle extends Sprite {

  private final Rectangle myPaddle;
  public int lives;
  public int experience;

  /**
   * Purpose: Initializes Paddle objects.
   * Assumptions: All input parameters are correct.
   * @param xCoord
   * @param yCoord
   * @param width
   * @param height
   * @param IMAGE name of the image file
   * @param lives indicates the lives the paddle has
   * @param myPaddle Rectangle object representing the paddle
   */
  public Paddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives,
      Rectangle myPaddle) {
    super(xCoord, yCoord, width, height, IMAGE, "", "paddle");
    lives = 3;
    this.myPaddle = myPaddle;
    Image paddle_image = new Image(
        Objects.requireNonNull(
            this.getClass().getClassLoader().getResourceAsStream(IMAGE)));

    myPaddle.setFill(new ImagePattern(paddle_image));
    experience = 0;
  }

  /**
   * Purpose: Evident from the name. To be used in BreakoutApp to update the paddle.
   * @return Rectangle object that represents the paddle
   */
  public Rectangle getRectangle() {
    return this.myPaddle;
  }
}
