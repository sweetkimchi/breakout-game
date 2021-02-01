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
  private Pane root;
  private AnimationTimer animation;
  private boolean paused = false;
  private int currentLevel = 1;
  private Text missileLeft;
  private Text winMessage;
  private Text livesLeft;
  private Text loseMessage;
  private Text credit;
  private Text pauseMessage;
  private Text currentLevelText;


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
  public void start(Stage stage) {

    //initialize maps
    scene_start = setupGame(SIZE, SIZE, BACKGROUND, stage);
    stage.setScene(scene_start);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }


  public Scene setupGame(int width, int height, Paint background, Stage stage) {

    // create one top level collection to organize the things in the scene
    root = new Pane();
    scene_set_up = new Scene(root, width, height, null);
    setBackgroundImage();
    initializeMaps();
    displayStartingText();

    //MAKE MISSILES
    //makes sprite objects and images
    //LEVEL 1
    // 1 10 5 10 80 100 80 20 102 0 10 (ten total)
    //col row3rd and 4th are constants for y coordinate, width, height, prefix of image,
    for (int row = 0; row < 10; row++) {
      for (int column = 0; column < 5; column++) {
        Block block = new Block(10 + row * (SIZE / 10), 80 * column + 100, 80, 20, TILE_IMAGE, BROKEN_TILE_IMAGE,"block", 10);
        block.upload_image_files();
        root.getChildren().add(block.getImageView());
        blockMap.add(block);
      }
    }

    //LEVEL 1
    //1 10 5 10 80 100 80 20 102 0 10 (ten total)
    //col row3rd and 4th are constants for y coordinate, width, height, prefix of image,
//    LEVEL 2
//    2 10 3 200 0
    //row col row col
    //LEVEL3
//    //3 1 1 200 100 600 600 000 1 10000 (ten total)



    //LEVEL3
    //3 1 1 200 100 600 600 000 1 10000 (ten total)
//    Boss boss = new Boss(200, 100, 600, 600, BOSS_IMAGE, "boss", 10000);
//    boss.upload_image_files();
//    root.getChildren().add(boss.getImageView());
//    bossMap.add(boss);
    makeBall();
    makePaddle();
    handleKeyInput(scene_set_up, stage);
    runAnimation();
    return scene_set_up;
  }

  private void setBackgroundImage(){
    myBackGround = new ImageView(
        new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE)));
    myBackGround.setFitWidth(SIZE);
    myBackGround.setFitHeight(SIZE);
    root.getChildren().add(myBackGround);
  }

  private void displayStartingText(){
    missileLeft = displayText(50, 50, "Missiles Left: " + Integer.toString(amount_missiles), 20,
        Color.GREENYELLOW);
    livesLeft = displayText(SIZE - 200, 50, "Lives Left: " + Integer.toString(number_of_lives), 20,
        Color.GREENYELLOW);
    credit = displayText(50, 950, "Breakout v1.0\nby Jiyun Hyo", 15, Color.GREENYELLOW);
    currentLevelText = displayText(460, 50, "Level " + currentLevel, 30,Color.GOLD);
  }

  private void makeBall(){
    Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", number_of_lives);
    ball.upload_image_files();
    root.getChildren().add(ball.getImageView());
    ballMap.add(ball);
  }

  private void makePaddle(){
    myPaddle = new Rectangle(x, y, 150, 20);
    //create Paddle
    MissilePaddle missilePaddle = new MissilePaddle(x, y, 150,
        20, MISSILE_PADDLE_IMAGE, 3, myPaddle);

    root.getChildren().add(missilePaddle.getRectangle());
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

  private void updateText() {
    missileLeft.setText("Missiles Left: " + amount_missiles);
    livesLeft.setText("Lives Left: " + number_of_lives);
    //  root.getChildren().add(missileLeft);
  }

  private void handlePaddleVelocity(KeyEvent event) {
    if (event.getCode() == KeyCode.LEFT) {
      setxPaddleVelocity(-9);
    }
    if (event.getCode() == KeyCode.RIGHT) {
      setxPaddleVelocity(9);
    }
  }

  private void handleShortCuts(KeyEvent event, Stage stage) {
    if (event.getCode() == KeyCode.R) {
      cleanUpAndRestart(stage);
    }
    if (event.getCode() == KeyCode.P) {

      pause();
    }
  }

  private void handleCheatCode(KeyEvent event) {
    if (event.getCode() == KeyCode.L) {
      if(number_of_lives == 0 && paused){
        paused = false;
      }
      number_of_lives++;
      loseMessage.setVisible(false);
    }
    if (event.getCode() == KeyCode.M) {
      amount_missiles += 10;
    }
  }

  private Text displayText(int xCoord, int yCoord, String message, int fontSize, Color color) {
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
        if (event.getCode() != null && !paused) {
          animation.start();
        }
        handlePaddleVelocity(event);
        handleShortCuts(event, stage);
        handleCheatCode(event);
        handleShoot(event);

      }
    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if ((event.getCode() == KeyCode.LEFT) || (event.getCode() == KeyCode.RIGHT)) {
          for (int i = 0; i < 2; i++) {
            xPaddleVelocity = 0;
          }
        }
      }
    });
  }

  private void runAnimation(){
    animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        updatePaddleVelocity();
        updateAllSprites();
        updateText();
        checkGameStatus();
      }
    };
  }

  private void updatePaddleVelocity(){
    x += xPaddleVelocity;
    myPaddle.setX(x);
    y += yPaddleVelocity;
    myPaddle.setY(y);
  }

  private void checkGameStatus() {
    if (!paused && pauseMessage != null){
      pauseMessage.setVisible(false);
    }
    if (blockMap.isEmpty() && bossMap.isEmpty()) {
      animation.stop();
      winMessage = displayText(300, 500, "YOU WON!!", 100, Color.GREENYELLOW);
      root.getChildren().add(winMessage);
    }
    if (!ballMap.get(0).deadOrAlive()) {
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
    if (number_of_lives <= 0 && !paused) {
      animation.stop();
      paused = true;
      loseMessage = displayText(600, 850,
          "YOU LOST!!\nClick 'R' to Restart\nClick 'L' to Re-load Lives", 25, Color.GREENYELLOW);

    }
  }

  private void cleanUpAndRestart(Stage stage) {
    Levels level = new Levels(currentLevel, stage);
    level.launchLevel(currentLevel);
    animation.stop();
  }

  private void loadLevelFromFile(){
    //LEVEL 1
    // 1 10 5 10 80 100 80 20 102 0 10 (ten total)
    //col row3rd and 4th are constants for y coordinate, width, height, prefix of image,
//    LEVEL 2
//  2 10 3 200 0
    //row col row col
    //LEVEL3
    //3 1 1 200 100 600 600 000 1 10000 (ten total)
  }

  private void pause() {
    if (!paused) {
      animation.stop();
      pauseMessage = displayText(300, 400, "PAUSED\n\nPress 'P' to Continue",50,Color.GOLD);
      paused = true;
    } else {
      animation.start();
      pauseMessage.setVisible(false);
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

  public void handleShoot(KeyEvent event) {
    if (event.getCode() == KeyCode.SPACE) {
      if (amount_missiles > 0) {
        amount_missiles--;
        for (int i = 0; i <= 1; i++) {
          Missile missile = new Missile(
              (int) myPaddle.getX() + (int) (myPaddle.getWidth() - 10) * i,
              (int) myPaddle.getY(), 3, 10, MISSILE_IMAGE);
          missile.upload_image_files();
          missileMap.add(missile);
          root.getChildren().add(missile.getImageView());
        }
      }
    }
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
