package freecell.model;

public interface FreecellOperationsBuilder {

  /**
   * The builder method helps tp construct game with step-by-step assigning value process.
   * The particular method defines the number of cascade pile in the game.
   * @param c The number cascade pile.
   * @return The builder for the following construction.
   */

  FreecellOperationsBuilder cascades(int c);

  /**
   * The builder method helps tp construct game with step-by-step assigning value process.
   * The particular method defines the number of open pile in the game.
   * @param o The number open pile.
   * @return The builder for the following construction.
   */

  FreecellOperationsBuilder opens(int o);

  /**
   * The method actually do the construction of the game.
   * @return The Freecell game with given parameter above.
   */

  <K> FreecellOperations<K> build();
}