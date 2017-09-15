package gomoku;


public class Main {

    private static final String PLAYER_NAME = "bid";

    public static void main(String[] args) {
        String playerName = parseArgs(args);
        Game game = new Game(playerName);
        Result result = game.play();
    }

    private static String parseArgs(String[] args) {
        if (args.length > 0)
            return args[0];
        else
            return PLAYER_NAME;
    }
}
