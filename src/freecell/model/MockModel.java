package freecell.model;

public class MockModel extends FreecellModelAbstract {
  private StringBuilder log;

  public MockModel(int cascades, int opens, StringBuilder log) {
    super(cascades, opens);
    this.log = log;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    log.append(source.toString()
            + ' ' + Integer.toString(pileNumber)
            + ' ' + Integer.toString(cardIndex)
            + ' ' + destination.toString()
            + ' ' + Integer.toString(destPileNumber) + '\n');
  }
}
