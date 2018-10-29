import org.junit.Before;
import org.junit.Test;

import freecell.model.Card;
import freecell.model.CardType;
import freecell.model.Color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class CardTest {

  private Card cardOne;
  private Card cardTwo;
  private Card cardThree;
  private Card cardFour;
  private Card cardFive;
  private Card cardSix;

  /**
   * The method set up several cards for following testing.
   */

  @Before
  public void setUp() {
    cardOne = new Card(CardType.SPADES, 1);
    cardTwo = new Card(CardType.SPADES, 13);
    cardThree = new Card(CardType.HEARTS, 8);
    cardFour = new Card(CardType.HEARTS, 8);
    cardFive = new Card(CardType.CLUBS, 11);
    cardSix = new Card(CardType.DIAMONDS, 10);

    try {
      Card card = new Card(CardType.SPADES, 0);
      fail("Init should fail");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

    try {
      Card card = new Card(CardType.HEARTS, -1);
      fail("Init should fail");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

    try {
      Card card = new Card(CardType.SPADES, 14);
      fail("Init should fail");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

  }

  @Test
  public void getType() {
    assertEquals(CardType.SPADES, cardOne.getType());
    assertEquals(CardType.HEARTS, cardThree.getType());
    assertEquals(CardType.CLUBS, cardFive.getType());
    assertEquals(CardType.DIAMONDS, cardSix.getType());
  }

  @Test
  public void getRank() {
    assertEquals(1, cardOne.getRank());
    assertEquals(13, cardTwo.getRank());
    assertEquals(8, cardThree.getRank());
    assertEquals(11, cardFive.getRank());
    assertEquals(10, cardSix.getRank());
  }

  @Test
  public void getColor() {
    assertEquals(Color.BLACK, cardOne.getColor());
    assertEquals(Color.BLACK, cardTwo.getColor());
    assertEquals(Color.BLACK, cardFive.getColor());
    assertEquals(Color.RED, cardFour.getColor());
    assertEquals(Color.RED, cardThree.getColor());
    assertEquals(Color.RED, cardSix.getColor());
  }

  @Test
  public void test_equals() {
    assertEquals(cardThree, cardFour);
    assertNotEquals(cardOne, cardFive);
    assertNotEquals(cardOne, cardTwo);
  }

  @Test
  public void toStringTest() {

    assertEquals("A♠", cardOne.toString());
    assertEquals("K♠", cardTwo.toString());
    assertEquals("8♥", cardThree.toString());
    assertEquals("8♥", cardFour.toString());
    assertEquals("J♣", cardFive.toString());
    assertEquals("10♦", cardSix.toString());
  }
}