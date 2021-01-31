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

  private ImageView imageView;

  public Sprite() {
    super();
  }

  public Sprite(int xCoord, int yCoord, int width, int height, String IMAGE, String type) {
    super(xCoord, yCoord, width, height);
    this.IMAGE = IMAGE;
    className = type;
    setImageView(this.IMAGE);
    this.speed = 600;
    xDirection = 1;
    yDirection = 1;
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

  public void update(double elapsedTime, Rectangle myPaddle, List<Block> blocks) {
    if (getClassName().equals("ball")) {
      this.getImageView()
          .setX(this.getImageView().getX() - this.speed * elapsedTime * this.xDirection);
      this.getImageView()
          .setY(this.getImageView().getY() - this.speed * elapsedTime * this.yDirection);
    } else if (getClassName().equals("block")) {
      //this.getImageView().setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    }
    checkBoundary(myPaddle, blocks);
  }

  public void checkBoundary(Rectangle myPaddle, List<Block> blocks) {
    double xPos = this.getImageView().getX();
    double yPos = this.getImageView().getY();
    checkX(xPos);
    checkY(yPos, myPaddle);
    checkCollision(xPos, yPos, myPaddle, blocks);
  }

  private void checkX(double xPos) {
    if (xPos <= 0 || xPos >= 1000) {
      this.xDirection *= -1;
    }
  }

  private void checkY(double yPos, Rectangle myPaddle) {
    if (yPos <= 0 || yPos >= 1000 || this.imageView.getBoundsInParent()
        .intersects(myPaddle.getBoundsInParent())) {
      this.yDirection *= -1;
    }
  }

  private void checkCollision(double xPos, double yPos, Rectangle myPaddle, List<Block> blocks) {
    double xBoundary = this.imageView.getBoundsInLocal().getWidth() / 2;
    double yBoundary = this.imageView.getBoundsInLocal().getWidth() / 2;
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
      }

//      if(this.getClassName().equals("ball") && this.imageView.getBoundsInParent().intersects(block.getImageView().getBoundsInParent())){
////        System.out.println("XPos: " + block.getX());
////        System.out.println("YPos: " + block.getY());
//        boolean top = false;
//        boolean bottom = false;
//        boolean left = false;
//        boolean right = false;
//
//
//        if(yPos - yBoundary - (block.getY() - yBlockBoundary) > -1 && this.yDirection == 1){
//          bottom = true;
//          System.out.println("hit from bottom");
//        }
//        if(yPos - yBoundary - (block.getY() - yBlockBoundary) < 1 && this.yDirection == -1){
//          top = true;
//          System.out.println("hit from up");
//        }
//        if(xPos - xBoundary - (block.getX() - xBlockBoundary) < 1 && this.xDirection == 1){
//          left = true;
//          System.out.println("Hit from left");
//        }
//        //ball coming from right
//        if(xPos - xBoundary - (block.getX() - xBlockBoundary) > -1 && this.xDirection == -1){
//          right = true;
//          System.out.println("hit from right");
//        }
//        //ball coming from bottom
//        if (top || bottom){
//            this.yDirection *= -1;
////        }else if(left || right){
//          this.xDirection *= -1;

    }

  }
}

