package breakout;

import java.awt.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends Rectangle {
  private Image image;
  private String IMAGE;
  private int index;

  private ImageView imageView;
  public Sprite(){
    super();
  }

  public Sprite(int xCoord, int yCoord, int width, int height, String IMAGE){
      super(xCoord, yCoord, width, height);
      this.IMAGE = IMAGE;
      setImageView(this.IMAGE);
      index = 0;
  }

  public ImageView setImageView(String IMAGE){
    image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
    imageView = new ImageView();
    return imageView;
  }

  public ImageView upload_image_files(){
    this.imageView.setImage(this.image);
    this.imageView.setFitWidth(width);
    this.imageView.setFitHeight(height);
    this.imageView.setX(this.getX());
    this.imageView.setY(this.getY());
    return imageView;
  }

  private void setFill(){

  }

  public ImageView getImageView(){
    return imageView;
  }

  public void remove(){
    imageView.setImage(null);
  }

  public void update(double elapsedTime){
    this.getImageView().setX(this.getImageView().getX() - 30 * elapsedTime);
  }
}
