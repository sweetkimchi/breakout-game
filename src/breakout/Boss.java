/**
 * Purpose: Declares Boss sprites by passing in unique set of parameters to the super constructor.
 * Stores all information regarding the Boss object
 * Assumptions: Assumes that all parameter inputs are valid.
 * Dependencies: Depends on the super-class (Sprite.java) for all of its methods including the constructor.
 * Example: Used to create Boss as shown below.
 * ```
 *  private void loadBossLevels(ArrayList<&String>& levelDescriptionsFromFile) {
 *     Boss boss = new Boss(Integer.parseInt(levelDescriptionsFromFile.get(1)),
 *         Integer.parseInt(levelDescriptionsFromFile.get(2)),
 *         Integer.parseInt(levelDescriptionsFromFile.get(3)),
 *         Integer.parseInt(levelDescriptionsFromFile.get(4)),
 *         levelDescriptionsFromFile.get(5) + postFix, "boss",
 *         Integer.parseInt(levelDescriptionsFromFile.get(6)));
 *     boss.upload_image_files();
 *     root.getChildren().add(boss.getImageView());
 *     bossMap.add(boss);
 *   }
 * ```
 * Any Other Detail: Default type is "boss."
 * @author Ji Yun Hyo
 */

package breakout;

public class Boss extends Block {

  public int lives;

  public Boss(int xCoord, int yCoord, int width, int height, String IMAGE, String type, int lives) {
    super(xCoord, yCoord, width, height, IMAGE, "", type, lives);
    this.lives = lives;
  }
}
