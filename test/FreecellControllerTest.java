import org.junit.Before;
import org.junit.Test;

import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;

import static org.junit.Assert.assertEquals;

public class FreecellControllerTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void playGame() {
    ControllerTypeChecks.checkSignatures();
    //MultiMoveModelTypeChecks.checkSignatures();
  }
}