import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import freecell.controller.FreecellController;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;

import static org.junit.Assert.assertEquals;

public class FreecellControllerTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void playGame() {
    FreecellOperations gameOne = FreecellModel
            .getBuilder()
            .cascades(8)
            .opens(4)
            .build();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1 8as 8as 8as 8as 8as 8 OAS A1 W1 O1 q");
    FreecellController controller = new FreecellController(in, out);
    controller.playGame(gameOne.getDeck(), gameOne, false);
    System.out.println(out.toString());
  }
}