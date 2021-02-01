package breakout;

import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

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
  private final String LEVEL_UP_POWER_UP = "344-Breakout-Tiles.png";
  private final String BIGGER_SIZED_BALL = "403-Breakout-Tiles.png";
  private final String MISSILE_IMAGE = "346-Breakout-Tiles.png";
  private Pane root;
  private int paddleLevel;
  private final double powerUPProbability = 0.05;
  private final double changeDirectionProbability = 0.005;
  private final double missileProbability = 0.1;
  private List<MissilePaddle> missilePaddles;

  public Sprite() {
    super();
  }

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

  public ImageView setImageView(String IMAGE) {
    image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
    imageView = new ImageView();
    return imageView;
  }

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

  public ImageView getImageView() {
    return imageView;
  }

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

  public void checkBoundary(Rectangle myPaddle, List<Block> blocks, List<Boss> boss,
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

  public boolean deadOrAlive() {
    return alive;
  }

  public String getLowHealthImage() {
    return this.LOW_HEALTH_IMAGE;
  }

  public List<PowerUp> createPowerUpsAndMissiles(List<PowerUp> powerUps, int xCoord, int yCoord,
      List<Missile> missileMap) {
    double probability = Math.random();
    if (probability < powerUPProbability) {
      PowerUp powerUp = new PowerUp(xCoord, yCoord, 30, 30, LEVEL_UP_POWER_UP, "", "powerup");
      powerUp.upload_image_files();
      powerUps.add(powerUp);
      root.getChildren().add(powerUp.getImageView());

    } else if (probability < powerUPProbability * 2) {
      PowerUp powerUp = new PowerUp(xCoord, yCoord, 30, 30, BIGGER_SIZED_BALL, "", "powerup");
      powerUp.upload_image_files();
      powerUps.add(powerUp);
      root.getChildren().add(powerUp.getImageView());
    }
    probability = Math.random();
    if (probability < missileProbability) {
      PowerUp powerUp = new PowerUp((int) (xCoord + 500 * probability), yCoord, 12, 40,
          MISSILE_IMAGE, "", "missile");
      powerUp.upload_image_files();
      powerUps.add(powerUp);
      root.getChildren().add(powerUp.getImageView());

    }
    return powerUps;
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

  private void checkCollision(List<Block> blocks, List<Boss> boss, List<Missile> missile,
      List<PowerUp> powerUps, Rectangle myPaddle, Ball ball) {
    checkBlockCollision(blocks, missile, powerUps);
    checkBossCollision(boss, missile, powerUps);
    checkPowerUpCollision(powerUps, myPaddle, ball);
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
}

