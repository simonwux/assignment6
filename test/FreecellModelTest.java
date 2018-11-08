import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import freecell.model.Card;
import freecell.model.CardType;
import freecell.model.FreecellModel;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FreecellModelTest {

  private FreecellOperations<Object> gameOne;
  private FreecellOperations<Object> gameTwo;
  private List deck;

  /**
   * The method pre-set some Freecell Model for test purpose.
   */

  @Before
  public void setUp() {

    gameOne = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();

    gameTwo = FreecellMultiMoveModel.getBuilder().cascades(8).opens(4).build();

    deck = new ArrayList<>();

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
    gameTwo.startGame(deck, false);

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

    assertEquals("[A♠, A♦, A♥, A♣, 2♠, 2♦, 2♥, 2♣, 3♠, 3♦, 3♥, 3♣, 4♠, 4♦, 4♥, "
                    + "4♣, 5♠, 5♦, 5♥, 5♣, 6♠, 6♦, 6♥, 6♣, 7♠, 7♦, 7♥, 7♣, 8♠, 8♦, 8♥, 8♣, 9♠, 9♦, "
                    + "9♥, 9♣, 10♠, 10♦, 10♥, 10♣, J♠, J♦, J♥, J♣, Q♠, Q♦, Q♥, Q♣, K♠, K♦, K♥, K♣]",
            gameOne.getDeck().toString());
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
    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
            + "C5: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣", a.getGameState());
    assertNotEquals(a.getGameState(), b.getGameState());
    FreecellOperations<Object> c = FreecellModel
            .getBuilder()
            .cascades(4)
            .opens(1)
            .build();
    c.startGame(c.getDeck(), false);
    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", c.getGameState());
  }

  @Test
  public void move() {

    // Illegal argument: invalid cascade source pile number
    try {
      gameOne.move(PileType.CASCADE, -1, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.CASCADE, -1, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // Illegal argument: invalid open source pile number
    try {
      gameOne.move(PileType.OPEN, 5, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.OPEN, 5, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // Illegal argument: invalid foundation source pile number
    try {
      gameOne.move(PileType.FOUNDATION, 5, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.FOUNDATION, 5, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // Illegal argument: invalid open dest pile number
    try {
      gameOne.move(PileType.OPEN, 2, 6, PileType.OPEN, 9);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.OPEN, 2, 6, PileType.OPEN, 9);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // Illegal argument: invalid foundation dest pile number
    try {
      gameOne.move(PileType.OPEN, 3, 6, PileType.FOUNDATION, -4);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.OPEN, 3, 6, PileType.FOUNDATION, -4);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // Illegal argument: invalid cascade dest pile number
    try {
      gameOne.move(PileType.OPEN, 0, 6, PileType.CASCADE, 101);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.OPEN, 0, 6, PileType.CASCADE, 101);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // invalid move: move a card from an empty list.
    try {
      gameOne.move(PileType.OPEN, 0, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.OPEN, 0, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // move an A to empty foundation pile
    gameOne.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    gameTwo.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    assertEquals("F1: A♦\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦, 3♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦, 2♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    assertEquals(gameOne.getGameState(), gameTwo.getGameState());

    // move an A from a open pile to another foundation pile
    gameOne.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    gameTwo.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    assertEquals("F1:\n"
            + "F2: A♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦, 3♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦, 2♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    assertEquals(gameOne.getGameState(), gameTwo.getGameState());
    gameOne.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    gameTwo.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    assertEquals("F1:\n"
            + "F2: A♦, 2♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦, 3♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    assertEquals(gameOne.getGameState(), gameTwo.getGameState());

    // invalid move: move a invalid card to a non-empty foundation pile
    try {
      gameOne.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // invalid move: move a card in the middle.
    try {
      gameOne.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }
    try {
      gameTwo.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    //invalid move: move a non_A card to a empty foundation pile.
    try {
      gameOne.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 3);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 3);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    // move a card to an open pile
    gameOne.move(PileType.CASCADE, 1, 6, PileType.OPEN, 1);
    gameTwo.move(PileType.CASCADE, 1, 6, PileType.OPEN, 1);
    assertEquals("F1:\n"
            + "F2: A♦, 2♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2: 3♦\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    assertEquals(gameOne.getGameState(), gameTwo.getGameState());

    // invalid move: move a cart to a non-empty open pile
    try {
      gameOne.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.CASCADE, 7, 5, PileType.OPEN, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    gameOne.move(PileType.CASCADE, 3, 5, PileType.OPEN, 0);
    gameTwo.move(PileType.CASCADE, 3, 5, PileType.OPEN, 0);

    // move a valid card to cascade pile
    gameOne.move(PileType.OPEN, 1, 0, PileType.CASCADE, 3);
    gameTwo.move(PileType.OPEN, 1, 0, PileType.CASCADE, 3);
    assertEquals("F1:\n"
            + "F2: A♦, 2♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 9♦\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 3♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    assertEquals(gameOne.getGameState(), gameTwo.getGameState());

    // invalid move: move a card to a non-empty cascade pile
    try {
      gameOne.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameTwo.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

  }

  @Test
  public void isGameOver() {
    assertFalse(gameOne.isGameOver());
    assertFalse(gameTwo.isGameOver());
    int sourcePile = 3;
    int index = 6;
    for (int targetPile = 0; targetPile < 4; targetPile++) {
      for (int i = 0; i < 13; i++) {
        gameOne.move(PileType.CASCADE, sourcePile, index, PileType.FOUNDATION, targetPile);
        gameTwo.move(PileType.CASCADE, sourcePile, index, PileType.FOUNDATION, targetPile);
        sourcePile -= 1;
        if (sourcePile < 0) {
          sourcePile = 7;
          index -= 1;
        }
      }
    }
    assertTrue(gameOne.isGameOver());
    assertTrue(gameTwo.isGameOver());
  }

  @Test
  public void getGameState() {
    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦, 3♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦, 2♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦, A♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦", gameOne.getGameState());
    gameOne.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    gameOne.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    gameOne.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    assertEquals("F1:\n"
            + "F2: A♦, 2♦, 3♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 5♦\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦", gameOne.getGameState());
    try {
      gameOne.move(PileType.CASCADE, 6, 5, PileType.OPEN, 0);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }
    assertEquals("F1:\n"
            + "F2: A♦, 2♦, 3♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 5♦\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♠, 5♠, 10♥, 2♥, 7♣, Q♦, 4♦\n"
            + "C2: Q♠, 4♠, 9♥, A♥, 6♣, J♦\n"
            + "C3: J♠, 3♠, 8♥, K♣, 5♣, 10♦\n"
            + "C4: 10♠, 2♠, 7♥, Q♣, 4♣, 9♦\n"
            + "C5: 9♠, A♠, 6♥, J♣, 3♣, 8♦\n"
            + "C6: 8♠, K♥, 5♥, 10♣, 2♣, 7♦\n"
            + "C7: 7♠, Q♥, 4♥, 9♣, A♣, 6♦\n"
            + "C8: 6♠, J♥, 3♥, 8♣, K♦", gameOne.getGameState());
  }

  @Test
  public void testMultiMove() {
    FreecellOperations<Object> gameThree = FreecellMultiMoveModel
            .getBuilder()
            .cascades(52)
            .opens(1)
            .build();

    gameThree.startGame(deck, false);

    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♠\n"
            + "C2: Q♠\n"
            + "C3: J♠\n"
            + "C4: 10♠\n"
            + "C5: 9♠\n"
            + "C6: 8♠\n"
            + "C7: 7♠\n"
            + "C8: 6♠\n"
            + "C9: 5♠\n"
            + "C10: 4♠\n"
            + "C11: 3♠\n"
            + "C12: 2♠\n"
            + "C13: A♠\n"
            + "C14: K♥\n"
            + "C15: Q♥\n"
            + "C16: J♥\n"
            + "C17: 10♥\n"
            + "C18: 9♥\n"
            + "C19: 8♥\n"
            + "C20: 7♥\n"
            + "C21: 6♥\n"
            + "C22: 5♥\n"
            + "C23: 4♥\n"
            + "C24: 3♥\n"
            + "C25: 2♥\n"
            + "C26: A♥\n"
            + "C27: K♣\n"
            + "C28: Q♣\n"
            + "C29: J♣\n"
            + "C30: 10♣\n"
            + "C31: 9♣\n"
            + "C32: 8♣\n"
            + "C33: 7♣\n"
            + "C34: 6♣\n"
            + "C35: 5♣\n"
            + "C36: 4♣\n"
            + "C37: 3♣\n"
            + "C38: 2♣\n"
            + "C39: A♣\n"
            + "C40: K♦\n"
            + "C41: Q♦\n"
            + "C42: J♦\n"
            + "C43: 10♦\n"
            + "C44: 9♦\n"
            + "C45: 8♦\n"
            + "C46: 7♦\n"
            + "C47: 6♦\n"
            + "C48: 5♦\n"
            + "C49: 4♦\n"
            + "C50: 3♦\n"
            + "C51: 2♦\n"
            + "C52: A♦", gameThree.getGameState());

    gameThree.move(PileType.CASCADE, 51, 0, PileType.CASCADE, 37);
    gameThree.move(PileType.CASCADE, 37, 0, PileType.CASCADE, 49);
    gameThree.move(PileType.CASCADE, 49, 0, PileType.CASCADE, 35);
    gameThree.move(PileType.CASCADE, 35, 0, PileType.CASCADE, 47);
    gameThree.move(PileType.CASCADE, 47, 0, PileType.CASCADE, 33);
    gameThree.move(PileType.CASCADE, 33, 0, PileType.CASCADE, 45);
    gameThree.move(PileType.CASCADE, 45, 0, PileType.CASCADE, 31);
    gameThree.move(PileType.CASCADE, 31, 0, PileType.CASCADE, 43);

    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♠\n"
            + "C2: Q♠\n"
            + "C3: J♠\n"
            + "C4: 10♠\n"
            + "C5: 9♠\n"
            + "C6: 8♠\n"
            + "C7: 7♠\n"
            + "C8: 6♠\n"
            + "C9: 5♠\n"
            + "C10: 4♠\n"
            + "C11: 3♠\n"
            + "C12: 2♠\n"
            + "C13: A♠\n"
            + "C14: K♥\n"
            + "C15: Q♥\n"
            + "C16: J♥\n"
            + "C17: 10♥\n"
            + "C18: 9♥\n"
            + "C19: 8♥\n"
            + "C20: 7♥\n"
            + "C21: 6♥\n"
            + "C22: 5♥\n"
            + "C23: 4♥\n"
            + "C24: 3♥\n"
            + "C25: 2♥\n"
            + "C26: A♥\n"
            + "C27: K♣\n"
            + "C28: Q♣\n"
            + "C29: J♣\n"
            + "C30: 10♣\n"
            + "C31: 9♣\n"
            + "C32:\n"
            + "C33: 7♣\n"
            + "C34:\n"
            + "C35: 5♣\n"
            + "C36:\n"
            + "C37: 3♣\n"
            + "C38:\n"
            + "C39: A♣\n"
            + "C40: K♦\n"
            + "C41: Q♦\n"
            + "C42: J♦\n"
            + "C43: 10♦\n"
            + "C44: 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n"
            + "C45: 8♦\n"
            + "C46:\n"
            + "C47: 6♦\n"
            + "C48:\n"
            + "C49: 4♦\n"
            + "C50:\n"
            + "C51: 2♦\n"
            + "C52:", gameThree.getGameState());

    gameThree.move(PileType.CASCADE, 43, 1, PileType.CASCADE, 17);
    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♠\n"
            + "C2: Q♠\n"
            + "C3: J♠\n"
            + "C4: 10♠\n"
            + "C5: 9♠\n"
            + "C6: 8♠\n"
            + "C7: 7♠\n"
            + "C8: 6♠\n"
            + "C9: 5♠\n"
            + "C10: 4♠\n"
            + "C11: 3♠\n"
            + "C12: 2♠\n"
            + "C13: A♠\n"
            + "C14: K♥\n"
            + "C15: Q♥\n"
            + "C16: J♥\n"
            + "C17: 10♥\n"
            + "C18: 9♥, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n"
            + "C19: 8♥\n"
            + "C20: 7♥\n"
            + "C21: 6♥\n"
            + "C22: 5♥\n"
            + "C23: 4♥\n"
            + "C24: 3♥\n"
            + "C25: 2♥\n"
            + "C26: A♥\n"
            + "C27: K♣\n"
            + "C28: Q♣\n"
            + "C29: J♣\n"
            + "C30: 10♣\n"
            + "C31: 9♣\n"
            + "C32:\n"
            + "C33: 7♣\n"
            + "C34:\n"
            + "C35: 5♣\n"
            + "C36:\n"
            + "C37: 3♣\n"
            + "C38:\n"
            + "C39: A♣\n"
            + "C40: K♦\n"
            + "C41: Q♦\n"
            + "C42: J♦\n"
            + "C43: 10♦\n"
            + "C44: 9♦\n"
            + "C45: 8♦\n"
            + "C46:\n"
            + "C47: 6♦\n"
            + "C48:\n"
            + "C49: 4♦\n"
            + "C50:\n"
            + "C51: 2♦\n"
            + "C52:", gameThree.getGameState());

    try {
      gameThree.move(PileType.CASCADE, 17, 0, PileType.OPEN, 0);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }

    try {
      gameThree.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 1);
      fail("The move should be invalid");
    } catch (IllegalArgumentException e) {
      // do nothing.
    }
  }
}