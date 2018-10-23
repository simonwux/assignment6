package freecell.model;

import java.util.List;

public class FreecellModel implements FreecellOperations {
  @Override
  public List getDeck() {
    return null;
  }

  @Override
  public void startGame(List deck, boolean shuffle) throws IllegalArgumentException {

  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber) throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String getGameState() {
    return null;
  }

}
