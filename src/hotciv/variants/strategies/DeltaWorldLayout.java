package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.UnitImpl;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * //TODO: Doc
 *
 * @author: Erik
 * Created: 16-11-12, 10:25
 */
public class DeltaWorldLayout implements WorldLayoutStrategy{
    @Override
    public void createWorldLayout(BaseGame game) {
        GameWorld<UnitImpl, CityImpl> gameWorld = game.getGameWorld();

        // first and foremost red city at (8, 12), blue city at (4, 5)
        gameWorld.placeCity(new Position(8, 12), new CityImpl(Player.RED));
        gameWorld.placeCity(new Position(4, 5), new CityImpl(Player.BLUE));

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
