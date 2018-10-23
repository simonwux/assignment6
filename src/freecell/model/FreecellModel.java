package freecell.model;

import java.util.List;

public class FreecellModel implements FreecellOperations {
  public List getDeck() {
    return null;
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

}
