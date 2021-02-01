package breakout;

import java.util.concurrent.TimeUnit;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;
import org.w3c.dom.css.Rect;

public class Sprite extends Rectangle {

  private Image image;
  private String IMAGE;
  private String className;
  private int xDirection;
  private int yDirection;
  private int speed;
  private int amount_missile;
  private ImageView imageView;
  private int number_of_lives;
  private int xCoord;
  private int yCoord;
  private boolean alive = true;

  public Sprite() {
    super();
  }

  public Sprite(int xCoord, int yCoord, int width, int height, String IMAGE, String type) {
    super(xCoord, yCoord, width, height);
    this.IMAGE = IMAGE;
    className = type;
    setImageView(this.IMAGE);
    this.speed = 300;
    xDirection = 1;
    yDirection = 1;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.upload_image_files();
    this.number_of_lives = 3;
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

  public void remove() {
    imageView.setImage(null);
  }


  public void update(double elapsedTime, Rectangle myPaddle, List<Block> blocks, List<Boss> boss,
      List<Missile> missile) {
    if (getClassName().equals("missile")) {
      this.getImageView()
          .setY(this.getImageView().getY() - this.speed * 2 * elapsedTime);
    }
    if (getClassName().equals("powerup")) {
      this.getImageView()
          .setY(this.getImageView().getY() + this.speed * 2 * elapsedTime);
    }
    if (getClassName().equals("ball")) {
      this.getImageView()
          .setX(this.getImageView().getX() - this.speed * elapsedTime * this.xDirection);
      this.getImageView()
          .setY(this.getImageView().getY() - this.speed * elapsedTime * this.yDirection);
    } else if (getClassName().equals("block")) {
      if (Math.random() < 0.005) {
        xDirection *= -1;
      }
      this.getImageView()
          .setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    } else if (getClassName().equals("boss")) {
      if (Math.random() < 0.005) {
        xDirection *= -1;
      }
      this.getImageView()
          .setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    }
    checkBoundary(myPaddle, blocks, boss, missile);
  }

  public void checkBoundary(Rectangle myPaddle, List<Block> blocks, List<Boss> boss,
      List<Missile> missile) {
    double xPos = this.getImageView().getX();
    double yPos = this.getImageView().getY();
    checkX(xPos);
    checkY(yPos, myPaddle);
    checkCollision(blocks, boss, missile);
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
        this.xDirection = 1;
      } else {
        this.xDirection = -1;
      }
    }
    if (this.getClassName().equals("ball")
        && this.getImageView().getBoundsInParent().getMaxY() >= 1000) {
        alive = false;
    }


  }

  public boolean deadOrAlive(){
    return alive;
  }



  private void checkBlockCollision(List<Block> blocks, List<Missile> missile) {
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
        block.lives--;
        if (block.lives <= 5) {
          block.getImageView().setImage(
              new Image(Objects.requireNonNull(
                  getClass().getClassLoader().getResourceAsStream("110-Breakout-Tiles.png"))));
        }
        if (block.lives <= 0) {
          block.getImageView().setImage(null);
          blocks.remove(block);
        }
      }

    }
  }

  private void checkBossCollision(List<Boss> boss, List<Missile> missile) {
    for (Boss boss_block : boss) {
      if ((this.getClassName().equals("ball") || this.getClassName().equals("missile")) && boss_block
          .getImageView().getBoundsInParent()
          .intersects(this.getImageView().getBoundsInParent())) {
        if (boss_block.getImageView().getBoundsInParent()
            .intersects(this.getImageView().getBoundsInParent())) {
          if (this.getClassName().equals("missile")) {
            this.getImageView().setImage(null);
            missile.remove(this);
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

  private void checkCollision(List<Block> blocks, List<Boss> boss, List<Missile> missile) {
    checkBlockCollision(blocks, missile);
    checkBossCollision(boss, missile);
  }
}

