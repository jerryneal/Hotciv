package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * Creates the world layout specified by DeltaCiv.
 *
 * @author Erik
 *         Created: 16-11-12, 10:25
 */
public class DeltaWorldLayout implements WorldLayoutStrategy {
    private BaseGame game;

    public DeltaWorldLayout(BaseGame game) {
        this.game = game;
    }

    @Override
    public void createWorldLayout() {
        GameWorld gameWorld = game.getGameWorld();

        // first and foremost red city at (8, 12), blue city at (4, 5)
        gameWorld.placeCity(new Position(8, 12), new CityImpl(Player.RED));
        gameWorld.placeCity(new Position(4, 5), new CityImpl(Player.BLUE));

        // Blue legion left of blue city.
        gameWorld.placeNewUnit(new Position(4, 4), GameConstants.LEGION, Player.BLUE);
        // Red settler just south of blue city.
        gameWorld.placeNewUnit(new Position(5, 5), GameConstants.SETTLER, Player.RED);
        // Red archer north east of the blue city.
        gameWorld.placeNewUnit(new Position(3, 8), GameConstants.ARCHER, Player.RED);

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
