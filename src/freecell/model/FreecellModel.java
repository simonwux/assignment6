package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreecellModel implements FreecellOperations {

  private final int CARDNUM;
  private final int CARDTYPENUM;
  private final int cascades;
  private final int opens;

  public FreecellModel(int cascades, int opens) {
    this.CARDNUM = 52;
    this.CARDTYPENUM = 13;
    this.cascades = cascades;
    this.opens = opens;
  }

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

    if (deck.size() != CARDNUM) {
      return false;
    }

    // A stupid but straightforward way to check duplicate.

    List checkDuplicate = new ArrayList();

    for (int i = 0; i < deck.size(); i++) {

      Object currentCard = deck.get(i);

      if (!(currentCard instanceof Card)) {
        return false;
      }

      if (checkDuplicate.contains(currentCard)) {
        return false;
      }

      checkDuplicate.add(currentCard);
    }

    // Since we initialize the card with pre-defined features,
    // it is impossible to have invalid card/

    return true;
  }

  public static FreecellOperationsBuilderImpl getBuilder() {
    return new FreecellOperationsBuilderImpl();
  }

  public static class FreecellOperationsBuilderImpl implements FreecellOperationsBuilder {

    private int cascades;
    private int opens;

    private FreecellOperationsBuilderImpl(){
    }

    public FreecellOperationsBuilder cascades(int c) {
      this.cascades = c;
      return this;
    }

    public FreecellOperationsBuilder opens(int o) {
      this.opens = o;
      return this;
    }

    public <K> FreecellOperations<K> build() {
      return new FreecellModel(cascades, opens);
    }
  }
}
