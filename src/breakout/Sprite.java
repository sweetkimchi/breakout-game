package breakout;

import java.awt.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends Rectangle {
  private static Image image;
  private static String IMAGE;
  private int index;

  private static ImageView imageView;
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
    imageView.setImage(image);
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);
    imageView.setX(this.getX());
    imageView.setY(this.getY());
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

  public void update(ImageView spriteImageView){
    System.out.println(this);
    imageView.setX(imageView.getX() + 100);
  }
}
