package freecell.model;

import java.util.List;

public class FreecellModel extends FreecellModelAbstract {

  /**
   * The constructor build up a Freecell game with provides number of cascade piles and open piles.
   *
   * @param cascades Number of cascade pile.
   * @param opens    Number of open pile.
   */
  private FreecellModel(int cascades, int opens) {
    super(cascades, opens);
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
    List<Card> sourceList;
    List<Card> targetList;

    if (source == PileType.OPEN) {
      currentCard = getSourceOpenPileCard(pileNumber);
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

      currentCard = sourceList.get(cardIndex);

    } else {
      sourceList = getSourceFoundationPile(pileNumber, cardIndex);
      currentCard = sourceList.get(cardIndex);
    }

    if (destination == PileType.FOUNDATION) {
      targetList = getTargetFoundationPile(destPileNumber, currentCard);
    } else if (destination == PileType.OPEN) {
      targetList = isTargetOpenPileMovable(destPileNumber)? openPile : null;
    } else {
      targetList = getTargetCascadePile(destPileNumber, currentCard);
    }

    if (targetList == null) {
      throw new IllegalArgumentException("The move is not valid.");
    } else {
      singleMove(sourceList, targetList, pileNumber, destPileNumber, currentCard);
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
      return new FreecellModel(cascades, opens);
    }
  }
}
