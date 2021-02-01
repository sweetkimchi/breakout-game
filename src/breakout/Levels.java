package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Levels {
  private int currentLevel;
  public static final String TITLE = "Example JavaFX";
  public static final Paint BACKGROUND = Color.WHITE;
  private Stage stage;
  private int SIZE = 1000;
  public Levels(int currentLevel, Stage stage){
    this.currentLevel = currentLevel;
    this.stage = stage;
  }

  public void launchLevel(int currentLevel){
    BreakoutApp breakout = new BreakoutApp();
    Scene scene = breakout.setupGame(SIZE, SIZE, BACKGROUND, stage);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }

  public void loadFromFiles(int currentLevel) throws FileNotFoundException {
    String fileName = "level" + currentLevel + ".txt";
    File myObj = new File("filename.txt");
    Scanner myReader = new Scanner(myObj);
    while (myReader.hasNextLine()) {
      String data = myReader.nextLine();
      System.out.println(data);
    }
    myReader.close();
  }
}
