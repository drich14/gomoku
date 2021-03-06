package gomoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static gomoku.EndReason.*;
import static gomoku.Outcome.DRAW;
import static gomoku.Outcome.LOSS;
import static gomoku.Outcome.WIN;
import static gomoku.Stone.OPPONENT;
import static java.util.Collections.singleton;

class GameCommunication {

    static final String MOVE_FILE = "move_file";
    static final String END_GAME = "end_game";

    private final String playerName;

    GameCommunication(String playerName) {
        this.playerName = playerName;
    }

    Boolean isOver() {
        return Files.exists(Paths.get(END_GAME));
    }

    void waitForTurn() {
        while (!Files.exists(Paths.get(playerFile(playerName)))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    Move readMove() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(MOVE_FILE));
            return parseMove(lines.get(0));
        } catch (IOException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Move parseMove(String line) {
        String[] tokens = line.split(" ");
        return new Move(OPPONENT, tokens[1].charAt(0), Integer.parseInt(tokens[2]));
    }

    void writeMove(Move move) {
        try {
            Debug.print("\n\n" + playerName + " made move " + move + "\n\n");
            Files.write(Paths.get(MOVE_FILE), singleton(playerName + " " + move.getCell()));
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    EndReason getEndReason() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(END_GAME));
            return parseEndReason(lines.get(0));
        } catch (IOException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    private EndReason parseEndReason(String line) {
        Optional<String> reason = Arrays.stream(line.split(" ")).skip(5).reduce((a, b) -> a + " " + b);
        if (reason.isPresent()) {
            if (reason.get().equals("Five in a row!"))
                return FIVE_IN_ROW;
            if (reason.get().equals("Time out!"))
                return OUT_OF_TIME;
            if (reason.get().equals("Out-of-order move!"))
                return OUT_OF_ORDER;
            if (reason.get().equals("Invalid move!"))
                return INVALID_MOVE;
        }
        return FULL_BOARD;
    }

    Outcome getOutcome() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(END_GAME));
            return parseOutcome(lines.get(0));
        } catch (IOException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Outcome parseOutcome(String line) {
        if (line.equals("END: TIE! board full!"))
            return DRAW;
        if (line.split(" ")[1].equals(playerName))
            return WIN;
        return LOSS;
    }

    static String playerFile(String playerName) {
        return playerName + ".go";
    }

    String getPlayerName() {
        return playerName;
    }
}
