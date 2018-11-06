package freecell.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import freecell.model.FreecellOperations;
import freecell.model.PileType;

public class FreecellController implements IFreecellController {

  final Readable in;
  final Appendable out;

  public FreecellController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input and output should not be null.");
    }
    this.in = rd;
    this.out = ap;
  }

  private String input(Scanner scan) throws IllegalStateException {
    String st = "";
    try {
      st = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Reader fails.");
    }
    return st;
  }

  private void output(String st) throws IllegalStateException {
    try {
      this.out.append(st);
    } catch (IOException e) {
      throw new IllegalStateException("Output fails.");
    }
  }

  private boolean isQuit(String st) {
    return st.equals("q") || st.equals("Q");
  }

  private boolean isInvalidPile(String st) {
    return st.length() < 2 || (st.charAt(0) != 'C' && st.charAt(0) != 'F' && st.charAt(0) != 'O');
  }

  private PileType getType(String st) {
    PileType result = null;
    if (st.charAt(0) == 'C') {
      result = PileType.CASCADE;
    }
    if (st.charAt(0) == 'O') {
      result = PileType.OPEN;
    }
    if (st.charAt(0) == 'F') {
      result = PileType.FOUNDATION;
    }
    return result;
  }

  private boolean isNumeric(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void playGame(List deck, FreecellOperations model, boolean shuffle)
          throws IllegalArgumentException, IllegalStateException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck should not be null.");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    model.startGame(deck, shuffle);
    String a, b, c;
    PileType sourceType;
    int sourceIndex;
    int cardIndex;
    PileType targetType;
    int targetIndex;
    Scanner scan = new Scanner(this.in);
    //a = scan.next();
    while (true) {
      sourceIndex = -1;
      sourceType = null;
      cardIndex = -1;
      targetType = null;
      targetIndex = -1;
      output(model.getGameState() + "\n");
      while (true) {
        a = input(scan);
        if (isQuit(a)) {
          output("Game quit prematurely.\n");
          return;
        }
        if (isInvalidPile(a)) {
          output("Invalid source pile name, input again.\n");
          continue;
        }
        try {
          sourceIndex = Integer.parseInt(a.substring(1));
          sourceIndex -= 1;
        } catch (NumberFormatException e) {
          output("Invalid source pile index, input again.\n");
          continue;
        }

        sourceType = getType(a);
        break;
      }

      while (true) {
        b = input(scan);
        if (b.equals("q") || b.equals("Q")) {
          output("Game quit prematurely.\n");
          return;
        }

        try {
          cardIndex = Integer.parseInt(b);
          cardIndex -= 1;
        } catch (Exception e) {
          output("Invalid card index, input again.\n");
          continue;
        }
        break;
      }

      while (true) {
        c = input(scan);
        if (isQuit(c)) {
          output("Game quit prematurely.\n");
          return;
        }
        if (isInvalidPile(c)) {
          output("Invalid target pile name, input again.\n");
          continue;
        }
        try {
          targetIndex = Integer.parseInt(c.substring(1));
          targetIndex -= 1;
        } catch (NumberFormatException e) {
          output("Invalid target pile index, input again.\n");
          continue;
        }

        targetType = getType(c);
        break;
      }

      try {
        model.move(sourceType, sourceIndex, cardIndex, targetType, targetIndex);
      } catch (IllegalArgumentException e) {
        output("Invalid move. Try again. " + e.getMessage() + "\n");
      }

      if (model.isGameOver()) {
        output(model.getGameState() + "\n");
        output("Game over.\n");
        return;
      }
    }
  }
}
