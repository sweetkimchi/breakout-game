/**
 * Purpose: Declares Missile sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the Missile object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create missiles as shown below.
 * ```
 * public void gameControlKeys(KeyEvent event) {
 *     if (event.getCode() == KeyCode.SPACE) {
 *       if (amount_missiles > 0) {
 *         amount_missiles--;
 *         for (int i = 0; i <= 1; i++) {
 *           Missile missile = new Missile(
 *               (int) myPaddle.getX() + (int) (myPaddle.getWidth() - 10) * i,
 *               (int) myPaddle.getY(), 6, 20, MISSILE_IMAGE);
 *           missile.upload_image_files();
 *           missileMap.add(missile);
 *           root.getChildren().add(missile.getImageView());
 *         }
 *       }
 *     }
 *     if (event.getCode() == KeyCode.N && paddleSpeed < 18) {
 *       paddleSpeed += 4;
 *     }
 *     if (event.getCode() == KeyCode.B && paddleSpeed > 9) {
 *       paddleSpeed = 9;
 *
 *     }
 *   }
 * ```
 * Any Other Detail: Default type is "missile."
 * @author Ji Yun Hyo
 */

package breakout;

public class Missile extends Sprite {

  /**
   * Purpose: Initializes Missile objects.
   * Assumptions: All input parameters are correct.
   * @param xCoord
   * @param yCoord
   * @param width
   * @param height
   * @param IMAGE image file name for full health powerup
   */
  public Missile(int xCoord, int yCoord, int width, int height, String IMAGE) {
    super(xCoord, yCoord, width, height, IMAGE, "", "missile");
  }


}
