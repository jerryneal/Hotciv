package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.UnitImpl;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * //TODO: Doc
 *
 * @author: Erik
 * Created: 16-11-12, 10:25
 */
public class DeltaWorldLayout implements WorldLayoutStrategy {
    @Override
    public void createWorldLayout(BaseGame game) {
        GameWorld<UnitImpl, CityImpl> gameWorld = game.getGameWorld();

        // first and foremost red city at (8, 12), blue city at (4, 5)
        gameWorld.placeCity(new Position(8, 12), new CityImpl(Player.RED));
        gameWorld.placeCity(new Position(4, 5), new CityImpl(Player.BLUE));

        // Units
        UnitFactory unitFactory = game.getUnitFactory();
        // Blue legion left of blue city.
        gameWorld.placeUnit(new Position(4, 4), unitFactory.makeUnit(game, GameConstants.LEGION, Player.BLUE));
        // Red settler just south of blue city.
        gameWorld.placeUnit(new Position(5, 5), unitFactory.makeUnit(game, GameConstants.SETTLER, Player.RED));
        // Red archer north east of the blue city.
        gameWorld.placeUnit(new Position(3, 8), unitFactory.makeUnit(game, GameConstants.ARCHER, Player.RED));

        String[] worldLayout = new String[]{
                "OOOPPMPPPPPOOOOO",
                "OOPHHPPPPFFFPPOO",
                "OPPPPPMPPPOOOPPO",
                "OPPMMMPPPPOOPPPP",
                "OOOPPPPPHHPPPPOO",
                "OPFPPPPPPPPHHPPO",
                "OOOPPPOOOOOOOOOO",
                "OPPPPPOPPPHPPMOO",
                "OPPPPPOPPHPPPFOO",
                "PFFFPPPPOPFFPPPP",
                "PPPPPPPPOOOPPPPP",
                "OPPMMMPPPPOOOOOO",
                "OOPPPPPPFFPPPPOO",
                "OOOOPPPPPPPPPOOO",
                "OOPPPHHPPOOOOOOO",
                "OOOOOPPPPPPPPPOO"
        };
        gameWorld.populateWorld(worldLayout);
    }
}
