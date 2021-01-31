package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Sprite{
  private ImageView imageView;
  private String className;
  private int index;
  public Ball(int xCoord, int yCoord, int width, int height, String IMAGE){
    super(xCoord, yCoord, width, height, IMAGE, "ball");
    imageView = setImageView(IMAGE);
    index = 0;
  }
//
//  public void update(){
//
//    this.getImageView().setX(this.getImageView().getX() - 30 * 0.05);
//  }
}
