package gomoku;

import org.junit.Test;

import static gomoku.Stone.FRIENDLY;
import static gomoku.Stone.OPPONENT;
import static org.junit.Assert.*;

public class MoveTest {
    @Test
    public void toStringIntConstructor() {
        Move move = new Move(FRIENDLY, 1, 0);

        assertEquals("F b 0", move.toString());
    }

    @Test
    public void toStringCharConstructor() {
        Move move = new Move(OPPONENT, 'B', 0);

        assertEquals("O b 0", move.toString());
    }

}