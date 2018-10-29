package freecell.model;

/**
 * The class defines a data structure to store the core element of a freecell game--card.
 * A gaming deck has fifty two cards, four suites, Spades, Hearts, Clubs and Diamonds, and each
 * suite has thirteen different ranks--one to thirteen, and rank 1 is represented as Ace, and rank
 * 11-13 is represented as Jack, Queen, and King.
 */

public class Card {

  private final CardType type;
  private final int rank;

  /**
   * The constructor create a card with given suite and rank.
   * @param type The suite of the card, chose from Spades, Hearts, Clubs and Diamonds.
   * @param rank The rank of the card, chose from one t0 thirteen.
   * @throws IllegalArgumentException When the rank is not in a valid value.
   */

  public Card(CardType type, int rank) throws IllegalArgumentException {

    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException("The rank of a card must be between 1 and 13.");
    }

    this.rank = rank;
    this.type = type;
  }

  /**
   * The method tells us the suite of the card.
   * @return Current card suite.
   */

  public CardType getType() {
    return type;
  }

  /**
   * The method tells us the rank of the card.
   * @return Current card rank.
   */

  public int getRank() {
    return rank;
  }

  /**
   * The method tells us the color of the card.
   * @return Black if the suite is Spades and Clubs, and red if the suite is Diamond and Hearts.
   */

  public Color getColor() {
    if (this.type == CardType.HEARTS || this.type == CardType.DIAMONDS) {
      return Color.RED;
    } else {
      return Color.BLACK;
    }
  }

  /**
   * The method provides a way to check whether two cards are equals.
   * @param o Other objects to be compared with.
   * @return True if they are equal, false otherwise.
   */

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof Card)) {
      return false;
    }

    return this.type == ((Card) o).type && this.rank == ((Card) o).rank;
  }

  /**
   * The method provides a new way to represent hash codes.
   * @return The hash code of current card.
   */

  @Override
  public int hashCode() {

    int result  = rank * 7;
    if (type == CardType.SPADES) {
      result =  31 * result;
    } else if (type == CardType.DIAMONDS) {
      result = 2 * 31 * result;
    } else if (type == CardType.CLUBS) {
      result = 3 * 31 * result;
    } else {
      result = 4 * 31 * result;
    }
    return result;

  }

  /**
   * The method print out the content of the card.
   * @return Card content represented by string.
   */

  @Override
  public String toString() {

    String display = "";

    if (rank == 1) {
      display = display + 'A';
    } else if (rank == 11) {
      display = display + 'J';
    } else if (rank == 12) {
      display = display + 'Q';
    } else if (rank == 13) {
      display = display + 'K';
    } else {
      display = display + String.valueOf(rank);
    }

    if (type == CardType.SPADES) {
      display = display + '♠';
    } else if (type == CardType.HEARTS) {
      display = display + '♥';
    } else if (type == CardType.CLUBS) {
      display = display + '♣';
    } else {
      display = display + '♦';
    }

    return display;
  }
}
