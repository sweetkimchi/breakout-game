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


public class BreakoutApp extends Application {

  public static final String TITLE = "Example JavaFX";
  public static final int SIZE = 1000;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.CADETBLUE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
  public static final String PADDLE_IMAGE = "325-Breakout-Tiles.png";
  public static final String BALL_IMAGE = "339-Breakout-Tiles.png";
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
    //starts the game
    animation.play();
  }

  public Scene setupGame(int width, int height, Paint background) {

    // create one top level collection to organize the things in the scene
    Group root = new Group();
    // make some shapes and set their properties
    Image paddle_image = new Image(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)));

    Image ball_image = new Image(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE)));

    for (int i = 0; i < 1; i++) {
      ImageView ball = new ImageView(ball_image);
      TreeMap<String, Integer> ball_tree = new TreeMap<>();
      ball_tree.put("x", 1);
      ball_tree.put("y", 1);
      ball.setX(ball.getBoundsInLocal().getWidth());
      ball.setY(ball.getBoundsInLocal().getHeight());
      ball.setFitHeight(ball.getBoundsInLocal().getHeight() / 5);
      ball.setFitWidth(ball.getBoundsInLocal().getWidth()/ 5);
      root.getChildren().add(ball);

      // create a place to see the shapes

    }


      myPaddle = new ImageView(paddle_image);
      myPaddle.setX(200);
      myPaddle.setY(600);
      myPaddle.setFitHeight(myPaddle.getBoundsInLocal().getHeight() / 5);
      myPaddle.setFitWidth(myPaddle.getBoundsInLocal().getWidth() / 5);
      root.getChildren().add(myPaddle);

      // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);

    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    return scene;
  }

  private void step(double elapsedTime) {
    System.out.println("fwae");
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case RIGHT -> myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
      case LEFT -> myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
//      case UP -> myPaddle.setY(myPaddle.getY() - MOVER_SPEED);
//      case DOWN -> myPaddle.setY(myPaddle.getY() + MOVER_SPEED);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
