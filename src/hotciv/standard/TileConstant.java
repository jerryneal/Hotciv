package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Tile;

public class TileConstant implements Tile{
	Position position;
	String type;
	public TileConstant(Position position, String type) {
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
