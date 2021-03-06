package gomoku;

enum Stone {
    FRIENDLY,
    OPPONENT;

    Stone getOpponent() {
        switch (this) {
            case FRIENDLY: return OPPONENT;
            case OPPONENT: return FRIENDLY;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return (this == FRIENDLY) ? "F" : "O";
    }
}
