package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 15:40
 */
public class AlphaCivWorldLayout implements WorldLayoutStrategy {
    public void createWorldLayout(BaseGame game) {
        GameWorld gameWorld = game.getGameWorld();
        String[] worldLayout = new String[]{
                "PHPPPPPPPPPPPPPP",
                "OPPPPPPPPPPPPPPP",
                "PPMPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
                "PPPPPPPPPPPPPPPP",
        };
        gameWorld.populateWorld(worldLayout);

        // Red has a city at (1,1)
        gameWorld.placeCity(new Position(1, 1), new CityImpl(Player.RED));
        // Blue has a city at (4,1)
        gameWorld.placeCity(new Position(4, 1), new CityImpl(Player.BLUE));

        // Red has a archer at (2,0)
        gameWorld.placeNewUnit(new Position(2, 0), GameConstants.ARCHER, Player.RED);
        // Red has a settler at (4,3)
        gameWorld.placeNewUnit(new Position(4, 3), GameConstants.SETTLER, Player.RED);
        // Blue has a legion at (3,2)
        gameWorld.placeNewUnit(new Position(3, 2), GameConstants.LEGION, Player.BLUE);
    }
}
