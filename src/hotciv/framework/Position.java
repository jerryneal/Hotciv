package hotciv.framework;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Position on the world map.
 * <p/>
 * Responsibilities:
 * 1) Know a specific location (row,column).
 * <p/>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Computer Science Department
 * Aarhus University
 * <p/>
 * This source code is provided WITHOUT ANY WARRANTY either
 * expressed or implied. You may study, use, modify, and
 * distribute it for non-commercial purposes. For any
 * commercial use, see http://www.baerbak.com/
 */
public class Position {

    /**
     * create a position.
     *
     * @param r the row
     * @param c the column
     */
    public Position(int r, int c) {
        this.r = r;
        this.c = c;
    }

    protected int r;
    protected int c;

    /**
     * get the row represented by this position.
     *
     * @return the row.
     */
    public int getRow() {
        return r;
    }

    /**
     * get the column represented by this position.
     *
     * @return the column.
     */
    public int getColumn() {
        return c;
    }

    public static int getDistance(Position p1, Position p2) {
        // Since we can move in diagonals, its equals just moving
        int verticalDistance = Math.abs(p1.getRow() - p2.getRow());
        int horizontalDistance = Math.abs(p1.getColumn() - p2.getColumn());
        return Math.max(verticalDistance, horizontalDistance);
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != Position.class) {
            return false;
        }
        Position other = (Position) o;
        return r == other.r && c == other.c;
    }

    public int hashCode() {
        // works ok for positions up to columns == 479
        return 479 * r + c;
    }

    public String toString() {
        return "[" + r + "," + c + "]";
    }

    public Position getNorth() {
        return new Position(this.r - 1, this.c);
    }
    public Position getNorthEast() {
        return new Position(this.r - 1, this.c + 1);
    }
    public Position getEast() {
        return new Position(this.r, this.c + 1);
    }
    public Position getSouthEast() {
        return new Position(this.r + 1, this.c + 1);
    }
    public Position getSouth() {
        return new Position(this.r + 1, this.c);
    }
    public Position getSouthWest() {
        return new Position(this.r + 1, this.c - 1);
    }
    public Position getWest() {
        return new Position(this.r, this.c - 1);
    }
    public Position getNorthWest() {
        return new Position(this.r - 1, this.c - 1);
    }
    public Iterable<Position> getAroundIterable() {
        // For now really simple
        // TODO: More positions.
        return Arrays.asList(getNorth(), getNorthEast(), getEast(), getSouthEast(), getSouth(), getSouthWest(), getWest(), getNorthWest());
    }


}
