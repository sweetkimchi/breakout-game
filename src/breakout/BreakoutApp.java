package breakout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
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

  private int velX = 0;
  private int velY = 0;
  private int x = SIZE/2;
  private int y = 800;
  private Scene myScene;
  private ImageView myPaddle;
  private ImageView myBouncer;
  private List<Sprite> map;
  private Rectangle myGrower;
  private String[] image_strings;
  private Image[] sprite_images;
  private Sprite[] player;

  @Override
  public void start(Stage stage) throws Exception {

    map = new ArrayList<Sprite>();
    myScene = setupGame(SIZE, SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();

    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    //starts the game
    animation.play();
  }

  public Scene setupGame(int width, int height, Paint background) {

    // create one top level collection to organize the things in the scene
    Group root = new Group();
    image_strings = new String[10];
    image_strings[0] = PADDLE_IMAGE;
    sprite_images = new Image[10];
    sprite_images[0] = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE)));

//    player = new Sprite(100, 100, 300, 500, sprite_images[0]);
//
//    myGrower = new Rectangle(300, 500, 10,
//        10);

    myGrower = new Rectangle(SIZE/2, SIZE - 100, 150,
        20);
    myGrower.setFill(new ImagePattern(sprite_images[0]));
    root.getChildren().add(myGrower);

      // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    handleKeyInput(scene);

    return scene;
  }

  private void step(double elapsedTime) {

  }

  private void handleKeyInput(Scene scene) {
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
          setVelX(-9);
        }
        if (event.getCode() == KeyCode.DOWN) {
          setVelY(9);
        }
        if (event.getCode() == KeyCode.RIGHT) {
          setVelX(9);
        }
        if (event.getCode() == KeyCode.UP) {
          setVelY(-9);
        }
      }
    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
          setVelX(0);
        }
        if (event.getCode() == KeyCode.DOWN) {
          setVelY(0);
        }
        if (event.getCode() == KeyCode.RIGHT) {
          setVelX(0);
        }
        if (event.getCode() == KeyCode.UP) {
          setVelY(0);
        }
      }
    });

    AnimationTimer animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        x += velX;
        myGrower.setX(x);
        y += velY;
        myGrower.setY(y);
      }
    };
    animation.start();
  }

  public void setVelX(int velX) {
    this.velX = velX;
  }

  public void setVelY(int velY) {
    this.velY = velY;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
