/**
 * Purpose: Stores and updates sprite information. Creates and destroys sprites. Checks for collision
 * between sprites.
 * Assumptions: BreakoutApp is properly using root (a Pane object) to display GUI. Less than 20 total
 * sprites will be created.
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

import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

  private final String LEVEL_UP_POWER_UP = "344-Breakout-Tiles.png";
  private final String BIGGER_SIZED_BALL = "403-Breakout-Tiles.png";
  private final String MISSILE_IMAGE = "346-Breakout-Tiles.png";
  private final double powerUPProbability = 0.05;
  private final double changeDirectionProbability = 0.005;
  private final double missileProbability = 0.1;
  public boolean alive = true;
  private Image image;
  private String IMAGE;
  private String className;
  private double xDirection;
  private double yDirection;
  private int speed;
  private ImageView imageView;
  private int xCoord;
  private int yCoord;
  private String LOW_HEALTH_IMAGE;
  private Pane root;
  private int paddleLevel;
  private List<MissilePaddle> missilePaddles;

  /**
   * Purpose: Initializes Sprite objects.
   * Assumptions: All input parameters are correct.
   * @param xCoord
   * @param yCoord
   * @param width
   * @param height
   * @param FULL_HEALTH_IMAGE image to be used when Sprite has close to full health
   * @param LOW_HEALTH_IMAGE image to be used when Sprite has close to no health
   * @param type name of sub-class that called the constructor
   */
  public Sprite(int xCoord, int yCoord, int width, int height, String FULL_HEALTH_IMAGE,
      String LOW_HEALTH_IMAGE, String type) {
    super(xCoord, yCoord, width, height);
    this.IMAGE = FULL_HEALTH_IMAGE;
    className = type;
    setImageView(this.IMAGE);
    this.speed = 400;
    paddleLevel = 1;
    //necessary set up to ensure the paddle's movement is smooth
    xDirection = 1;
    yDirection = 1;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.LOW_HEALTH_IMAGE = LOW_HEALTH_IMAGE;
    this.upload_image_files();
  }

  /**
   * Purpose: Allows Sprite objects to set a new ImageView in case an event in BreakoutApp finds it necessary.
   * Assumption: IMAGE file is properly formatted and contains a valid image file name.
   * Exception: IMAGE may be Null. The image file name might not exist.
   * @param IMAGE name of the image file under 'doc' folder
   * @return imageView object with the new sprite image
   */
  public ImageView setImageView(String IMAGE) {
    image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
    imageView = new ImageView();
    return imageView;
  }

  /**
   * Purpose: Assigns an image to this Sprite object.
   * Assumption: imageView from this Sprite object exists.
   * Exceptions: Null pointer exception if imageView does not exist
   * @return image added to this Sprite
   */
  public ImageView upload_image_files() {
    this.imageView.setImage(this.image);
    this.imageView.setFitWidth(this.getWidth());
    this.imageView.setFitHeight(this.getHeight());
    this.imageView.setX(xCoord);
    this.imageView.setY(yCoord);
    return imageView;
  }

  private String getClassName() {
    return this.className;
  }

  /**
   * Purpose: Allows root in BreakoutApp to add imageView of this Sprite to be displayed using GUI.
   * Assumption: imageView exists.
   * Exception: No known exception.
   * @return
   */
  public ImageView getImageView() {
    return imageView;
  }

  /**
   * Purposes: Allows BreakoutApp to loop through all sprites created and have each sprite object
   * call its own update method.
   * Assumption: There are no more than 20 types of sprites. 'root' and 'ball' have proper values.
   * Exception: No known exception.
   * @param elapsedTime Time for one frame.
   * @param myPaddle Rectangle object defined in BreakoutApp as the paddle.
   * @param blocks List containing pointers to Block objects that are created
   * @param boss List containing pointers to Boss objects that are created
   * @param missileMap List containing pointers to Missile objects that are created
   * @param powerUps List containing pointers to PowerUp objects that are created
   * @param currentLevel
   * @param root Pane defined in BreakoutApp. Used to add sprites onto GUI.
   * @param ball Ball created in BreakoutApp.
   * @param missilePaddles List containing pointers to MissilePaddle objects that are created
   */
  public void update(double elapsedTime, Rectangle myPaddle, List<Block> blocks, List<Boss> boss,
      List<Missile> missileMap, List<PowerUp> powerUps, int currentLevel, Pane root, Ball ball,
      List<MissilePaddle> missilePaddles) {
    this.root = root;
    this.missilePaddles = missilePaddles;
    if (getClassName().equals("missile")) {
      this.getImageView()
          .setY(this.getImageView().getY() - this.speed * 2 * elapsedTime);
    }
    if (getClassName().equals("powerup")) {
      this.getImageView()
          .setY(this.getImageView().getY() + this.speed * 0.5 * elapsedTime);
    }
    if (getClassName().equals("ball")) {
      this.getImageView()
          .setX(this.getImageView().getX() - this.speed * elapsedTime * this.xDirection);
      this.getImageView()
          .setY(this.getImageView().getY() - this.speed * elapsedTime * this.yDirection);
    } else if (getClassName().equals("block") && currentLevel > 1) {
      if (Math.random() < changeDirectionProbability) {
        xDirection *= -1;
      }
      this.getImageView()
          .setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    } else if (getClassName().equals("boss")) {
      if (Math.random() < changeDirectionProbability) {
        xDirection *= -1;
      }
      this.getImageView()
          .setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    }
    checkBoundary(myPaddle, blocks, boss, missileMap, powerUps, ball);
  }

  private void checkBoundary(Rectangle myPaddle, List<Block> blocks, List<Boss> boss,
      List<Missile> missile, List<PowerUp> powerUps, Ball ball) {
    double xPos = this.getImageView().getX();
    double yPos = this.getImageView().getY();
    checkX(xPos);
    checkY(yPos, myPaddle);
    checkCollision(blocks, boss, missile, powerUps, myPaddle, ball);
  }

  private void checkX(double xPos) {
    if (this.getClassName().equals("block") || this.getClassName().equals("boss")) {
      if (this.getImageView().getBoundsInParent().getMinX() <= 10) {
        this.xDirection = 1;
      } else if (this.getImageView().getBoundsInParent().getMaxX() >= 980) {
        this.xDirection = -1;
      }
      //THIS IS VERY IMPORTANT - NEEDED IN ORDER TO FIX THE BUG WHERE THE BLOCKS GET TRAPPED AT THE EDGES
    } else {
      if (this.getImageView().getBoundsInParent().getMinX() <= 10
          || this.getImageView().getBoundsInParent().getMaxX() >= 980) {
        this.xDirection *= -1;
      }
    }
  }

  private void checkY(double yPos, Rectangle myPaddle) {
    if (this.getImageView().getBoundsInParent().getMinY() <= 0) {
      this.yDirection *= -1;
    }
    if (this.getClassName().equals("ball") && this.imageView
        .getBoundsInParent()
        .intersects(myPaddle.getBoundsInParent())) {
      this.yDirection *= -1;
      if (this.getImageView().getBoundsInParent().getCenterX()
          < myPaddle.getX() + myPaddle.getWidth() / 2) {
        handlePaddleDeflection(0, myPaddle.getX() + myPaddle.getWidth() / 2,
            this.getImageView().getBoundsInParent().getCenterX());
      } else {
        handlePaddleDeflection(1, myPaddle.getX() + myPaddle.getWidth() / 2,
            this.getImageView().getBoundsInParent().getCenterX());
      }
    }
    if (this.getClassName().equals("ball")
        && this.getImageView().getBoundsInParent().getMaxY() >= 1000) {
      alive = false;
    }
    if (this.getClassName().equals("powerup")
        && this.getImageView().getBoundsInParent().getMaxY() >= 1000) {
      root.getChildren().remove(this);
    }


  }

  private void handlePaddleDeflection(int num, double centerOfPaddle, double centerOfBall) {
    if (num == 0) {
      this.xDirection = 1 + Math.abs(centerOfBall - centerOfPaddle) / 100;
    } else {
      this.xDirection = -1 - Math.abs(centerOfBall - centerOfPaddle) / 100;
    }

  }

  /**
   * Purpose: Returns the status of the ball so that BreakoutApp can determine whether to take one life away.
   * Assumptions: Valid value for 'alive' exists
   * @return 'true' when alive and 'false' when dead.
   */
  public boolean deadOrAlive() {
    return alive;
  }

  /**
   * Purpose: Returns the image used for low health sprites to be used in collision updates.
   * Assumptions: Valid value for LOW_HEALTH_IMAGE exists.
   * @return image file name.
   */
  public String getLowHealthImage() {
    return this.LOW_HEALTH_IMAGE;
  }

  private List<PowerUp> createPowerUpsAndMissiles(List<PowerUp> powerUps, int xCoord, int yCoord,
      List<Missile> missileMap) {
    double probability = Math.random();
    if (probability < powerUPProbability) {
      declarePowerUpObject(powerUps, xCoord, yCoord, 30, 30, LEVEL_UP_POWER_UP, "powerup");

    } else if (probability < powerUPProbability * 2) {
      declarePowerUpObject(powerUps, xCoord, yCoord, 30, 30, BIGGER_SIZED_BALL, "powerup");
    }
    probability = Math.random();
    if (probability < missileProbability) {
      declarePowerUpObject(powerUps, (int) (xCoord + 500 * probability), yCoord, 12, 40,
          MISSILE_IMAGE, "missile");

    }
    return powerUps;
  }

  private void declarePowerUpObject(List<PowerUp> powerUps, int xCoord, int yCoord, int i, int i2,
      String level_up_power_up, String powerup) {
    PowerUp powerUp = new PowerUp(xCoord, yCoord, i, i2, level_up_power_up, "", powerup);
    powerUp.upload_image_files();
    powerUps.add(powerUp);
    root.getChildren().add(powerUp.getImageView());
  }


  private void checkBlockCollision(List<Block> blocks, List<Missile> missile,
      List<PowerUp> powerUps) {
    for (Block block : blocks) {
      double xBlockBoundaryMax = block.getBoundsInLocal().getWidth();
      double yBlockBoundary = block.getBoundsInLocal().getHeight();
      if ((this.getClassName().equals("ball") || this.getClassName().equals("missile")) && block
          .getImageView().getBoundsInParent()
          .intersects(this.getImageView().getBoundsInParent())) {
        if (block.getImageView().getBoundsInParent()
            .intersects(this.getImageView().getBoundsInParent())) {
          if (this.getClassName().equals("missile")) {
            this.getImageView().setImage(null);
            missile.remove(this);
          }
          if (this.getImageView().getBoundsInParent().getMinY() >= block.getImageView()
              .getBoundsInParent().getMinY()
              && this.getImageView().getBoundsInParent().getMaxY() <= block.getImageView()
              .getBoundsInParent().getMaxY()) {
            this.yDirection *= -1;
          }
        }
        //DEDUCT LIVES WHEN HIT

        createPowerUpsAndMissiles(powerUps, (int) block.getX(), (int) block.getY(), missile);
        block.lives--;
        if (block.lives <= 5) {
          block.getImageView().setImage(
              new Image(Objects.requireNonNull(
                  getClass().getClassLoader().getResourceAsStream(block.getLowHealthImage()))));
        }
        if (block.lives <= 0) {
          block.getImageView().setImage(null);
          blocks.remove(block);
        }
      }

    }
  }

  private void checkBossCollision(List<Boss> boss, List<Missile> missile,
      List<PowerUp> powerUps) {
    for (Boss boss_block : boss) {
      if ((this.getClassName().equals("ball") || this.getClassName().equals("missile"))
          && boss_block
          .getImageView().getBoundsInParent()
          .intersects(this.getImageView().getBoundsInParent())) {
        if (boss_block.getImageView().getBoundsInParent()
            .intersects(this.getImageView().getBoundsInParent())) {
          if (this.getClassName().equals("missile")) {
            this.getImageView().setImage(null);
            missile.remove(this);
            createPowerUpsAndMissiles(powerUps,
                (int) boss_block.getX() + (int) boss_block.getWidth() / 2,
                (int) boss_block.getY() + (int) boss_block.getHeight(), missile);
          }
          if (this.getImageView().getBoundsInParent().getMinY() >= boss_block.getImageView()
              .getBoundsInParent().getMinY()
              && this.getImageView().getBoundsInParent().getMaxY() <= boss_block.getImageView()
              .getBoundsInParent().getMaxY()) {
            this.yDirection *= -1;
          }
        }

        //DEDUCT LIVES WHEN HIT
        boss_block.lives--;
        //  System.out.println(block.lives);
        if (boss_block.lives <= 0) {
          boss_block.getImageView().setImage(null);
          boss.remove(boss_block);
        }
      }
    }
  }


  private void checkPowerUpCollision(List<PowerUp> powerUps, Rectangle myPaddle, Ball ball) {
    if (this.getClassName().equals("powerup") && this.imageView
        .getBoundsInParent()
        .intersects(myPaddle.getBoundsInParent())) {
      this.getImageView().setImage(null);
      if (this.IMAGE.equals(LEVEL_UP_POWER_UP) && (myPaddle.getWidth() < 250
          || myPaddle.getWidth() > 299)) {
        myPaddle.setWidth(myPaddle.getWidth() * 1.2);
      } else if (this.IMAGE.equals(MISSILE_IMAGE)) {
        System.out.println("DAMAGE");
        missilePaddles.get(0).lives--;
      } else {
        ball.getImageView().setFitWidth(ball.getImageView().getFitWidth() + 10);
        ball.getImageView().setFitHeight(ball.getImageView().getFitHeight() + 10);
        if (ball.getImageView().getFitHeight() > 20) {
          ball.getImageView().setImage(new Image(Objects.requireNonNull(
              getClass().getClassLoader().getResourceAsStream("402-Breakout-Tiles.png"))));
        }
      }
      powerUps.remove(this);
    }
  }

  private void checkCollision(List<Block> blocks, List<Boss> boss, List<Missile> missile,
      List<PowerUp> powerUps, Rectangle myPaddle, Ball ball) {
    checkBlockCollision(blocks, missile, powerUps);
    checkBossCollision(boss, missile, powerUps);
    checkPowerUpCollision(powerUps, myPaddle, ball);
  }
}

