package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.*;



public class BreakoutApp extends Application{

  public static final String TITLE = "Example JavaFX";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.PLUM;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
  public static final String BOUNCER_IMAGE = "ball.gif";
  public static final int BOUNCER_SPEED = 30;
  public static final Paint MOVER_COLOR = Color.PLUM;
  public static final int MOVER_SIZE = 50;
  public static final int MOVER_SPEED = 5;
  public static final Paint GROWER_COLOR = Color.BISQUE;
  public static final double GROWER_RATE = 1.1;
  public static final int GROWER_SIZE = 50;

  private Scene myScene;
  private ImageView myBouncer;
  private Rectangle myMover;
  private Rectangle myGrower;
  private Map<ImageView, TreeMap<String, Integer>> map;

  @Override
  public void start(Stage stage) throws Exception {
  }

  public static void main(String[] args){
    launch(args);
  }
}
