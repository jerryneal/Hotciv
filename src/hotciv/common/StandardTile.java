package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.Tile;

/**
 * The standard implementation of a tile.
 * Just a constructor with 2 getters.
 */
public class StandardTile implements Tile {
    private Position position;
    private String type;

    /**
     * The default and only constructor that simply stores the arguments for use in the getters.
     *
     * @param position The position
     * @param type     the type of tile.
     */
    public StandardTile(Position position, String type) {
        this.position = position;
        this.type = type;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getTypeString() {
        return this.type;
    }
}
