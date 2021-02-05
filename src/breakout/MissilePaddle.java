/**
 * Purpose: Declares MissilePaddle sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the MissilePaddle object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create missile paddles as shown below.
 * ```
 * private void createPaddle(String IMAGE_FILE, int xCoord, int yCoord) {
 *     myPaddle = new Rectangle(xCoord, yCoord, 150, 20);
 *     //create Paddle
 *     MissilePaddle missilePaddle = new MissilePaddle(xCoord, yCoord, 150,
 *         20, IMAGE_FILE, 3, myPaddle);
 *     missilePaddleMap.add(missilePaddle);
 *     root.getChildren().add(missilePaddle.getRectangle());
 *   }
 * ```
 * Any Other Detail: Default type is "paddle."
 * @author Ji Yun Hyo
 */

package breakout;

import javafx.scene.shape.Rectangle;

public class MissilePaddle extends Paddle {

  public int lives;

  /**
   * Purpose: Initializes MissilePaddle objects.
   * Assumptions: All input parameters are correct.
   * @param xCoord
   * @param yCoord
   * @param width
   * @param height
   * @param IMAGE image file name for full health
   * @param lives image file name for low health
   * @param paddle Rectangle object representing the paddle
   */
  public MissilePaddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives,
      Rectangle paddle) {
    super(xCoord, yCoord, width, height, IMAGE, lives, paddle);
    this.lives = lives;
  }


}
