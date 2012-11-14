package hotciv.common.strategy;

import hotciv.common.*;

/**
 * This interface describes a method to construct the gameworld.
 */
public interface WorldLayoutStrategy {
    /**
     * Creates the gameworld by putting tiles, cities and units where they belong.
     * The GameObjectFactory must always be used to create the objects that are out on the GameWorld.
     *
     * Remember to get the relevant factory from the BaseGame if you want to create units.
     *
     * @param game The game to put stuff in. Get the world using game.getGameWorld.
     */
	public void createWorldLayout(BaseGame game);
}
