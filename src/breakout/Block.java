/**
 * Purpose: Declares Block sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the Block object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create Blocks as shown below.
 * ```
 * private void loadStationaryBlockLevels(ArrayList<&String>& levelDescriptionsFromFile) {
 *     for (int row = 0; row < Integer.parseInt(levelDescriptionsFromFile.get(1)); row++) {
 *       for (int column = 0; column < Integer.parseInt(levelDescriptionsFromFile.get(2));
 *           column++) {
 *         Block block = new Block(
 *             Integer.parseInt(levelDescriptionsFromFile.get(3)) + row * (SIZE / Integer
 *                 .parseInt(levelDescriptionsFromFile.get(1))),
 *             Integer.parseInt(levelDescriptionsFromFile.get(4)) * column + Integer
 *                 .parseInt(levelDescriptionsFromFile.get(5)),
 *             Integer.parseInt(levelDescriptionsFromFile.get(6)),
 *             Integer.parseInt(levelDescriptionsFromFile.get(7)),
 *             levelDescriptionsFromFile.get(8) + postFix,
 *             levelDescriptionsFromFile.get(9) + postFix, "block",
 *             Integer.parseInt(levelDescriptionsFromFile.get(10)));
 *         block.upload_image_files();
 *         root.getChildren().add(block.getImageView());
 *         blockMap.add(block);
 *       }
 *     }
 *   }
 * ```
 * Any Other Detail: Default type is "block."
 * @author Ji Yun Hyo
 */

package breakout;

public class Block extends Sprite {

  private final String LOW_HEALTH_IMAGE;
  public int lives;

  public Block(int xCoord, int yCoord, int width, int height, String FULL_HEALTH_IMAGE,
      String LOW_HEALTH_IMAGE, String type, int lives) {

    super(xCoord, yCoord, width, height, FULL_HEALTH_IMAGE, LOW_HEALTH_IMAGE, type);
    this.LOW_HEALTH_IMAGE = LOW_HEALTH_IMAGE;
    this.lives = lives;
  }

}
