package breakout;

/**
 * Purpose: Loads information about each level from files. Launches a new level by calling the
 * setupGame method from BreakoutApp.
 * Assumption: Assumes the files that are read are correctly formatted. Assumes the files contain
 * correct number of inputs.
 * Dependencies: Declares a BreakoutApp object. BreakoutApp object calls setupGame with the
 * current level as parameter.
 * Example: Used to launch the game as shown below.
 * ```
 * private void
 * skipToLevelIndicatedByKeyInput(KeyEvent event, Stage stage) { try { currentLevel = Math .max(1,
 * Math.min(3, Integer.parseInt(event.getCode().toString().substring(5))));
 * cleanUpAndRestart(stage); } catch (NumberFormatException | FileNotFoundException
 * integerException) { return; } }
 * ```
 * <p>
 * Why I think is a good design: I wanted to separate starting a game with the actual game engine
 * that runs the game. This would be useful if we were to develop a second type of game that is
 * vastly different from a Breakout variant. Levels class would allow users to switch to and from
 * different games. Instead of having the Levels class store a lot of information, I designed in a
 * way where all the Levels class needs is the current level in order to launch a level. It keeps
 * itself SHY from all other classes, especially the BreakoutApp class. It also does not care about
 * the Sprite class and all its sub-classes because Levels class simply reads info from files and
 * sends it to BreakoutApp to be populated. Instead of the constructor automatically launching a new
 * level, I wanted the BreakoutApp to be able to call loadFromFiles directly because that made it so
 * much easier for theBreakoutApp to populate Pane root with all the sprites.
 * <p>
 *
 * Anything else: I refactored loading of the different templates from BreakoutApp into Levels
 * class. This way, the game engine would not have to know
 *
 * @author Ji Yun Hyo
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Levels {

  public static final String TITLE = "Breakout Game";
  private static final String postFix = "-Breakout-Tiles.png";
  private final int currentLevel;
  private final Stage stage;
  private final int SIZE = 1000;

  /**
   * Purpose: Allows other classes to create an object of Levels. Allows other classes to use the
   * Levels object to launch a new level with new parameters.
   * Assumptions: Assumes correct inputs. The calling class must already have a valid Stage
   * object set up. The calling class must have away to discern the currentLevel of the game.
   * Exceptions: No known exceptions.
   *
   * @param currentLevel
   * @param stage        Stage object that is used to set the overall GUI
   */
  public Levels(int currentLevel, Stage stage) {
    this.currentLevel = currentLevel;
    this.stage = stage;
  }

  /**
   * Purpose: Reboots the BreakoutApp game engine. BreakoutApp can start again from scratch.
   * Allows a new BreakoutApp object to start the game over so it does not have to keep track of
   * how many times it ran. Individual run of BreakoutApp has no information on any
   * of the previous runs.
   * Assumptions: BreakoutApp exists and functions correctly.
   * Exceptions: If BreakoutApp is not properly implemented, could result in Null pointer exception.
   *
   * @param currentLevel
   */
  public void rebootGameEngineWithCurrentLevel(int currentLevel) {
    BreakoutApp breakout = new BreakoutApp();
    Scene scene = breakout.setupGame(SIZE, SIZE, stage, currentLevel);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }

  /**
   * Purpose: Reads the level information from the appropriate file. Returns the ArrayList to
   * BreakoutApp that creates the GUI map based on these parameters. Assumptions: Files contain
   * correct inputs for each template. The class that uses the returned knows the template of the
   * map and is able to use the data from the files to create new levels with necessary sprites.
   * Exceptions: File may not exist. Handled by try and catch.
   *
   * @return an ArrayList that contains the string values of data present in the file.
   */
  public ArrayList<String> loadDataFromFiles() {
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
   * Purpose: Handles request to loadLevel for a specific currentLevel without rebooting the game
   * engine. Reboot may be helpful if there are problems with the root Pane.
   *
   * @param blockMap
   * @param bossMap
   * @param root
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