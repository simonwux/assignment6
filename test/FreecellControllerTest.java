import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import freecell.controller.FreecellController;
import freecell.model.Card;
import freecell.model.CardType;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.MockModel;

import static org.junit.Assert.assertEquals;

public class FreecellControllerTest {

  @Test
  public void playGame() {
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

    FreecellOperations gameOne = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    FreecellController controller;
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1 8as 8as 8as 8as 8as 8 OAS A1 W1 O1 q");

    // Input should not be null.
    try {
      controller = new FreecellController(null, out);
    } catch (IllegalArgumentException e) {
      assertEquals("Input and output should not be null.", e.getMessage());
    }

    // Output should not be null.
    try {
      controller = new FreecellController(in, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Input and output should not be null.", e.getMessage());
    }

    // Input and output should not be null.
    try {
      controller = new FreecellController(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Input and output should not be null.", e.getMessage());
    }

    //Test 'q' and bad inputs and invalid move.
    in = new StringReader("A1 C1asfd C1 8as 8as 8as 8as 8.5 8 OAS A1 W1a O1 q");
    controller = new FreecellController(in, out);
    controller.playGame(gameOne.getDeck(), gameOne, false);
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
            + "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "Invalid source pile name, input again.\n"
            + "Invalid source pile index, input again.\n"
            + "Invalid card index, input again.\n"
            + "Invalid card index, input again.\n"
            + "Invalid card index, input again.\n"
            + "Invalid card index, input again.\n"
            + "Invalid card index, input again.\n"
            + "Invalid target pile index, input again.\n"
            + "Invalid target pile name, input again.\n"
            + "Invalid target pile name, input again.\n"
            + "Invalid move. Try again. Card to be move is not top card of a cascade pile.\n"
            + "F1:\n"
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
            + "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "Game quit prematurely.\n", out.toString());

    // Test 'Q'.
    in = new StringReader("Q");
    StringBuffer out2 = new StringBuffer();
    controller = new FreecellController(in, out2);
    controller.playGame(gameOne.getDeck(), gameOne, false);
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
            + "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "Game quit prematurely.\n", out2.toString());

    in = new StringReader("Q");
    StringBuffer out3 = new StringBuffer();
    controller = new FreecellController(in, out3);
    controller.playGame(deck, gameOne, false);
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
            + "C8: 6♠, J♥, 3♥, 8♣, K♦, 5♦\n"
            + "Game quit prematurely.\n", out3.toString());

    // When deck is null.
    in = new StringReader("Q");
    StringBuffer out4 = new StringBuffer();
    controller = new FreecellController(in, out4);
    try {
      controller.playGame(null, gameOne, false);
    } catch (IllegalArgumentException e) {
      assertEquals("Deck should not be null.", e.getMessage());
    }

    // When model is null.
    in = new StringReader("Q");
    StringBuffer out5 = new StringBuffer();
    controller = new FreecellController(in, out5);
    try {
      controller.playGame(gameOne.getDeck(), null, false);
    } catch (IllegalArgumentException e) {
      assertEquals("Model should not be null.", e.getMessage());
    }

    // Reader fails.
    in = new StringReader(". .. . ");
    StringBuffer out6 = new StringBuffer();
    controller = new FreecellController(in, out6);
    try {
      controller.playGame(gameOne.getDeck(), gameOne, false);
    } catch (IllegalStateException e) {
      assertEquals("Reader fails.", e.getMessage());
    }

    // Test game over.
    String st = "";
    int sourcePile = 4;
    int index = 7;
    for (int targetPile = 0; targetPile < 4; targetPile++) {
      for (int i = 0; i < 13; i++) {
        st += "C" + Integer.toString(sourcePile) + " " + Integer.toString(index) + " " + "F"
                + Integer.toString(targetPile + 1) + " ";
        sourcePile -= 1;
        if (sourcePile < 1) {
          sourcePile = 8;
          index -= 1;
        }
      }
    }
    in = new StringReader(st);
    StringBuffer out7 = new StringBuffer();
    controller = new FreecellController(in, out7);
    controller.playGame(deck, gameOne, false);
    String outSt = out7.toString();
    String ans = "";
    int count = 0;
    for (int i = outSt.length() - 1; i > 0; i--) {
      if (outSt.charAt(i) == '\n') {
        count += 1;
        if (count == 18) {
          break;
        }
      }
      ans = outSt.charAt(i) + ans;
    }
    assertEquals("F1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "F2: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1:\n"
            + "C2:\n"
            + "C3:\n"
            + "C4:\n"
            + "C5:\n"
            + "C6:\n"
            + "C7:\n"
            + "C8:\n"
            + "Game over.\n", ans);

    // Mock testing.
    StringBuilder log = new StringBuilder();
    MockModel gameThree = new MockModel(log);
    in = new StringReader("C1 7 O1 C3 7 O2 C8 6 O3 q");
    StringBuffer out8 = new StringBuffer();
    controller = new FreecellController(in, out8);
    controller.playGame(deck, gameThree, false);
    assertEquals("CASCADE 0 6 OPEN 0\n"
            + "CASCADE 2 6 OPEN 1\n"
            + "CASCADE 7 5 OPEN 2\n", log.toString());
  }
}