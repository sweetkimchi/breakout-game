/**
 * Purpose: Handles the main parts of the game: game engine, GUI, keyboard input control, and
 * updating all sprites.
 * Assumptions: Assumes that the user feeds correct input values to the program.
 * Dependencies: Declares Ball, MissilePaddle, Levels, Boss, and Block objects. Also
 * creates Javafx objects such as Panel, JFrame, and Scene.
 * Example: Used to launch the game as shown below.
 * ``` public void launchLevel(int currentLevel) { BreakoutApp breakout = new
 * BreakoutApp(); Scene scene = breakout.setupGame(SIZE, SIZE, stage, currentLevel);
 * stage.setScene(scene); stage.setTitle(TITLE); stage.show(); stage.requestFocus(); }
 * ```
 * Any Other Detail: The longest class in the project.
 * @author Ji Yun Hyo
 */

package breakout;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class BreakoutApp extends Application {

  public static final String TITLE = "Breakout Game";
  public static final int SIZE = 1000;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  //image files
  private static final String postFix = "-Breakout-Tiles.png";
  public static final String MISSILE_PADDLE_IMAGE = "325" + postFix;
  public static final String FAST_PADDLE_IMAGE = "310" + postFix;
  public static final String BALL_IMAGE = "340" + postFix;
  public static final String POWERFUL_BALL = "402" + postFix;
  public static final String BACKGROUND_IMAGE = "400" + postFix;
  public static final String MISSILE_IMAGE = "346" + postFix;
  private final int yPaddleVelocity = 0;
  private final int numberOfLives = 3;
  private int xPaddleVelocity = 0;
  private int x = SIZE / 2 - 30;
  private int y = SIZE - 50;
  private int amount_missiles = 50;
  private Scene scene_set_up;
  private Scene startScene;
  private ImageView myBackGround;
  private Rectangle myPaddle;
  private Pane root;
  private AnimationTimer animation;
  private boolean paused = false;
  private int currentLevel = 1;
  private Text missileLeft;
  private Text winMessage;
  private Text livesLeft;
  private Text loseMessage;
  private Text pauseMessage;
  private Stage myStage;
  private int paddleSpeed = 9;


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

  public static void main(String[] args) {
    displaySplashScreen();
    launch(args);

  }

  private static void displaySplashScreen() {
    JFrame jFrame = new JFrame();
    jFrame.setTitle("Breakout Game");
    jFrame.setSize(400, 900);
    jFrame.add(new JLabel(
        "<html>Note: Bigger monitor (16 inches or above) <br> recommended<h1 style=\"color:green;\">How to Play</h1> Destory all blocks. Maneuver the direction of the ball <br> by controlling where on the paddle it hits. <br><br> Challenge: Try to get through all levels without <br> cheat code. <br><br> Tip 1: When fighting the boss, try to make the ball <br> approach the boss from the sides because <br>sides are the boss's vulnerability.<br><br> Tip 2: Try to power up the ball with carrots. <br><br> SECRET TIP: Try to level up the paddle with 'G' to <br>the fullest so that the paddle covers the entire <br>bottom. Then, keep pressing 'L' <br>to replesh lives. Sit back and watch. <h1 style=\"color:blue;\">Basic Abilities</h1><p>LEFT - move left<br> RIGHT - move right <br> SPACE - shoot missiles <br> N - increase speed of paddle by 4 <br> B - return to default paddle speed <h1 style=\"color:Red;\">Settings </h1><p>R - restart game <br> P - pause/resume <h1 style=\"color:green;\">Cheat Code</h1> L - add one life <br> M - 10 more missiles <br> F - power up the ball <br> D - decrease ball size <br> G - level up paddle/no level up limit <br> V - decrease size of paddle <br> 0 ~ 9 - jump to level (or the maximum level) </html>",
        SwingConstants.CENTER), BorderLayout.CENTER);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setVisible(true);

  }

  @Override
  public void start(Stage stage) {
    myStage = stage;
    startScene = setupGame(SIZE, SIZE, stage, 1);
    stage.setScene(startScene);
    stage.setTitle(TITLE);
    stage.show();
    stage.requestFocus();
  }

  public Scene setupGame(int width, int height, Stage stage, int currentLevel) {
    // create one top level collection to organize the things in the scene
    this.currentLevel = currentLevel;
    root = new Pane();
    scene_set_up = new Scene(root, width, height, null);
    setBackgroundImage();
    initializeMaps();
    displayStartingText();
    loadLevelFromFile(currentLevel, stage);
    makeBall();
    createPaddle(MISSILE_PADDLE_IMAGE, x, y);
    handleKeyInput(scene_set_up, stage);
    runAnimation();
    return scene_set_up;
  }

  private void setBackgroundImage() {

    myBackGround = new ImageView(
        new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE)));
    myBackGround.setFitWidth(SIZE);
    myBackGround.setFitHeight(SIZE);
    root.getChildren().add(myBackGround);

  }

  private void displayStartingText() {
    missileLeft = displayText(50, 50, "Missiles Left: " + amount_missiles, 20,
        Color.GREENYELLOW);
    livesLeft = displayText(SIZE - 200, 50, "Lives Left: " + numberOfLives, 20,
        Color.GREENYELLOW);
    Text credit = displayText(50, 950, "Breakout v1.0\nby Jiyun Hyo", 15, Color.GREENYELLOW);
    Text currentLevelText = displayText(460, 50, "Level " + currentLevel, 30, Color.GOLD);
  }

  private void makeBall() {
    Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", numberOfLives);
    ball.upload_image_files();
    root.getChildren().add(ball.getImageView());
    ballMap.add(ball);
  }

  private void createPaddle(String IMAGE_FILE, int xCoord, int yCoord) {
    myPaddle = new Rectangle(xCoord, yCoord, 150, 20);
    //create Paddle
    MissilePaddle missilePaddle = new MissilePaddle(xCoord, yCoord, 150,
        20, IMAGE_FILE, 3, myPaddle);
    missilePaddleMap.add(missilePaddle);
    root.getChildren().add(missilePaddle.getRectangle());
  }

  private void initializeMaps() {
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
    livesLeft.setText("Lives Left: " + missilePaddleMap.get(0).lives);
    //  root.getChildren().add(missileLeft);
  }

  private void handlePaddleVelocity(KeyEvent event) {
    if (event.getCode() == KeyCode.LEFT) {
      setxPaddleVelocity(-paddleSpeed);
    }
    if (event.getCode() == KeyCode.RIGHT) {
      setxPaddleVelocity(paddleSpeed);
    }
  }

  private void handleShortCuts(KeyEvent event, Stage stage) throws FileNotFoundException {
    if (event.getCode() == KeyCode.R) {
      cleanUpAndRestart(stage);
    }
    if (event.getCode() == KeyCode.P) {

      pause();
    }
  }

  private void handleCheatCode(KeyEvent event, Stage stage) {
    if (event.getCode() == KeyCode.L) {
      if (missilePaddleMap.get(0).lives == 0 && paused) {
        paused = false;
      }
      missilePaddleMap.get(0).lives++;
      loseMessage.setVisible(false);
    }
    if (event.getCode() == KeyCode.M) {
      amount_missiles += 10;
    }
    //make ball bigger and able to go through bricks (stronger damage)
    if (event.getCode() == KeyCode.F) {
      setBallImage(10);
    }
    //decrease ball size and weaker (lower damage)
    if (event.getCode() == KeyCode.D) {
      setBallImage(-10);
    }

    if (event.getCode() == KeyCode.G) {
      myPaddle.setWidth(myPaddle.getWidth() * 1.2);
    }
    if (event.getCode() == KeyCode.V) {
      myPaddle.setWidth(myPaddle.getWidth() / 1.2);
    }
    if (event.getCode() == KeyCode.Y && blockMap.isEmpty() && bossMap.isEmpty()) {
      currentLevel++;
      try {
        cleanUpAndRestart(stage);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private void setBallImage(int increment) {
    if (ballMap.get(0).getImageView().getFitHeight() > 15 || increment > 0) {
      ballMap.get(0).getImageView()
          .setFitWidth(ballMap.get(0).getImageView().getFitWidth() + increment);
      ballMap.get(0).getImageView()
          .setFitHeight(ballMap.get(0).getImageView().getFitHeight() + increment);
      if (ballMap.get(0).getImageView().getFitHeight() > 20) {
        ballMap.get(0).getImageView().setImage(new Image(Objects.requireNonNull(
            getClass().getClassLoader().getResourceAsStream(POWERFUL_BALL))));
      } else {
        ballMap.get(0).getImageView().setImage(new Image(Objects.requireNonNull(
            getClass().getClassLoader().getResourceAsStream(BALL_IMAGE))));
      }
    }

  }

  private void skipToLevelIndicatedByKeyInput(KeyEvent event, Stage stage) {
    try {
      currentLevel = Math
          .max(1, Math.min(3, Integer.parseInt(event.getCode().toString().substring(5))));
      cleanUpAndRestart(stage);
    } catch (NumberFormatException | FileNotFoundException integerException) {
      return;
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
        try {
          handleShortCuts(event, stage);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        handleCheatCode(event, stage);
        gameControlKeys(event);
        skipToLevelIndicatedByKeyInput(event, stage);

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

  private void runAnimation() {
    animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        updatePaddleVelocity();
        updateAllSprites();
        updateText();
        checkGameStatus();
        checkPaddleSpeed();
      }
    };
  }

  private void updatePaddleVelocity() {
    x += xPaddleVelocity;
    myPaddle.setX(x);
    y += yPaddleVelocity;
    myPaddle.setY(y);
  }

  private void checkGameStatus() {
    if (!paused && pauseMessage != null) {
      pauseMessage.setVisible(false);
    }
    if (blockMap.isEmpty() && bossMap.isEmpty()) {
      animation.stop();
      winMessage = displayText(300, 500, "YOU WON!!\n Press 'Y' to go to next level", 50,
          Color.GREENYELLOW);
      root.getChildren().add(winMessage);
    }
    if (!ballMap.get(0).deadOrAlive()) {
      missilePaddleMap.get(0).lives--;
      updateText();
      ballMap.get(0).getImageView().setImage(null);
      ballMap.remove(0);
      Ball ball = new Ball(SIZE / 2, SIZE - 100, 20, 20, BALL_IMAGE, "ball", numberOfLives);
      ball.upload_image_files();
      root.getChildren().add(ball.getImageView());
      ballMap.add(ball);
      animation.stop();
    }
    if (missilePaddleMap.get(0).lives <= 0 && !paused) {
      animation.stop();
      paused = true;
      loseMessage = displayText(600, 850,
          "YOU LOST!!\nPress 'R' to Restart\nPress 'L' to Re-load Lives", 25, Color.GREENYELLOW);

    }

  }

  private void cleanUpAndRestart(Stage stage) throws FileNotFoundException {
    Levels level = new Levels(currentLevel, stage);
    level.launchLevel(currentLevel);
    animation.stop();
  }

  private void loadLevelFromFile(int levelTemplate, Stage stage) {
    Levels launch_helper = new Levels(currentLevel, stage);
    ArrayList<String> levelDescriptionsFromFile = launch_helper.loadFromFiles();

    if (levelTemplate == 1) {
      loadStationaryBlockLevels(levelDescriptionsFromFile);
    } else if (levelTemplate == 2) {
      loadMovingBlockLevels(levelDescriptionsFromFile);
    } else if (levelTemplate <= 9) {
      loadBossLevels(levelDescriptionsFromFile);
    }
  }


  //not refactorable but is necessary because each template is unique and I want to save them
  //as "maps" in possibly future versions
  private void loadStationaryBlockLevels(ArrayList<String> levelDescriptionsFromFile) {
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

  private void loadMovingBlockLevels(ArrayList<String> levelDescriptionsFromFile) {
    extracted(levelDescriptionsFromFile);
  }

  private void extracted(ArrayList<String> levelDescriptionsFromFile) {
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

  private void loadBossLevels(ArrayList<String> levelDescriptionsFromFile) {
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

  private void pause() {
    if (!paused) {
      animation.stop();
      pauseMessage = displayText(300, 400, "PAUSED\n\nPress 'P' to Continue", 50, Color.GOLD);
      paused = true;
    } else {
      animation.start();
      pauseMessage.setVisible(false);
      paused = false;
    }
  }

  private void updateAllSprites() {
    for (Sprite sprite : spriteMap) {
      sprite
          .update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
              root, ballMap.get(0), missilePaddleMap);
    }
    for (Block block : blockMap) {
      block.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
          root, ballMap.get(0), missilePaddleMap);
    }
    for (Ball ball : ballMap) {
      ball.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
          root, ballMap.get(0), missilePaddleMap);
    }
    for (Boss boss : bossMap) {
      boss.update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
          root, ballMap.get(0), missilePaddleMap);
    }
    for (Missile missile : missileMap) {
      missile
          .update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
              root, ballMap.get(0), missilePaddleMap);
    }
    for (DotPaddle dotPaddle : dotPaddleMap) {
      dotPaddle
          .update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
              root, ballMap.get(0), missilePaddleMap);
    }
    for (PowerUp powerUp : powerUpsMap) {
      powerUp
          .update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
              root, ballMap.get(0), missilePaddleMap);
    }
    for (MissilePaddle missilePaddle : missilePaddleMap) {
      missilePaddle
          .update(SECOND_DELAY, myPaddle, blockMap, bossMap, missileMap, powerUpsMap, currentLevel,
              root, ballMap.get(0), missilePaddleMap);
    }
  }

  public void gameControlKeys(KeyEvent event) {
    if (event.getCode() == KeyCode.SPACE) {
      if (amount_missiles > 0) {
        amount_missiles--;
        for (int i = 0; i <= 1; i++) {
          Missile missile = new Missile(
              (int) myPaddle.getX() + (int) (myPaddle.getWidth() - 10) * i,
              (int) myPaddle.getY(), 6, 20, MISSILE_IMAGE);
          missile.upload_image_files();
          missileMap.add(missile);
          root.getChildren().add(missile.getImageView());
        }
      }
    }
    if (event.getCode() == KeyCode.N && paddleSpeed < 18) {
      paddleSpeed += 4;
    }
    if (event.getCode() == KeyCode.B && paddleSpeed > 9) {
      paddleSpeed = 9;

    }
  }

  private void checkPaddleSpeed() {
    if (paddleSpeed > 9) {
      myPaddle.setFill(new ImagePattern(new Image(
          Objects.requireNonNull(
              this.getClass().getClassLoader().getResourceAsStream(FAST_PADDLE_IMAGE)))));
    } else {
      myPaddle.setFill(new ImagePattern(new Image(
          Objects.requireNonNull(
              this.getClass().getClassLoader().getResourceAsStream(MISSILE_PADDLE_IMAGE)))));
    }
  }

  private void setxPaddleVelocity(int xPaddleVelocity) {
    this.xPaddleVelocity = xPaddleVelocity;
  }
}
