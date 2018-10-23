package freecell.model;

public class FreecellOperationsBuilderImpl implements FreecellOperationsBuilder {

  private int cascades;
  private int opens;

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
