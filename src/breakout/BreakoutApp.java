package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class BreakoutApp extends Application {

  public static final String TITLE = "Example JavaFX";
  public static final int SIZE = 1000;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.WHITE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
  public static final double INERTIA = 5;

  //image files
  private static final String postFix = "-Breakout-Tiles.png";
  public static final String MISSILE_PADDLE_IMAGE = "325" + postFix;
  public static final String TILE_IMAGE = "102" + postFix;
  public static final String BROKEN_TILE_IMAGE = "110" + postFix;
  public static final String BALL_IMAGE = "339" + postFix;
  public static final String BOSS_IMAGE = "000" + postFix;
  public static final String BACKGROUND_IMAGE = "400" + postFix;
  public static final String MISSILE_IMAGE = "346" + postFix;
  private String paddleType = "missilepaddle";
  private int xPaddleVelocity = 0;
  private int yPaddleVelocity = 0;
  private int x = SIZE / 2 - 30;
  private int y = SIZE - 50;
  private int amount_missiles = 50;
  private int number_of_lives = 1;
  private Scene scene_set_up;
  private Scene scene_start;
  private ArrayList<ImageView> image_view;
  private ImageView myBackGround;
  private Rectangle myPaddle;
  private String[] image_strings;
  private Sprite[] player;
  private Pane root;
  private AnimationTimer animation;
  private boolean paused = false;
  private int currentLevel = 1;
  private Text missileLeft;
  private Text winMessage;
  private Text livesLeft;
  private Text loseMessage;
  private Text credit;


  private ArrayList<String> image_files;
  //list storing pointers to respective object
  private List<Sprite> spriteMap;
  private List<Block> blockMap;
  private List<Ball> ballMap;
  private List<DotPaddle> dotPaddleMap;
  private List<Missile> missileMap;
  private List<MissilePaddle> missilePaddleMap;
  private List<PowerUp> powerUpsMap;
  private List<Boss> bossMap;

  @Override
  public void start(Stage stage){

    //initialize maps
    scene_start = setupGame(SIZE, SIZE, BACKGROUND, stage);
    stage.setScene(scene_start);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }


  public Scene setupGame(int width, int height, Paint background, Stage stage){

    // create one top level collection to organize the things in the scene
    root = new Pane();
    scene_set_up = new Scene(root, width, height, null);
    myBackGround = new ImageView(
        new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE)));
    myBackGround.setFitWidth(SIZE);
    myBackGround.setFitHeight(SIZE);
    root.getChildren().add(myBackGround);

    initializeMaps();

    //constructors
    image_strings = new String[10];
    image_strings[0] = MISSILE_PADDLE_IMAGE;
    image_files = new ArrayList<>();
    image_files.add(MISSILE_PADDLE_IMAGE);
    image_files.add(TILE_IMAGE);

    //DISPLAY TEXT
    missileLeft = displayText(50, 50, "Missiles Left: " + Integer.toString(amount_missiles), 20, Color.GOLD);
    livesLeft = displayText(SIZE - 200, 50, "Lives Left: " + Integer.toString(number_of_lives), 20, Color.GOLD);
    credit = displayText(50, 950, "Breakout v1.0\nby Jiyun Hyo", 15, Color.GREENYELLOW);
    //what I need to do here is that I need to make a method in Sprite for each object and
    /*
    ROW COL TYPE IMAGE
     */

    //MAKE MISSILES
    //makes sprite objects and images
    //LEVEL 1
//    for (int col = 0; col < 10; col++) {
//      for (int row = 0; row < 5; row++) {
//        Block block = new Block(10 + col * (SIZE / 10), 80 * row + 100, 80, 20, image_files.get(1), "block", 10);
//        block.upload_image_files();
//        root.getChildren().add(block.getImageView());
//        blockMap.add(block);
//      }
//    }

//    LEVEL 2
    for (int col = 0; col < 10; col++) {
      for (int row = 0; row < 3; row++) {
        Block block = new Block(200 + (SIZE / 10) * col + row * (SIZE / 10),
            100 * row + 100 + col * 60, 80, 20, image_files.get(1), "block", 3);

        block.upload_image_files();
        root.getChildren().add(block.getImageView());
        blockMap.add(block);
      }
    }

    //LEVEL3
//    Boss boss = new Boss(200, 100, 600, 600, BOSS_IMAGE, "boss", 10000);
//    boss.upload_image_files();
//    root.getChildren().add(boss.getImageView());
//    bossMap.add(boss);

    //make a ball and store in ArrayList
    Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", number_of_lives);
    ball.upload_image_files();
    root.getChildren().add(ball.getImageView());
    ballMap.add(ball);

    myPaddle = new Rectangle(x, y, 150, 20);
    //create Paddle
    MissilePaddle missilePaddle = new MissilePaddle(x, y, 150,
        20, MISSILE_PADDLE_IMAGE, 3, myPaddle);



    root.getChildren().add(missilePaddle.getRectangle());

    // create a place to see the shapes
    handleKeyInput(scene_set_up, stage);

    return scene_set_up;
  }

  public void initializeMaps() {
    spriteMap = new ArrayList<Sprite>();
    ballMap = new ArrayList<Ball>();
    blockMap = new ArrayList<Block>();
    dotPaddleMap = new ArrayList<DotPaddle>();
    missileMap = new ArrayList<Missile>();
    missilePaddleMap = new ArrayList<MissilePaddle>();
    powerUpsMap = new ArrayList<PowerUp>();
    bossMap = new ArrayList<Boss>();
  }

  private void updateText(){
    missileLeft.setText("Missiles Left: " + amount_missiles);
    livesLeft.setText("Lives Left: " + number_of_lives);
    //  root.getChildren().add(missileLeft);
  }

  private void handlePaddleVelocity(KeyEvent event){
    if (event.getCode() == KeyCode.LEFT) {
      setxPaddleVelocity(-9);
    }
    if (event.getCode() == KeyCode.RIGHT) {
      setxPaddleVelocity(9);
    }
  }

  private void handleShortCuts(KeyEvent event, Stage stage){
    if (event.getCode() == KeyCode.R){
      cleanUpAndRestart(stage);
    }
    if (event.getCode() == KeyCode.P){
      pause();
    }
  }

  private void handleCheatCode(KeyEvent event){
    if (event.getCode() == KeyCode.L){
      number_of_lives++;
    }
    if (event.getCode() == KeyCode.M){
      amount_missiles += 10;
    }
  }

  private Text displayText(int xCoord, int yCoord, String message, int fontSize, Color color){
    Text text = new Text();
    text.setX(xCoord);
    text.setY(yCoord);
    text.setText(message);
    text.setFill(color);
    text.setFont(new Font("SansSerif", fontSize));
    root.getChildren().add(text);
    return text;
  }


  private void handleKeyInput(Scene scene, Stage stage) {
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() != null){
          animation.start();
        }
        handlePaddleVelocity(event);
        handleShortCuts(event, stage);
        handleCheatCode(event);

        if (event.getCode() == KeyCode.SPACE) {
          if(amount_missiles > 0){
            amount_missiles--;
            for(int i = 0; i <= 1; i++){
              Missile missile = new Missile((int) myPaddle.getX() + (int) (myPaddle.getWidth() - 10) * i, (int) myPaddle.getY() , 3, 10, MISSILE_IMAGE);
              missile.upload_image_files();
              missileMap.add(missile);
              root.getChildren().add(missile.getImageView());
            }
          }
        }
      }
    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if ((event.getCode() == KeyCode.LEFT) || (event.getCode() == KeyCode.RIGHT)){
          for(int i = 0; i < 2; i++){
            xPaddleVelocity = 0;
          }
        }
//        if (event.getCode() == KeyCode.DOWN) {
//          setVelY(0);
//        }

//        if (event.getCode() == KeyCode.UP) {
//          setVelY(0);
//        }
      }
    });

    animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        x += xPaddleVelocity;
        myPaddle.setX(x);
        y += yPaddleVelocity;
        myPaddle.setY(y);
        updateAllSprites();
        updateText();
        checkGameStatus();
      }
    };
  }

  private void checkGameStatus(){
    if(blockMap.isEmpty() && bossMap.isEmpty()){
      animation.stop();
      winMessage = displayText(150, 500, "YOU WON!!", 100, Color.OLIVEDRAB);
      root.getChildren().add(winMessage);
    }
    if(!ballMap.get(0).deadOrAlive()){
      number_of_lives--;
      updateText();
      ballMap.get(0).getImageView().setImage(null);
      ballMap.remove(0);
      Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", number_of_lives);
      ball.upload_image_files();
      root.getChildren().add(ball.getImageView());
      ballMap.add(ball);
      animation.stop();
    }
    if(number_of_lives <= 0){
      animation.stop();
      loseMessage = displayText(600, 950, "YOU LOST!! Click 'R' to Restart", 25, Color.WHITE);
    }
  }

  private void cleanUpAndRestart(Stage stage){
    Levels level = new Levels(currentLevel, stage);
    level.launchLevel();
    animation.stop();
  }

  private void pause(){
    if(!paused){
      animation.stop();
      paused = true;
    }else{
      animation.start();
      paused = false;
    }
  }


  private void updateAllSprites() {
    for (Sprite sprite : spriteMap) {
      sprite.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (Block block : blockMap) {
      block.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (Ball ball : ballMap) {
      ball.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (Boss boss : bossMap) {
      boss.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (Missile missile : missileMap) {
      missile.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (DotPaddle dotPaddle : dotPaddleMap) {
      dotPaddle.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (PowerUp powerUp : powerUpsMap) {
      powerUp.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
    for (MissilePaddle missilePaddle : missilePaddleMap) {
      missilePaddle.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap);
    }
  }

  public void shoot(Sprite sprite) {

  }

  public void setxPaddleVelocity(int xPaddleVelocity) {
    this.xPaddleVelocity = xPaddleVelocity;
  }

  public void setyPaddleVelocity(int yPaddleVelocity) {
    this.yPaddleVelocity = yPaddleVelocity;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
