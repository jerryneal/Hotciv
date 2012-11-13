package hotciv.common.strategy;

import hotciv.common.*;

/**
 * This interface describes a method to construct the gameworld.
 */
public interface WorldLayoutStrategy {
    /**
     * Creates the gameworld by putting tiles, cities and units where they belong.
     * The GameObjectFactory must always be used to create the objects that are out on the GameWorld.
     * @param gameWorld The world to put stuff on.
     * @param factory The factory to create stuff from.
     */
	public void createWorldLayout(GameWorld<UnitImpl, TileConstant, CityImpl> gameWorld, GameObjectFactory factory);
}
