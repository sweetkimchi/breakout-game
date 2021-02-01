package breakout;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paddle extends Sprite{
  public int lives;
  private Rectangle myPaddle;
  public int experience;
  public Paddle(int xCoord, int yCoord, int width, int height, String IMAGE, int lives, Rectangle myPaddle){
    super(xCoord, yCoord, width, height, IMAGE, "paddle");
    lives = 3;
    this.myPaddle = myPaddle;
    Image paddle_image = new Image(
        Objects.requireNonNull(
            this.getClass().getClassLoader().getResourceAsStream(IMAGE)));

    myPaddle.setFill(new ImagePattern(paddle_image));
    experience = 0;
  }

  public Rectangle getRectangle(){
    return this.myPaddle;
  }

  public int getExperience(){
    return experience;
  }
}
