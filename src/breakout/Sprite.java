package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class Sprite extends Rectangle {
  private Image image;
  private String IMAGE;
  private String className;
  private int xDirection;
  private int yDirection;
  private int speed;

  private ImageView imageView;
  public Sprite(){
    super();
  }

  public Sprite(int xCoord, int yCoord, int width, int height, String IMAGE, String type){
      super(xCoord, yCoord, width, height);
      this.IMAGE = IMAGE;
      className = type;
      setImageView(this.IMAGE);
      this.speed = 500;
      xDirection = 1;
      yDirection = 1;
  }

  public ImageView setImageView(String IMAGE){
    image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
    imageView = new ImageView();
    return imageView;
  }

  public ImageView upload_image_files(){
    this.imageView.setImage(this.image);
    this.imageView.setFitWidth(this.getWidth());
    this.imageView.setFitHeight(this.getHeight());
    this.imageView.setX(this.getX());
    this.imageView.setY(this.getY());
    return imageView;
  }

  private void setFill(){

  }

  private String getClassName(){
    return this.className;
  }

  public ImageView getImageView(){
    return imageView;
  }

  public void remove(){
    imageView.setImage(null);
  }

  public void update(double elapsedTime, Rectangle myPaddle, List<Block> blocks){
    if(getClassName().equals("ball")){
      this.getImageView().setX(this.getImageView().getX() - this.speed * elapsedTime * this.xDirection);
      this.getImageView().setY(this.getImageView().getY() - this.speed * elapsedTime * this.yDirection);
    }
    else if(getClassName().equals("block")){
      //this.getImageView().setX(this.getImageView().getX() + this.speed * this.xDirection * elapsedTime);
    }
    checkBoundary(myPaddle, blocks);
  }

  public void checkBoundary(Rectangle myPaddle, List<Block> blocks){
    double xPos = this.getImageView().getX();
    double yPos = this.getImageView().getY();
    checkX(xPos, myPaddle, blocks);
    checkY(yPos, myPaddle, blocks);
  }

  private void checkX(double xPos, Rectangle myPaddle, List<Block> blocks){
    if(xPos <= 0 || xPos >= 1000){
      this.xDirection *= -1;
    }
  }

  private void checkY(double yPos, Rectangle myPaddle, List<Block> blocks){

    if(yPos <= 0 || yPos >= 1000 || this.imageView.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
      this.yDirection *= -1;
    }
  }
}
