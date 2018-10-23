import org.junit.Before;
import org.junit.Test;

import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;

import static org.junit.Assert.*;

public class FreecellModelTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void getDeck() {
  }

  @Test
  public void startGame() {
    FreecellOperations<Object> a =FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    a.startGame(a.getDeck(), false);
    a.startGame(a.getDeck(), false);
    System.out.println(a.getGameState());
    System.out.println(a.isGameOver());
  }

  @Test
  public void move() {
  }

  @Test
  public void isGameOver() {
  }

  @Test
  public void getGameState() {
  }

  @Test
  public void getBuilder() {
  }
}