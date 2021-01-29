package breakout;

import java.util.Objects;
import javafx.scene.image.Image;

public class Sprite {
  private static Image image;
  public Sprite(int width, int height, String IMAGE){
    image = new Image(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(IMAGE)));
  }

  public void update(){

  }
}
