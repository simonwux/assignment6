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
    try {
      FreecellOperations a = gameOne = FreecellModel
              .getBuilder()
              .cascades(1)
              .opens(1)
              .build();
    } catch (IllegalArgumentException e) {
      assertEquals("Cascades should be at least 4.", e.getMessage());
    }

    try {
      FreecellOperations a = gameOne = FreecellModel
              .getBuilder()
              .cascades(4)
              .opens(1)
              .build();
    } catch (IllegalArgumentException e) {
      assertEquals("Opens should be at least 1.", e.getMessage());
    }
  }

  @Test
  public void startGame() {
    FreecellOperations<Object> a = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    a.startGame(a.getDeck(), false);
    FreecellOperations<Object> b = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    b.startGame(b.getDeck(), true);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n" +
            "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n" +
            "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n" +
            "C4: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n" +
            "C5: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n" +
            "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n" +
            "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n" +
            "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n", a.getGameState());
    assertNotEquals(a.getGameState(), b.getGameState());
  }

  @Test
  public void move() {

    //System.out.println(gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    //System.out.println(gameOne.getGameState());
    gameOne.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    //System.out.println(gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    //System.out.println(gameOne.getGameState());

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
    //System.out.println(gameOne.getGameState());

    gameOne.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    //System.out.println(gameOne.getGameState());

    try {
      gameOne.move(PileType.CASCADE, 6, 5, PileType.OPEN, 0);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }


  }

  @Test
  public void isGameOver() {
    assertFalse(gameOne.isGameOver());
    int sourcePile = 3;
    int index = 6;
    for (int targetPile = 0; targetPile < 4; targetPile++) {
      for (int i = 0; i < 13; i++) {
        gameOne.move(PileType.CASCADE, sourcePile, index, PileType.FOUNDATION, targetPile);
        sourcePile -= 1;
        if (sourcePile < 0) {
          sourcePile = 7;
          index -= 1;
        }
      }
    }
    //System.out.println(gameOne.getGameState());
    assertTrue(gameOne.isGameOver());
  }

  @Test
  public void getGameState() {
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n" +
            "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦, 3♦\n" +
            "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦, 2♦\n" +
            "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦, A♦\n" +
            "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n" +
            "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n" +
            "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n" +
            "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦\n", gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    gameOne.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    assertEquals("F1:\n" +
            "F2: A♦, 2♦, 3♦\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 5♦\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n" +
            "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n" +
            "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n" +
            "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n" +
            "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n" +
            "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n" +
            "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n" +
            "C8: 6♠, J♥, 3♥, 8♣, K♦\n", gameOne.getGameState());
    try {
      gameOne.move(PileType.CASCADE, 6, 5, PileType.OPEN, 0);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }
    assertEquals("F1:\n" +
            "F2: A♦, 2♦, 3♦\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 5♦\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n" +
            "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n" +
            "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n" +
            "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n" +
            "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n" +
            "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n" +
            "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n" +
            "C8: 6♠, J♥, 3♥, 8♣, K♦\n", gameOne.getGameState());
  }

  @Test
  public void getBuilder() {
  }
}