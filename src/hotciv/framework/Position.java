package hotciv.framework;

import java.util.Iterator;


/**
 * Position on the world map.
 * <p/>
 * Responsibilities:
 * 1) Know a specific location (row,column).
 * 2) Calculate what positions are around the position.
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

    /**
     * Calculates the distance between the 2 points on the board.
     * So the distance between (0,0) and (2,2) is 2.
     *
     * @param p1 The position to calculates distance from.
     * @param p2 The position to calculates distance to.
     * @return The distance between the 2 points.
     */
    public static int getDistance(Position p1, Position p2) {
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

    /**
     * This method returns an iterable like the one returned by getPositionsWithinDistance().
     * This one just never stops, it returns an infinite number of positions.
     *
     * @return the iterable.
     */
    public Iterable<Position> getAroundIterable() {
        return getPositionsWithinDistance(0);
    }

    /**
     * This method returns an iterable that iterates around the position, going clockwise in increasing distances,
     * until the distance is greater than the parameter maxDistance.
     * Note that it does not return the current position (this), the first position the iterator returns is the position just north of "this".
     * This iterable doesn't care if the positions are out of bounds, it just returns the positions going clockwise.
     * The hasNext method of the iterator always return true if the next position isn't further away than the maxdistance,
     * or true if maxDistance = 0, so in the last case there is an unlimited number of Positions in the iterator.
     *
     * @param maxDistance The maximum distance to return position from. Or 0 if no limit.
     * @return the iterable.
     */
    public Iterable<Position> getPositionsWithinDistance(final int maxDistance) {
        final Position center = this;
        return new Iterable<Position>() {
            @Override
            public Iterator<Position> iterator() {
                return new Iterator<Position>() {
                    Position position = center.getNorth();
                    Position startNextDistanceIterationPosition = position;
                    int currentDistance = 1;
                    Direction direction = Direction.RIGHT;

                    @Override
                    public boolean hasNext() {
                        if (maxDistance == 0) {
                            return true;
                        }
                        return currentDistance <= maxDistance;
                    }

                    @Override
                    public Position next() {
                        Position result = position;

                        Position next = getNextInDirection(direction, position);
                        if (getDistance(center, next) > currentDistance) {
                            direction = getNextClockwiseDirection(direction);
                            next = getNextInDirection(direction, position);
                        }
                        if (next.equals(startNextDistanceIterationPosition)) {
                            next = next.getNorth();
                            startNextDistanceIterationPosition = next;
                            currentDistance += 1;
                        }
                        position = next;

                        return result;
                    }

                    private Position getNextInDirection(Direction direction, Position position) {
                        switch (direction) {
                            case UP:
                                return position.getNorth();
                            case RIGHT:
                                return position.getEast();
                            case DOWN:
                                return position.getSouth();
                            case LEFT:
                                return position.getWest();
                            default:
                                throw new RuntimeException("Unrecognized direction: " + direction);
                        }
                    }

                    private Direction getNextClockwiseDirection(Direction direction) {
                        switch (direction) {
                            case UP:
                                return Direction.RIGHT;
                            case RIGHT:
                                return Direction.DOWN;
                            case DOWN:
                                return Direction.LEFT;
                            case LEFT:
                                return Direction.UP;
                            default:
                                throw new RuntimeException("Unrecognized direction: " + direction);
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Remove is not supported");
                    }

                };
            }
        };
    }

    private enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
}
