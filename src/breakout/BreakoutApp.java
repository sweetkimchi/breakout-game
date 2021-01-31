package breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
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
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakoutApp extends Application {

  public static final String TITLE = "Example JavaFX";
  public static final int SIZE = 1000;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.CADETBLUE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;

  //image files
  private static final String postFix = "-Breakout-Tiles.png";
  public static final String PADDLE_IMAGE = "325" + postFix;
  public static final String TILE_IMAGE = "220" + postFix;
  public static final String BALL_IMAGE = "339" + postFix;



  public static final int BOUNCER_SPEED = 30;
  public static final Paint MOVER_COLOR = Color.PLUM;
  public static final int MOVER_SIZE = 50;
  public static final int MOVER_SPEED = 5;
  public static final Paint GROWER_COLOR = Color.BISQUE;
  public static final double GROWER_RATE = 1.1;
  public static final int GROWER_SIZE = 50;

  private int velX = 0;
  private int velY = 0;
  private int x = SIZE/2 - 30;
  private int y = SIZE - 50;
  private Scene scene_set_up_game;
  private Scene scene_start;
  private ArrayList<ImageView> image_view;
  private ImageView myBouncer;
  private Rectangle myPaddle;
  private String[] image_strings;
  private Sprite[] player;



  private ArrayList<String> image_files;
  //list storing pointers to respective object
  private List<Sprite> spriteMap;
//  private List<Block> blockMap;
//  private List<Ball> ballMap;
//  private List<DotPaddle> dotPaddleMap;
//  private List<Missile> missileMap;
//  private List<MissilePaddle> missilePaddleMap;
//  private List<PowerUp> powerUpsMap;
//  private List<Boss> bossMap;

  @Override
  public void start(Stage stage) throws Exception {

    //initialize maps
    initializeMap();
    scene_start = setupGame(SIZE, SIZE, BACKGROUND);
    stage.setScene(scene_start);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();

//    //cool effect
//    for(int i = 1; i < 10000 * spriteMap.size(); i++){
//      if(i % 10000 == 0){
//        Sprite sprite = spriteMap.get(10000 / i);
//        sprite.upload_image_files();
//      }
//      else if(i % 20000 == 0){
//        Sprite sprite = spriteMap.get(20000 / i);
//        sprite.upload_image_files();
//      }
//      TimeUnit.MILLISECONDS.sleep(1);
//    }
  }

  public Scene setupGame(int width, int height, Paint background) throws InterruptedException {

    // create one top level collection to organize the things in the scene
    Group root = new Group();
    scene_set_up_game = new Scene(root, width, height, background);

    //constructors
    image_strings = new String[10];
    image_strings[0] = PADDLE_IMAGE;
    image_files = new ArrayList<>();
    image_files.add(PADDLE_IMAGE);
    image_files.add(TILE_IMAGE);

    //what I need to do here is that I need to make a method in Sprite for each object and
    Sprite dum = new Block(10 + 1 * (SIZE/15), 50 * 1 + 100, 50, 50, image_files.get(1));
    //add image

    //makes sprite objects and images
    for(int col = 0; col < 15; col++){
      for(int row = 0; row < 5; row++){
        Sprite sprite = new Sprite(10 + col * (SIZE/15), 50 * row + 100, 50, 50, image_files.get(1));
        sprite.upload_image_files();
        root.getChildren().add(sprite.getImageView());
        spriteMap.add(sprite);
      }
    }

    //make a ball and store in ArrayList
    Sprite ball = new Ball(SIZE/2, SIZE - 100, 20, 20, BALL_IMAGE);
    ball.upload_image_files();
    root.getChildren().add(ball.getImageView());
    System.out.println(spriteMap.size());
    spriteMap.add(ball);

    for(Sprite sprite : spriteMap){
      System.out.println(sprite);
    }

    myPaddle = new Rectangle(SIZE/2, SIZE - 100, 150,
        20);
    Image image_baby = new Image(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(image_files.get(0))));

    myPaddle.setFill(new ImagePattern(image_baby));
    root.getChildren().add(myPaddle);

      // create a place to see the shapes
    handleKeyInput(scene_set_up_game);

    return scene_set_up_game;
  }

  private void animationSetUp(){
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    //starts the game
    animation.play();
  }

  private void step(double delay){
    System.out.println("okay");
  }

  private void initializeMap(){
    spriteMap = new ArrayList<Sprite>();
//    ballMap = new ArrayList<Ball>();
//    blockMap = new ArrayList<Block>();
//    dotPaddleMap = new ArrayList<DotPaddle>();
//    missileMap = new ArrayList<Missile>();
//    missilePaddleMap = new ArrayList<MissilePaddle>();
//    powerUpsMap = new ArrayList<PowerUp>();
//    bossMap = new ArrayList<Boss>();
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
        myPaddle.setX(x);
        y += velY;
        myPaddle.setY(y);
        for(Sprite sprite : spriteMap){
          sprite.update(sprite.getImageView());
        }

      //  System.out.println("check");
      }
    };
    animation.start();
  }

  public void shoot(Sprite sprite){

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
