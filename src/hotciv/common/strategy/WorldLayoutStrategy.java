package hotciv.common.strategy;

import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.TileConstant;
import hotciv.common.UnitImpl;

public interface WorldLayoutStrategy {
	
	public void createWorldLayout(GameWorld<UnitImpl, TileConstant, CityImpl> gameWorld);
}
