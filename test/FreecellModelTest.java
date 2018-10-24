import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import freecell.model.Card;
import freecell.model.CardType;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

import static org.junit.Assert.*;

public class FreecellModelTest {

  private FreecellOperations<Object> gameOne;

  @Before
  public void setUp() throws Exception {

    gameOne = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();

    List deck = new ArrayList<>();

    for (int i = 13; i > 0; i--) {
      deck.add(new Card(CardType.SPADES, i));
    }

    for (int i = 13; i > 0; i--) {
      deck.add(new Card(CardType.HEARTS, i));
    }

    for (int i = 13; i > 0; i--) {
      deck.add(new Card(CardType.CLUBS, i));
    }

    for (int i = 13; i > 0; i--) {
      deck.add(new Card(CardType.DIAMONDS, i));
    }

    gameOne.startGame(deck, false);

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

    System.out.println(gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    System.out.println(gameOne.getGameState());
    gameOne.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    System.out.println(gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    System.out.println(gameOne.getGameState());

    try {
      gameOne.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameOne.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    gameOne.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    System.out.println(gameOne.getGameState());

    gameOne.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    System.out.println(gameOne.getGameState());

    try {
      gameOne.move(PileType.CASCADE, 6, 5, PileType.OPEN, 0);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }


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