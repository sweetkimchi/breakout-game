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
  private boolean move;

  private ImageView imageView;

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
    this.move = move;
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
    this.imageView.setX(this.getX());
    this.imageView.setY(this.getY());
    return imageView;
  }

  private void setFill() {

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


  public void update(double elapsedTime, Rectangle myPaddle, List<Block> blocks, List<Boss> boss) {
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
    checkBoundary(myPaddle, blocks, boss);
  }

  public void checkBoundary(Rectangle myPaddle, List<Block> blocks, List<Boss> boss) {
    double xPos = this.getImageView().getX();
    double yPos = this.getImageView().getY();
    checkX(xPos);
    checkY(yPos, myPaddle);
    checkCollision(blocks, boss);
  }

  private void checkX(double xPos) {
    if(this.getClassName().equals("block") || this.getClassName().equals("boss")) {
      if (this.getImageView().getBoundsInParent().getMinX() <= 10) {
        this.xDirection = 1;
      } else if (this.getImageView().getBoundsInParent().getMaxX() >= 980) {
        this.xDirection = -1;
      }
      //THIS IS VERY IMPORTANT - NEEDED IN ORDER TO FIX THE BUG WHERE THE BLOCKS GET TRAPPED AT THE EDGES
    }else {
      if(this.getImageView().getBoundsInParent().getMinX() <= 10 || this.getImageView().getBoundsInParent().getMaxX() >= 980){
        this.xDirection *= -1;
      }
    }
  }

  private void checkY(double yPos, Rectangle myPaddle) {
    if (this.getImageView().getBoundsInParent().getMinY() <= 0) {
      this.yDirection *= -1;
    }
    if( this.getClassName().equals("ball") && this.getImageView().getBoundsInParent().getMaxY() >= 1000 || this.imageView
        .getBoundsInParent()
        .intersects(myPaddle.getBoundsInParent())){
        this.yDirection *= -1;
        System.out.println(this.getImageView().getBoundsInParent().getMinX());
        System.out.println(myPaddle.getX() + myPaddle.getWidth()/2);
        if(this.getImageView().getBoundsInParent().getCenterX() < myPaddle.getX() + myPaddle.getWidth()/2){
          this.xDirection = 1;
        }
        else{
          this.xDirection = -1;
        }
    }


  }

  private void checkBlockCollision(List<Block> blocks) {
    for (Block block : blocks) {
      double xBlockBoundaryMax = block.getBoundsInLocal().getWidth();
      double yBlockBoundary = block.getBoundsInLocal().getHeight();
      if (this.getClassName().equals("ball") && block.getImageView().getBoundsInParent()
          .intersects(this.getImageView().getBoundsInParent())) {
//        if (this.getImageView().getBoundsInParent().getMinX() >= block.getImageView()
//            .getBoundsInParent().getMinX()
//            && this.getImageView().getBoundsInParent().getMaxX() <= block.getImageView()
//            .getBoundsInParent().getMaxX()) {
//          this.xDirection *= -1;
//        }
        if (this.getClassName().equals("ball") && block.getImageView().getBoundsInParent()
            .intersects(this.getImageView().getBoundsInParent())) {
          if (this.getImageView().getBoundsInParent().getMinY() >= block.getImageView()
              .getBoundsInParent().getMinY()
              && this.getImageView().getBoundsInParent().getMaxY() <= block.getImageView()
              .getBoundsInParent().getMaxY()) {
            this.yDirection *= -1;
          }
        }

        //DEDUCT LIVES WHEN HIT
        block.lives--;
      //  System.out.println(block.lives);
        if(block.lives <= 5){
          block.getImageView().setImage(new Image(getClass().getClassLoader().getResourceAsStream("110-Breakout-Tiles.png")));
        }
        if (block.lives <= 0) {
          block.getImageView().setImage(null);
          blocks.remove(block);
        }
      }

    }
  }

  private void checkBossCollision(List<Boss> boss) {
    for (Boss boss_block : boss) {
      double xBlockBoundaryMax = boss_block.getBoundsInLocal().getWidth();
      double yBlockBoundary = boss_block.getBoundsInLocal().getHeight();
      if (this.getClassName().equals("ball") && boss_block.getImageView().getBoundsInParent()
          .intersects(this.getImageView().getBoundsInParent())) {
        if (this.getClassName().equals("ball") && boss_block.getImageView().getBoundsInParent()
            .intersects(this.getImageView().getBoundsInParent())) {
          if (this.getImageView().getBoundsInParent().getMinY() >= boss_block.getImageView()
              .getBoundsInParent().getMinY()
              && this.getImageView().getBoundsInParent().getMaxY() <= boss_block.getImageView()
              .getBoundsInParent().getMaxY()) {
            this.yDirection *= -1;
          }
        }
      }
    }
  }

  private void checkCollision(List<Block> blocks, List<Boss> boss) {
    checkBlockCollision(blocks);
    checkBossCollision(boss);
  }
}

