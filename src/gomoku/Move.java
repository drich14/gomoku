package gomoku;

import static java.lang.Character.toLowerCase;

class Move {

    private static final Character INDEX = 'a';

    private final Stone stone;
    private final Integer column;
    private final Integer row;

    Move(Stone stone, Character column, Integer row) {
        this(stone, toLowerCase(column) - INDEX, row);
    }

    Move(Stone stone, Integer column, Integer row) {
        this.stone = stone;
        this.column = column;
        this.row = row;
    }

    @Override
    public String toString() {
        return (char) (column + INDEX) + " " + row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (stone != move.stone) return false;
        if (column != null ? !column.equals(move.column) : move.column != null) return false;
        return row != null ? row.equals(move.row) : move.row == null;
    }

    @Override
    public int hashCode() {
        int result = stone != null ? stone.hashCode() : 0;
        result = 31 * result + (column != null ? column.hashCode() : 0);
        result = 31 * result + (row != null ? row.hashCode() : 0);
        return result;
    }

    Integer getColumn() {
        return column;
    }

    Stone getStone() {
        return stone;
    }

    Integer getRow() {
        return row;
    }
}
