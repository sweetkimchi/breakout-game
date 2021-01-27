package breakout;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import javafx.scene.text.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;




public class BreakoutApp extends Application{

  public static final String TITLE = "Example JavaFX";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
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
  private ImageView myPaddle;
  private ImageView myBouncer;

  @Override
  public void start(Stage stage) throws Exception {
    myScene = setupGame(SIZE, SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  public Scene setupGame(int width, int height,Paint background){

    // create one top level collection to organize the things in the scene
    Group root = new Group();
    // make some shapes and set their properties
    Image image = new Image(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE)));

    for (int i = 0; i < 1; i++) {
      ImageView bouncer = new ImageView(image);
      TreeMap<String, Integer> bouncer_tree = new TreeMap<>();
      bouncer_tree.put("x", 1);
      bouncer_tree.put("y", 1);
      bouncer.setX(width / 2 - bouncer.getBoundsInLocal().getWidth() / 2);
      bouncer.setY(height / 2 - bouncer.getBoundsInLocal().getHeight() / 2);
      bouncer.setFitHeight(i * 3 + 10);
      bouncer.setFitWidth(i * 3 + 10);
      root.getChildren().add(bouncer);
    }

    // create a place to see the shapes
    return new Scene(root, width, height, background);
  }

  public void step(double delay){

  }

  public static void main(String[] args){
    launch(args);
  }
}
