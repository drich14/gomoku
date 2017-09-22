package gomoku;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static gomoku.GameCommunication.END_GAME;
import static gomoku.GameCommunication.MOVE_FILE;
import static gomoku.Outcome.DRAW;
import static gomoku.Player.TIME_LIMIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class PlayerTest {


    private final String playerA = "A";
    private Result resultA;
    private final String playerB = "B";
    private Result resultB;
    private Throwable exception;

    @Test
    public void play3x3() {
        Debug.print = true;
        play(3, 3, 3, new AlgorithmImpl());

        assertNull(exception);
        assertEquals(DRAW, resultA.getOutcome());
        assertEquals(DRAW, resultB.getOutcome());
        assertEquals(9, resultA.getMoves().size());
        assertEquals(9, resultB.getMoves().size());
    }


    @Test
    public void play4x4() {
        Debug.print = true;
        Debug.debug = false;
        play(4, 4, 4, new AlgorithmImpl());

        assertNull(exception);
        assertEquals(DRAW, resultA.getOutcome());
        assertEquals(DRAW, resultB.getOutcome());
        assertEquals(16, resultA.getMoves().size());
        assertEquals(16, resultB.getMoves().size());
    }

    private void play(Integer width, Integer height, Integer winLength, Algorithm algorithm) {
        cleanUpGame();
        refGame(width, height, winLength, algorithm);
        cleanUpGame();
    }

    private void refGame(Integer width, Integer height, Integer winLength, Algorithm algorithm) {
        try {
            Thread threadA = startPlayer(playerA, width, height, winLength, TIME_LIMIT, algorithm);
            Thread threadB = startPlayer(playerB, width, height, winLength, TIME_LIMIT * 1000000000, algorithm);

            Process p = Runtime.getRuntime().exec("python test/referee.py " + playerA + " " + playerB + " "
                    + width + " " + height + " " + winLength);

            new StreamGobbler(p.getErrorStream()).start();
            new StreamGobbler(p.getInputStream()).start();
            p.waitFor();
            threadA.join();
            threadB.join();

            assertEquals(0, p.exitValue());
        } catch (IOException | InterruptedException e) {
            cleanUpGame();
            fail(e.getMessage());
        }
    }

    private Thread startPlayer(String playerName, Integer width, Integer height, Integer winLength, Double timeout, Algorithm algorithm) {
        Thread thread = new Thread(() -> {
            Result result = new Player(new GameCommunication(playerName), new Board(width, height), winLength, timeout, algorithm).play();
            Debug.print(result);
            if (playerName.equals(playerA))
                resultA = result;
            else
                resultB = result;
        });
        thread.setUncaughtExceptionHandler((th, e) -> {
            exception = e;
            e.printStackTrace();
        });
        thread.start();
        return thread;
    }

    private void cleanUpGame() {
        tryDelete(playerA + ".go");
        tryDelete(playerB + ".go");
        tryDelete(MOVE_FILE);
        tryDelete(END_GAME);
    }

    private void tryDelete(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException ignored) {
        }
    }

    class StreamGobbler extends Thread {
        final InputStream is;

        StreamGobbler(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ( (line = br.readLine()) != null)
                    Debug.print(line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}