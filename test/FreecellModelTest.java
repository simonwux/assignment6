import org.junit.Before;
import org.junit.Test;

import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

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
    FreecellOperations<Object> a = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    a.startGame(a.getDeck(), false);
    System.out.println(a.getGameState());
    a.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    a.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 1);
    a.move(PileType.CASCADE, 2, 6, PileType.OPEN, 0);
    a.move(PileType.CASCADE, 2, 5, PileType.CASCADE, 1);
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