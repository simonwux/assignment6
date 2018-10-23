package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreecellModel implements FreecellOperations {

  private final int CARDNUM = 52;
  private final int CARDTYPENUM = 13;

  // Still confusing about what this function do, so I suppose it returns a new deck.

  public List getDeck() {

    List Deck = new ArrayList();

    for (int i = 0; i < CARDTYPENUM; i++) {
      Deck.add(new Card(CardType.SPADES, i + 1));
      Deck.add(new Card(CardType.DIAMONDS, i + 1));
      Deck.add(new Card(CardType.HEARTS, i + 1));
      Deck.add(new Card(CardType.CLUBS, i + 1));
    }

    shuffle(Deck);

    return Deck;
  }

  public void startGame(List deck, boolean shuffle) throws IllegalArgumentException {

  }

  public void move(PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber) throws IllegalArgumentException, IllegalStateException {

  }

  public boolean isGameOver() {
    return false;
  }

  public String getGameState() {
    return null;
  }

  private void shuffle(List deck) {
    Collections.shuffle(deck);
  }

  private boolean isDeckValid(List deck) {

    // To be implemented.
    return false;
  }

}
