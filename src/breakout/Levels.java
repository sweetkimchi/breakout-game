package breakout;

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

  public void launchLevel(){
    BreakoutApp breakout = new BreakoutApp();
    Scene scene = breakout.setupGame(SIZE, SIZE, BACKGROUND, stage);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }
}
