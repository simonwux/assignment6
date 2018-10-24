package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreecellModel implements FreecellOperations {

  private final int CARDNUM;
  private final int CARDTYPENUM;
  private final int SUITTYPENUM;
  private final int cascades;
  private final int opens;
  private List<List<Card>> cascadePile;
  private List<Card> openPile;
  private List<List<Card>> foundationPile;
  private boolean startGameFlag;

  private FreecellModel(int cascades, int opens) {
    this.startGameFlag = false;
    this.CARDNUM = 52;
    this.CARDTYPENUM = 13;
    this.SUITTYPENUM = CardType.values().length;
    this.cascades = cascades;
    this.opens = opens;
    this.cascadePile = new ArrayList();
    for (int i = 0; i < this.cascades; i++) {
      this.cascadePile.add(new ArrayList<Card>());
    }
    this.openPile = new ArrayList();
    for (int i = 0; i < this.opens; i++) {
      this.openPile.add(null);
    }
    this.foundationPile = new ArrayList();
    for (int i = 0; i < this.SUITTYPENUM; i++) {
      this.foundationPile.add(new ArrayList<Card>());
    }
  }

  // Still confusing about what this function do, so I suppose it returns a new deck.

  public List getDeck() {

    List<Card> Deck = new ArrayList();

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
    if (!isDeckValid(deck)) {
      throw new IllegalArgumentException("Invalid deck.");
    }
    if (shuffle) {
      shuffle(deck);
    }
    this.startGameFlag = true;
    this.cascadePile = new ArrayList();
    for (int i = 0; i < this.cascades; i++) {
      this.cascadePile.add(new ArrayList<Card>());
    }
    this.openPile = new ArrayList();
    for (int i = 0; i < this.opens; i++) {
      this.openPile.add(null);
    }
    this.foundationPile = new ArrayList();
    for (int i = 0; i < this.SUITTYPENUM; i++) {
      this.foundationPile.add(new ArrayList<Card>());
    }
    for (int i = 0; i < this.cascades; i++) {
      for (int j = i; j < this.CARDNUM; j += this.cascades) {
        this.cascadePile.get(i).add((Card) deck.get(j));
      }
    }
    System.out.println(this.cascadePile);
  }

  public void move(PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    if (!this.startGameFlag) {
      throw new IllegalStateException("The game has not started.");
    }
    // Get the current card to be move.

    Card currentCard;
    List sourceList;

    if (source == PileType.OPEN) {

      if (pileNumber >= opens || pileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index.");
      }

      Card targetCard = openPile.get(pileNumber); // initialized with null.

      if (targetCard == null) {
        throw new IllegalArgumentException("There is no such card to be move.");
      }

      currentCard = openPile.get(pileNumber);
      sourceList = openPile;

    } else if (source == PileType.CASCADE) { // Only the top card of a cascade pile can be moved.

      if (pileNumber >= cascades || pileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index.");
      }

      sourceList = cascadePile.get(pileNumber);

      if (sourceList.isEmpty()) { // When the pile is empty, there is no element in the list.
        throw new IllegalArgumentException("Source pile does not contain any card.");
      }

      if (cardIndex != sourceList.size() - 1) {
        throw new IllegalArgumentException("Card to be move is not top card of a cascade pile.");
      }

      currentCard = (Card) sourceList.get(cardIndex);

    } else {

      if (pileNumber >= SUITTYPENUM || pileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index. ");
      }

      sourceList = foundationPile.get(pileNumber);

      if (sourceList.isEmpty()) { // When the pile is empty, there is no element in the list.
        throw new IllegalArgumentException("Target pile does not contain any card.");
      }

      if (cardIndex != sourceList.size() - 1) {
        throw new IllegalArgumentException("Card to be move is not top card of a foundation pile.");
      }

      currentCard = (Card) sourceList.get(cardIndex);

    }

    boolean movable = false;
    List targetList;

    // A card can be added added to a foundation iff its suit match that of the pile, and its value
    // is one more that that of the card currently one top of the pile. If the foundation pile is
    // currently empty, any ace can be added.

    if (destination == PileType.FOUNDATION) {

      if (destPileNumber >= SUITTYPENUM || destPileNumber < 0) {
        throw new IllegalArgumentException("Pile number out of index.");
      }

      targetList = foundationPile.get(destPileNumber);

      if (targetList.isEmpty()) {

        if (currentCard.getRank() == 1) {
          movable = true;
        }

      } else {

        Card topCard = (Card) targetList.get(targetList.size() - 1);

        if (topCard.getType() == currentCard.getType()
                && topCard.getRank() + 1 == currentCard.getRank()) {
          movable = true;
        }
      }

    }

    // An open pile may contain at most one card.

    else if (destination == PileType.OPEN) {

      if (destPileNumber >= opens || destPileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index.");
      }

      targetList = openPile;

      if (targetList.get(destPileNumber) == null) {
        movable = true;
      }
    }

    // However, a card from some pile can be moved to the end of a cascade pile if and only if
    // its color is different from that of the currently last card, and its value is exactly one
    // less than that of the currently last card

    else {

      if (destPileNumber >= cascades || destPileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index.");
      }

      targetList = cascadePile.get(destPileNumber);

      if (targetList.isEmpty()) {
        movable = true;
      } else {

        Card topCard = (Card) targetList.get(targetList.size() - 1);

        if (topCard.getColor() != currentCard.getColor()
                && topCard.getRank() - 1 == currentCard.getRank()) {
          movable = true;
        }
      }

    }

    // now we do the move.

    if (!movable) {
      throw new IllegalArgumentException("The move is not valid.");
    } else {

      if (sourceList == openPile) {
        sourceList.set(pileNumber, null);
      } else {
        sourceList.remove(currentCard);
      }

      if (targetList == openPile) {
        targetList.set(destPileNumber, currentCard);
      } else {
        targetList.add(currentCard);
      }
    }
  }

  public boolean isGameOver() {
    for (int i = 0; i < this.SUITTYPENUM; i++) {
      if (this.foundationPile.get(i).size() != this.CARDTYPENUM) {
        return false;
      }
      Card ace = this.foundationPile.get(i).get(0);
      for (int j = 1; j < this.CARDTYPENUM; j++) {
        Card now = this.foundationPile.get(i).get(j);
        if (now.getType() != ace.getType() || now.getRank() != ace.getRank() + j) {
          return false;
        }
      }
    }
    return true;
  }

  public String getGameState() {
    String ans = "";
    if (!this.startGameFlag) {
      return ans;
    }
    for (int i = 0; i < this.SUITTYPENUM; i++) {
      ans += "F" + Integer.toString(i + 1) + ":";
      for (int j = 0; j < this.foundationPile.get(i).size() - 1; j++) {
        ans += " " + this.foundationPile.get(i).get(j).toString() + ",";
      }
      if (this.foundationPile.get(i).size() > 0) {
        ans += " " + this.foundationPile.get(i).get(this.foundationPile.get(i).size() - 1).toString();
      }
      ans += "\n";
    }

    for (int i = 0; i < this.opens; i++) {
      ans += "O" + Integer.toString(i + 1) + ":";
      if (this.openPile.get(i) != null) {
        ans += " " + this.openPile.get(i).toString();
      }
      ans += "\n";
    }

    for (int i = 0; i < this.cascades; i++) {
      ans += "C" + Integer.toString(i + 1) + ":";
      for (int j = 0; j < this.cascadePile.get(i).size() - 1; j++) {
        ans += " " + this.cascadePile.get(i).get(j).toString() + ",";
      }
      if (this.cascadePile.get(i).size() > 0) {
        ans += " " + this.cascadePile.get(i).get(this.cascadePile.get(i).size() - 1).toString();
      }
      ans += "\n";
    }
    return ans;
  }

  private void shuffle(List deck) {
    Collections.shuffle(deck);
  }

  private boolean isDeckValid(List deck) {

    if (deck.size() != CARDNUM) {
      return false;
    }

    // A stupid but straightforward way to check duplicate.

    Set checkDuplicate = new HashSet();

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

    private FreecellOperationsBuilderImpl() {
      this.cascades = 8;
      this.opens = 4;
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
