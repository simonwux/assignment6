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

  private String input(Scanner scan) {
    String st;
    try {
      st = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Reader fails.");
    }
    return st;
  }

  private void output(String st) {
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
      output(model.getGameState() + "\n");
      while (true) {
        a = input(scan);
        System.out.println(a);
        if (isQuit(a)) {
          output("Game quit prematurely.\n");
          // System.out.println(this.out.toString());
          return;
        }
        if (isInvalidPile(a)) {
          output("Invalid source pile name, input again.\n");
          continue;
        }
        try {
          sourceIndex = Integer.parseInt(a.substring(1));
        } catch (IllegalArgumentException e) {
          output("Invalid source pile index, input again.\n");
          continue;
        }
        sourceIndex -= 1;

        sourceType = getType(a);
        break;
      }

      while (true) {
        b = input(scan);
        if (b == "q" || b == "Q") {
          output("Game quit prematurely.\n");
          return;
        }
        try {
          cardIndex = Integer.parseInt(b);
        } catch (IllegalArgumentException e) {
          output("Invalid card index, input again.\n");
          continue;
        }
        cardIndex -= 1;
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
        } catch (IllegalArgumentException e) {
          output("Invalid source pile index, input again.\n");
          continue;
        }
        targetIndex -= 1;

        targetType = getType(c);
        break;
      }

      try {
//        System.out.println(sourceType);
//        System.out.println(sourceIndex);
//        System.out.println(cardIndex);
//        System.out.println(targetType);
//        System.out.println(targetIndex);
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
