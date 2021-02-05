/**
 * Purpose: Loads information about each level from files. Launches a new level by calling the setupGame method from BreakoutApp.
 * Assumption: Assumes the files that are read are correctly formatted. Assumes the files contain correct number
 * of inputs.
 * Dependencies: Declares a BreakoutApp object. BreakoutApp object calls setupGame with the current
 * level as parameter.
 * Example: Used to launch the game as shown below.
 * ```
 *private void cleanUpAndRestart(Stage stage) throws FileNotFoundException { Levels level = new
 * Levels(currentLevel, stage); level.launchLevel(currentLevel); animation.stop(); }
 * <p>
 * private void loadLevelFromFile(int levelTemplate, Stage stage) { Levels launch_helper = new
 * Levels(currentLevel, stage); ArrayList<String> levelDescriptionsFromFile =
 * launch_helper.loadFromFiles();
 * <p>
 * if (levelTemplate == 1) { loadStationaryBlockLevels(levelDescriptionsFromFile); } else if
 * (levelTemplate == 2) { loadMovingBlockLevels(levelDescriptionsFromFile); } else if (levelTemplate
 * <= 9) { loadBossLevels(levelDescriptionsFromFile); }
 * }
 * ```
 *
 * Why I think is a good design: I wanted to separate starting a game with the actual game engine
 * that runs the game. This would be useful if we were to develop a second type of game that is vastly
 * different from a Breakout variant. Levels class would allow users to switch to and from different
 * games.
 *
 * @author Ji Yun Hyo
 */

package breakout;

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
   * Allows other classes to create an object of Levels. Assumes correct inputs.
   * @param currentLevel
   * @param stage Stage object that is used to set the overall GUI
   */
  public Levels(int currentLevel, Stage stage) {
    this.currentLevel = currentLevel;
    this.stage = stage;
  }

  /**
   * Purpose: Launches a new level by reating a BreakoutApp object and calling the setupGame method with currentLevel.
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
   * Assumptions: Files contain correct inputs.
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
