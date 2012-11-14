package hotciv.variants;

import hotciv.common.*;
import hotciv.common.strategy.*;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.HashSet;

/**
 * @author: Erik
 * Date: 09-11-12, Time: 11:38
 */
public class DeltaCiv {
    private DeltaCiv() {

    }
    public static Game getGame() {
        return new GameBuilder().setWorldLayoutStrategy(new WorldLayoutStrategy() {
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
        }).build();
    }
}
