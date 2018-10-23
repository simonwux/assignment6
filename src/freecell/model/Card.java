package freecell.model;

public class Card {

  private CardType type;
  private int rank;

  public Card(CardType type, int rank) throws IllegalArgumentException {

    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException("The rank of a card must be between 1 and 13.");
    }

    this.rank = rank;
    this.type = type;
  }

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

  @Override
  public String toString() {

    String display = "";

    if (rank == 1) {
      display  = display + 'A';
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
