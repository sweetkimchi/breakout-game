package breakout;

/**
 * Purpose: Loads information about each level from files. Launches a new level by calling the
 * setupGame method from BreakoutApp.
 * Assumption: Assumes the files that are read are correctly formatted. Assumes the files contain
 * correct number of inputs.
 * Dependencies: Declares a BreakoutApp object. BreakoutApp object calls setupGame with the current
 * level as parameter.
 * Example: Used to launch the game as shown below.
 * ```
 *  private void skipToLevelIndicatedByKeyInput(KeyEvent event, Stage stage) {
 *     try {
 *       currentLevel = Math
 *           .max(1, Math.min(3, Integer.parseInt(event.getCode().toString().substring(5))));
 *       cleanUpAndRestart(stage);
 *     } catch (NumberFormatException | FileNotFoundException integerException) {
 *       return;
 *     }
 *   }
 * ```
 * Second Example:
 *  private void cleanUpAndRestart(Stage stage) throws FileNotFoundException {
 *     Levels level = new Levels(currentLevel, stage);
 *     level.launchLevel(currentLevel);
 *     animation.stop();
 *   }
 *
 *   private void loadLevelFromFile(int levelTemplate, Stage stage) {
 *     Levels launch_helper = new Levels(currentLevel, stage);
 *     ArrayList<&String> levelDescriptionsFromFile = launch_helper.loadFromFiles();
 *
 *     if (levelTemplate == 1) {
 *       loadStationaryBlockLevels(levelDescriptionsFromFile);
 *     } else if (levelTemplate == 2) {
 *       loadMovingBlockLevels(levelDescriptionsFromFile);
 *     } else if (levelTemplate <= 9) {
 *       loadBossLevels(levelDescriptionsFromFile);
 *     }
 *   }
 *
 *   private void loadStationaryBlockLevels(ArrayList<&String> levelDescriptionsFromFile) {
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
 *
 * Why I think is a good design: I wanted to separate starting a game with the actual game engine
 * that runs the game. This would be useful if we were to develop a second type of game that is vastly
 * different from a Breakout variant. Levels class would allow users to switch to and from different
 * games. Instead of having the Levels class store a lot of information, I designed in a way where
 * all the Levels class needs is the current level in order to launch a level. It keeps itself SHY
 * from all other classes, especially the BreakoutApp class. It also does not care about the Sprite
 * class and all its sub-classes because Levels class simply reads info from files and sends it to
 * BreakoutApp to be populated. Instead of the constructor automatically launching a new level,
 * I wanted the BreakoutApp to be able to call loadFromFiles directly because that made it so much
 * easier for theBreakoutApp to populate Pane root with all the sprites.
 *
 * @author Ji Yun Hyo
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Levels {

  public static final String TITLE = "Breakout Game";
  private final int currentLevel;
  private final Stage stage;
  private final int SIZE = 1000;

  /**
   * Purpose: Allows other classes to create an object of Levels. Allows other classes to use the
   * Levels object to launch a new level with new parameters.
   * Assumptions: Assumes correct inputs. The calling class must already have a valid Stage object
   * set up. The calling class
   * must have a way to discern the currentLevel of the game.
   * Exceptions: No known exceptions.
   * @param currentLevel
   * @param stage Stage object that is used to set the overall GUI
   */
  public Levels(int currentLevel, Stage stage) {
    this.currentLevel = currentLevel;
    this.stage = stage;
  }

  /**
   * Purpose: Launches a new level by creating a BreakoutApp object and calling the setupGame method
   * with currentLevel.
   * Assumptions: BreakoutApp exists and functions correctly.
   * Exceptions: If BreakoutApp is not properly implemented, could result in Null pointer exception.
   * @param currentLevel
   */
  public void launchLevel(int currentLevel) {
    BreakoutApp breakout = new BreakoutApp();
    Scene scene = breakout.setupGame(SIZE, SIZE, stage, currentLevel);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }

  /**
   * Purpose: Reads the level information from the appropriate file. Returns the ArrayList to BreakoutApp
   * that creates the GUI map based on these parameters.
   * Assumptions: Files contain correct inputs for each template. The class that uses the returned
   * knows the template of the map and is able to use the data from the files to create new levels
   * with necessary sprites.
   * Exceptions: File may not exist. Handled by try and catch.
   * @return an ArrayList that contains the string values of data present in the file.
   */
  public ArrayList<String> loadFromFiles() {
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
}