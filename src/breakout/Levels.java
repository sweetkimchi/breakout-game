package breakout;

/**
 * Purpose: Loads information about each level from files. Populates the 'root' variable with the
 * sprites indicated by the data and the templates.<br>
 * Assumption: Assumes the files that are read are correctly formatted. Assumes the files contain
 * correct number of inputs.<br>
 * Dependencies: Used to have dependency on BreakoutApp but I got rid of that. No known dependency.
 * Example: Used to launch the game as shown below.<br>
 * ```
 * private void
 * skipToLevelIndicatedByKeyInput(KeyEvent event, Stage stage) { try { currentLevel = Math .max(1,
 * Math.min(3, Integer.parseInt(event.getCode().toString().substring(5))));
 * cleanUpAndRestart(stage); } catch (NumberFormatException | FileNotFoundException
 * integerException) { return; } }
 * ```
 * <p><br>
 * Why I think is a good design: Levels class does not need to know anything about the BreakoutApp.
 * It does not care about which class calls the methods as long as the inputs are valid.
 * This would be useful if we were to develop a second type of game that is
 * vastly different from a Breakout variant. Levels class would allow users to switch to and from
 * different games. Instead of having the Levels class store a lot of information, I designed in a
 * way where all the Levels class needs is the current level in order to launch a level. It keeps
 * itself SHY from all other classes, especially the BreakoutApp class. It also does not care about
 * the Sprite class and all its sub-classes because Levels class simply reads info from files and
 * populates the root.<br>
 * <p>
 *
 * Anything else: I refactored loading of the different templates which were originally in
 * BreakoutApp into Levels class. This way, the game engine would not have to know anything about
 * the templates in order to use them.<br>
 *
 * @author Ji Yun Hyo
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.layout.Pane;

public class Levels {

  private static final String postFix = "-Breakout-Tiles.png";
  private final int currentLevel;
  private final int SIZE = 1000;

  /**
   * Purpose: Allows other classes to create an object of Levels. Allows other classes to use the
   * Levels object to launch a new level with new parameters.<br>
   * Assumptions: Assumes correct inputs. The calling class must already have a valid Stage
   * object set up. The calling class must have away to discern the currentLevel of the game.<br>
   * Exceptions: No known exceptions.<br>
   *  @param currentLevel
   */
  public Levels(int currentLevel) {
    this.currentLevel = currentLevel;
  }

  private ArrayList<String> loadDataFromFiles() {
    ArrayList<String> parameters = new ArrayList<String>();
    String fileName = "level" + currentLevel + ".txt";
    File myObj = new File(fileName);
    Scanner myReader = null;
    try {
      myReader = new Scanner(myObj);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (true) {
      assert myReader != null;
      if (!myReader.hasNext()) {
        break;
      }
      String data = myReader.next();
      parameters.add(data);
    }
    myReader.close();
    return parameters;
  }

  /**
   * Purpose: Loads level by using the appropriate template indicated by the first input from
   * data in files. <br>
   * Assumption: All inputs are correctly formatted and are valid.<br>
   * Exception: 'parameters' might be null if data in the files are not valid.<br>
   * @param blockMap map of Block objects to be populated on root<br>
   * @param bossMap map of Boss objects to be populated on root<br>
   * @param root Pane object that displays sprites<br>
   */
  public void loadLevel(List<Block> blockMap, List<Boss> bossMap, Pane root) {
    ArrayList<String> parameters = loadDataFromFiles();
    if (currentLevel == 1) {
      loadFromStationaryBlockTemplate(parameters, blockMap, root);
    } else if (currentLevel == 2) {
      loadFromMovingBlockTemplate(parameters, blockMap, root);
    } else if (currentLevel <= 9) {
      loadFromBossTemplate(parameters, bossMap, root);
    }
  }

  private void loadFromStationaryBlockTemplate(ArrayList<String> levelDescriptionsFromFile,
      List<Block> blockMap, Pane root) {
    for (int row = 0; row < Integer.parseInt(levelDescriptionsFromFile.get(1)); row++) {
      for (int column = 0; column < Integer.parseInt(levelDescriptionsFromFile.get(2));
          column++) {
        Block block = new Block(
            Integer.parseInt(levelDescriptionsFromFile.get(3)) + row * (SIZE / Integer
                .parseInt(levelDescriptionsFromFile.get(1))),
            Integer.parseInt(levelDescriptionsFromFile.get(4)) * column + Integer
                .parseInt(levelDescriptionsFromFile.get(5)),
            Integer.parseInt(levelDescriptionsFromFile.get(6)),
            Integer.parseInt(levelDescriptionsFromFile.get(7)),
            levelDescriptionsFromFile.get(8) + postFix,
            levelDescriptionsFromFile.get(9) + postFix, "block",
            Integer.parseInt(levelDescriptionsFromFile.get(10)));
        block.upload_image_files();
        root.getChildren().add(block.getImageView());
        blockMap.add(block);
      }
    }
  }

  private void loadFromMovingBlockTemplate(ArrayList<String> levelDescriptionsFromFile,
      List<Block> blockMap, Pane root) {
    for (int col = 0; col < Integer.parseInt(levelDescriptionsFromFile.get(1)); col++) {
      for (int row = 0; row < Integer.parseInt(levelDescriptionsFromFile.get(2)); row++) {
        Block block = new Block(
            Integer.parseInt(levelDescriptionsFromFile.get(3)) + (SIZE / 10) * col + row * (
                SIZE / 10),
            Integer.parseInt(levelDescriptionsFromFile.get(4)) * row + Integer
                .parseInt(levelDescriptionsFromFile.get(5))
                + col * Integer.parseInt(levelDescriptionsFromFile.get(6)),
            Integer.parseInt(levelDescriptionsFromFile.get(7)),
            Integer.parseInt(levelDescriptionsFromFile.get(8)),
            levelDescriptionsFromFile.get(9) + postFix,
            levelDescriptionsFromFile.get(10) + postFix, "block",
            Integer.parseInt(levelDescriptionsFromFile.get(11)));

        block.upload_image_files();
        root.getChildren().add(block.getImageView());
        blockMap.add(block);
      }
    }
  }

  private void loadFromBossTemplate(ArrayList<String> levelDescriptionsFromFile, List<Boss> bossMap,
      Pane root) {
    Boss boss = new Boss(Integer.parseInt(levelDescriptionsFromFile.get(1)),
        Integer.parseInt(levelDescriptionsFromFile.get(2)),
        Integer.parseInt(levelDescriptionsFromFile.get(3)),
        Integer.parseInt(levelDescriptionsFromFile.get(4)),
        levelDescriptionsFromFile.get(5) + postFix, "boss",
        Integer.parseInt(levelDescriptionsFromFile.get(6)));
    boss.upload_image_files();
    root.getChildren().add(boss.getImageView());
    bossMap.add(boss);
  }
}