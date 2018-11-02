package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//Do we need the abstract class???
public abstract class FreecellModelAbstract implements FreecellOperations{

  private final int CARDNUM;
  private final int CARDTYPENUM;
  private final int SUITTYPENUM;
  private final int cascades;
  private final int opens;
  private List<List<Card>> cascadePile;
  private List<Card> openPile;
  private List<List<Card>> foundationPile;
  private boolean startGameFlag;

  /**
   * The constructor build up a Freecell game with provides number of cascade piles and open piles.
   *
   * @param cascades Number of cascade pile.
   * @param opens    Number of open pile.
   */

  public FreecellModelAbstract(int cascades, int opens) {
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

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is no restriction
   * imposed on the ordering of these cards in the deck. An invalid deck is defined as a deck that
   * has one or more of these flaws:
   * <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */

  public List getDeck() {

    List<Card> deck = new ArrayList();

    for (int i = 0; i < CARDTYPENUM; i++) {
      deck.add(new Card(CardType.SPADES, i + 1));
      deck.add(new Card(CardType.DIAMONDS, i + 1));
      deck.add(new Card(CardType.HEARTS, i + 1));
      deck.add(new Card(CardType.CLUBS, i + 1));
    }

    return deck;
  }

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling it first. This
   * method first verifies that the deck is valid. It deals the deck among the cascade piles in
   * roundrobin fashion. Thus if there are 4 cascade piles, the 1st pile will get cards 0, 4, 8,
   * ..., the 2nd pile will get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and
   * the 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade piles, they may
   * have a different number of cards
   *
   * @param deck    the deck to be dealt
   * @param shuffle if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */

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
  }

  /**
   * Move a card from the given source pile to the given destination pile, if the move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link PileType})
   * @throws IllegalStateException    if a move is attempted before the game has starts
   */

  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber)
          throws IllegalArgumentException, IllegalStateException {

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

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */

  public boolean isGameOver() {
    if (!this.startGameFlag) {
      return false;
    }
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

  /**
   * Return the present state of the game as a string. The string is formatted as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above
   */

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
        //remove this to get around of length problem
        ans += " " + foundationPile.get(i).get(foundationPile.get(i).size() - 1).toString();
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
    return ans.substring(0, ans.length() - 1);
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
}
