package gomoku;

import org.junit.Test;

import static gomoku.Algorithm.*;
import static gomoku.Player.TIME_LIMIT;
import static gomoku.Stone.FRIENDLY;
import static gomoku.Stone.OPPONENT;
import static org.junit.Assert.*;

public class AlgorithmImplTest {

    // 3 x 3

    @Test
    public void chooseMoveFriendlyBlock() {
        // O |   |
        // O | F |
        //(F)|   |
        Board board = new Board(3, 3)
                .withMove(new Move(OPPONENT, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 1))
                .withMove(new Move(OPPONENT, 0, 1));

        Move move = new Move(FRIENDLY, 0, 2);


        Double evaluation = new AlgorithmImpl().evaluateMove(new Move(FRIENDLY, 1, 2), board, 3, TIME_LIMIT);
        assertEquals(LOSS_VALUE, evaluation);
        assertEquals(move, new AlgorithmImpl().chooseMove(FRIENDLY, board, 3, TIME_LIMIT));
    }

    @Test
    public void chooseMoveOpponentBlock() {
        // O |   |
        // O | F |
        //(O)|   |
        Board board = new Board(3, 3)
                .withMove(new Move(OPPONENT, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 1))
                .withMove(new Move(OPPONENT, 0, 1));

        Move move = new Move(OPPONENT, 0, 2);

        Double evaluation = new AlgorithmImpl().evaluateMove(new Move(OPPONENT, 0, 2), board, 3, TIME_LIMIT);
        assertEquals(LOSS_VALUE, evaluation, 0.0);
        assertEquals(move, new AlgorithmImpl().chooseMove(OPPONENT, board, 3, TIME_LIMIT));
    }

    @Test
    public void evaluateMoveWin() {
        //  F | F |(F)
        //    |   |
        //    |   |
        Board board = new Board(3, 3)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 0));
        Move move = new Move(FRIENDLY, 2, 0);

        assertEquals(WIN_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveLoss1() {
        //  O | O |
        //  F |   |(F)
        //    |   |
        Board board = new Board(3, 3)
                .withMove(new Move(OPPONENT, 0, 0))
                .withMove(new Move(FRIENDLY, 0, 1))
                .withMove(new Move(OPPONENT, 1, 0));
        Move move = new Move(FRIENDLY, 2, 1);

        assertEquals(LOSS_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveLoss2() {
        // F |(F)|
        // F | O |
        // O |   |
        Board board = new Board(3, 3)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(OPPONENT, 1, 1))
                .withMove(new Move(FRIENDLY, 0, 1))
                .withMove(new Move(OPPONENT, 0, 2));

        Move move = new Move(FRIENDLY, 1, 0);

        assertEquals(LOSS_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    @Test
    public void chooseMove2() {
        // F |   |(F)
        // F | O |
        // O |   |
        Board board = new Board(3, 3)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(OPPONENT, 1, 1))
                .withMove(new Move(FRIENDLY, 0, 1))
                .withMove(new Move(OPPONENT, 0, 2));

        Move move = new Move(FRIENDLY, 2, 0);

        assertEquals(move, new AlgorithmImpl().chooseMove(FRIENDLY, board, 3, TIME_LIMIT));
    }

    @Test
    public void evaluateMoveLoss2Moves() {
        //  O | O |
        //  F | F | O
        // (F)| O | F
        Board board = new Board(3, 3)
                .withMove(new Move(OPPONENT, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 1))
                .withMove(new Move(OPPONENT, 2, 1))
                .withMove(new Move(FRIENDLY, 0, 1))
                .withMove(new Move(OPPONENT, 1, 2))
                .withMove(new Move(FRIENDLY, 2, 2))
                .withMove(new Move(OPPONENT, 1, 0));
        Move move = new Move(FRIENDLY, 0, 2);


        assertEquals(LOSS_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveLoss2MovesOppNext() {
        //  F | F |
        //  O | O | F
        // (O)| F | O
        Board board = new Board(3, 3)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(OPPONENT, 1, 1))
                .withMove(new Move(FRIENDLY, 2, 1))
                .withMove(new Move(OPPONENT, 0, 1))
                .withMove(new Move(FRIENDLY, 1, 2))
                .withMove(new Move(OPPONENT, 2, 2))
                .withMove(new Move(FRIENDLY, 1, 0));
        Move move = new Move(OPPONENT, 0, 2);


        assertEquals(WIN_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveLoss3Moves() {
        //  O | O |
        //  F | F | O
        // (F)|   | F
        Board board = new Board(3, 3)
                .withMove(new Move(FRIENDLY, 1, 1))
                .withMove(new Move(OPPONENT, 2, 1))
                .withMove(new Move(FRIENDLY, 0, 1))
                .withMove(new Move(OPPONENT, 0, 0))
                .withMove(new Move(FRIENDLY, 2, 2))
                .withMove(new Move(OPPONENT, 1, 0));
        Move move = new Move(FRIENDLY, 0, 2);

        assertEquals(LOSS_VALUE, new AlgorithmImpl().evaluateMove(move, board, 3, TIME_LIMIT), 0.0);
    }

    // 4 x 4

    @Test
    public void evaluateMoveWin4x4() {
        //  F | F | F |(F)
        //    |   |   |
        //    |   |   |
        //    |   |   |
        Board board = new Board(4, 4)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 0))
                .withMove(new Move(FRIENDLY, 2, 0));
        Move move = new Move(FRIENDLY, 3, 0);

        assertEquals(WIN_VALUE, new AlgorithmImpl().evaluateMove(move, board, 4, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveLoss4x4() {
        //  F | F | F |
        //    |   |   |(O)
        //    |   |   |
        //    |   |   |
        Board board = new Board(4, 4)
                .withMove(new Move(FRIENDLY, 0, 0))
                .withMove(new Move(FRIENDLY, 1, 0))
                .withMove(new Move(FRIENDLY, 2, 0));
        Move move = new Move(OPPONENT, 3, 0);

        assertEquals(WIN_VALUE, new AlgorithmImpl().evaluateMove(move, board, 4, TIME_LIMIT), 0.0);
    }

    @Test
    public void evaluateMoveDraw4x4() {
        // (F)|   |   |
        //    |   |   |
        //    |   |   |
        //    |   |   |
        Board board = new Board(4, 4);
        Move move = new Move(FRIENDLY, 0, 0);

        assertEquals(DRAW_VALUE, new AlgorithmImpl().evaluateMove(move, board, 4, TIME_LIMIT), 0.0);
    }

    // time limit

    @Test
    public void chooseMoveInTimeLimit() {
        //    |   |   |
        //    |   |   |
        //    |   |   |
        //    |   |   |
        Board board = new Board(4, 4);
        Long startTime = System.nanoTime();
        new AlgorithmImpl().chooseMove(FRIENDLY, board, 4, TIME_LIMIT);
        Long duration = System.nanoTime() - startTime;
        assertTrue("expected: " + TIME_LIMIT + "actual: " + duration,duration < TIME_LIMIT);
    }

}