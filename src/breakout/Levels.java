/**
 * Purpose: Loads information about each level from files. Launches a new level by calling the Breakoutapp.
 * Assumptions: Assumes the files that are read are correctly formatted. Assumes the files contain correct number of inputs.
 * Dependencies: Declares a BreakoutApp ojbect. BreakoutApp object calls setupGame with the current level as parameter.
 * Example: Used to launch the game as shown below.
 * ```
 * private void cleanUpAndRestart(Stage stage) throws FileNotFoundException {
 *     Levels level = new Levels(currentLevel, stage);
 *     level.launchLevel(currentLevel);
 *     animation.stop();
 *   }
 *
 *   private void loadLevelFromFile(int levelTemplate, Stage stage) {
 *     Levels launch_helper = new Levels(currentLevel, stage);
 *     ArrayList<String> levelDescriptionsFromFile = launch_helper.loadFromFiles();
 *
 *     if (levelTemplate == 1) {
 *       loadStationaryBlockLevels(levelDescriptionsFromFile);
 *     } else if (levelTemplate == 2) {
 *       loadMovingBlockLevels(levelDescriptionsFromFile);
 *     } else if (levelTemplate <= 9) {
 *       loadBossLevels(levelDescriptionsFromFile);
 *     }
 *   }
 * ```
 *
 * @author Ji Yun Hyo
 *
 *
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


  public Levels(int currentLevel, Stage stage) {
    this.currentLevel = currentLevel;
    this.stage = stage;
  }

  /**
   *
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
   * Purpose:
   * @return parameters
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
