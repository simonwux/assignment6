import org.junit.Test;

import freecell.model.Card;
import freecell.model.CardType;

import static org.junit.Assert.*;

public class FreecellOperationsTest {

  @Test
  public void testCard() {

    Card cardOne = new Card(CardType.SPADES, 1);
    Card cardTwo = new Card(CardType.SPADES, 13);
    Card cardThree = new Card(CardType.HEARTS, 8);
    Card cardFour = new Card(CardType.HEARTS, 8);
    Card cardFive = new Card(CardType.CLUBS, 11);
    Card cardSix = new Card(CardType.DIAMONDS, 10);

    assertEquals("A♠", cardOne.toString());
    assertEquals("K♠", cardTwo.toString());
    assertEquals("8♥", cardThree.toString());
    assertEquals("8♥", cardFour.toString());
    assertEquals("J♣", cardFive.toString());
    assertEquals("10♦", cardSix.toString());

    assertEquals(cardThree, cardFour);
    assertNotEquals(cardOne, cardFive);
    assertNotEquals(cardOne, cardTwo);

  }

}