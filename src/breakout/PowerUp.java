/**
 * Purpose: Declares PowerUp sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the PowerUp object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create power ups as shown below.
 * ```
 * private void declarePowerUpObject(List<&PowerUp>& powerUps, int xCoord, int yCoord, int i, int i2,
 *       String level_up_power_up, String powerup) {
 *     PowerUp powerUp = new PowerUp(xCoord, yCoord, i, i2, level_up_power_up, "", powerup);
 *     powerUp.upload_image_files();
 *     powerUps.add(powerUp);
 *     root.getChildren().add(powerUp.getImageView());
 *   }
 * ```
 * Any Other Detail: Default type is "powerup."
 * @author Ji Yun Hyo
 */

package breakout;

public class PowerUp extends Sprite {

  public PowerUp(int xCoord, int yCoord, int width, int height, String IMAGE,
      String LOW_HEALTH_IMAGE, String type) {
    super(xCoord, yCoord, width, height, IMAGE, LOW_HEALTH_IMAGE, "powerup");
  }

}
