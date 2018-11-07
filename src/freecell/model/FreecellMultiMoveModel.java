package freecell.model;

import java.util.List;

/**
 * A more realistic version of Freecell is one where the player can move several cards at once from
 * one cascade pile to another (while it is also possible to move several cards from a cascade pile
 * to a foundation pile, we will ignore this feature in this variation). Moving multiple cards must
 * obey two conditions. The first condition is that they should form a valid build, i.e. they should
 * be arranged in alternating colors and consecutive, descending values in the cascade pile that
 * they are moving from. The second condition is the same for any move to a cascade pile: these
 * cards should form a build with the last card in the destination cascade pile.
 */

public class FreecellMultiMoveModel extends FreecellModelAbstract {

  /**
   * The constructor build up a MultiFreecell game with provides number of cascade piles and open
   * piles.
   *
   * @param cascades Number of cascade pile.
   * @param opens    Number of open pile.
   */

  private FreecellMultiMoveModel(int cascades, int opens) {
    super(cascades, opens);
  }

  /**
   * Move a card from the given source pile to the given destination pile, if the move is valid. The
   * version of move() method is upgraded for some multi-move functionality.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link PileType})
   * @throws IllegalStateException    if a move is attempted before the game has starts
   */

  // The move method needs to be overridden to fit with multi card movements.
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber)
          throws IllegalArgumentException, IllegalStateException {

    if (!this.startGameFlag) {
      throw new IllegalStateException("The game has not started.");
    }
    // Get the current card to be move.

    boolean multipleMove = false;
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

    } else if (source == PileType.CASCADE) {

      // modify here to support multiple move. and multimove cannot move to open pile.

      if (pileNumber >= cascades || pileNumber < 0) { //suppose 0 1 2 3
        throw new IllegalArgumentException("Pile number out of index.");
      }

      sourceList = cascadePile.get(pileNumber);

      if (sourceList.isEmpty()) { // When the pile is empty, there is no element in the list.
        throw new IllegalArgumentException("Source pile does not contain any card.");
      }

      if (cardIndex >= sourceList.size() || cardIndex < 0) {
        throw new IllegalArgumentException("Card index is invalid.");
      }

      currentCard = (Card) sourceList.get(cardIndex);
      int moveNum = 1;
      int emptyCascade = 0;
      int emptyOpen = 0;
      Card lastCard = new Card(currentCard.getType(), currentCard.getRank());

      for (int i = cardIndex + 1; i < sourceList.size(); i++) {
        Card card = (Card) sourceList.get(i);
        if (card.getColor() == lastCard.getColor() || card.getRank() + 1 != lastCard.getRank()) {
          throw new IllegalArgumentException("Multi-move on cards out of order");
        }
        lastCard = card;
        moveNum++;
      }

      if (moveNum > 1) {

        for (int i = 0; i < cascadePile.size(); i++) {
          if (cascadePile.get(i).isEmpty()) {
            emptyCascade++;
          }
        }

        for (int i = 0; i < openPile.size(); i++) {
          if (openPile.get(i) == null) {
            emptyOpen++;
          }
        }

        if (moveNum > (emptyOpen + 1) * Math.pow(2.0, emptyCascade)) {
          throw new IllegalArgumentException("There is not enough space for that many card move.");
        }

        multipleMove = true;
      }

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

      if (multipleMove) {
        throw new IllegalArgumentException("Cannot multi-move to foundation");
      }

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

      if (multipleMove) {
        throw new IllegalArgumentException("Cannot multi-move to open");
      }

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
    } else if (multipleMove) {
      List<Card> toMove = sourceList.subList(cardIndex, sourceList.size());
      targetList.addAll(toMove);
      sourceList.subList(cardIndex, sourceList.size()).clear();
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

    /**
     * The builder method helps tp construct game with step-by-step assigning value process. The
     * particular method defines the number of cascade pile in the game.
     *
     * @param c The number cascade pile.
     * @return The builder for the following construction.
     */

    public FreecellOperationsBuilder cascades(int c) {
      if (c < 4) {
        throw new IllegalArgumentException("Cascades should be at least 4.");
      }
      this.cascades = c;
      return this;
    }

    /**
     * The builder method helps tp construct game with step-by-step assigning value process. The
     * particular method defines the number of open pile in the game.
     *
     * @param o The number open pile.
     * @return The builder for the following construction.
     */

    public FreecellOperationsBuilder opens(int o) {
      if (o < 1) {
        throw new IllegalArgumentException("Opens should be at least 1.");
      }
      this.opens = o;
      return this;
    }

    /**
     * The method actually do the construction of the game.
     *
     * @return The Freecell game with given parameter above.
     */

    public <K> FreecellOperations<K> build() {
      return new FreecellMultiMoveModel(cascades, opens);
    }
  }
}
