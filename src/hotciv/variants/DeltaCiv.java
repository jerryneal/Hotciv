package hotciv.variants;

import hotciv.common.CityImpl;
import hotciv.common.GameBuilder;
import hotciv.common.GameWorld;
import hotciv.common.TileConstant;
import hotciv.common.UnitImpl;
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
            public void createWorldLayout(GameWorld<UnitImpl, TileConstant, CityImpl> gameWorld) {
            	// first and foremost red city at (8, 12), blue city at (4, 5)
            	gameWorld.placeCity(new Position(8, 12), new CityImpl(Player.RED));
            	gameWorld.placeCity(new Position(4, 5), new CityImpl(Player.BLUE));
            	
            	
            	// Now for terrain: lots of plains
            	for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        gameWorld.placeTile(new Position(i, j), new TileConstant(new Position(i, j), GameConstants.PLAINS));
                    }
                }
            	
            	// other terrain grouped in sets
            	HashSet<Position> oceans = new HashSet<Position>();
            	HashSet<Position> forests = new HashSet<Position>();
            	HashSet<Position> mountains = new HashSet<Position>();
            	HashSet<Position> hills = new HashSet<Position>();
            	// first row
            	oceans.add(new Position(0, 0));
            	oceans.add(new Position(0, 1));
            	oceans.add(new Position(0, 2));
            	mountains.add(new Position(0, 5));
            	oceans.add(new Position(0, 11));
            	oceans.add(new Position(0, 12));
            	oceans.add(new Position(0, 13));
            	oceans.add(new Position(0, 14));
            	oceans.add(new Position(0, 15));
            	// second row
            	oceans.add(new Position(1, 0));
            	oceans.add(new Position(1, 1));
            	hills.add(new Position(1, 3));
            	hills.add(new Position(1, 4));
            	forests.add(new Position(1, 9));
            	forests.add(new Position(1, 10));
            	forests.add(new Position(1, 11));
            	oceans.add(new Position(1, 14));
            	oceans.add(new Position(1, 15));
            	// third row
            	oceans.add(new Position(2, 0));
            	mountains.add(new Position(2, 6));
            	oceans.add(new Position(2, 10));
            	oceans.add(new Position(2, 11));
            	oceans.add(new Position(2, 12));
            	oceans.add(new Position(2, 15));
            	// fourth row
            	oceans.add(new Position(3, 0));
            	mountains.add(new Position(3, 3));
            	mountains.add(new Position(3, 4));
            	mountains.add(new Position(3, 5));
            	oceans.add(new Position(3, 10));
            	oceans.add(new Position(3, 11));
            	// fifth row
            	oceans.add(new Position(4, 0));
            	oceans.add(new Position(4, 1));
            	oceans.add(new Position(4, 2));
            	hills.add(new Position(4, 8));
            	hills.add(new Position(4, 9));
            	oceans.add(new Position(4, 14));
            	oceans.add(new Position(4, 15));
            	// sixth row
            	oceans.add(new Position(5, 0));
            	forests.add(new Position(5, 2));
            	hills.add(new Position(5, 11));
            	hills.add(new Position(5, 12));
            	oceans.add(new Position(5, 15));
            	// seventh row
            	oceans.add(new Position(6, 0));
            	oceans.add(new Position(6, 1));
            	oceans.add(new Position(6, 2));
            	oceans.add(new Position(6, 6));
            	oceans.add(new Position(6, 7));
            	oceans.add(new Position(6, 8));
            	oceans.add(new Position(6, 9));
            	oceans.add(new Position(6, 10));
            	oceans.add(new Position(6, 12));
            	oceans.add(new Position(6, 13));
            	oceans.add(new Position(6, 14));
            	oceans.add(new Position(6, 15));
            	// eighth row
            	oceans.add(new Position(7, 0));
            	oceans.add(new Position(7, 6));
            	hills.add(new Position(7, 10));
            	mountains.add(new Position(7, 13));
            	oceans.add(new Position(7, 14));
            	oceans.add(new Position(7, 15));
            	// ninth row
            	oceans.add(new Position(8, 0));
            	oceans.add(new Position(8, 6));
            	hills.add(new Position(8, 9));
            	forests.add(new Position(8, 13));
            	oceans.add(new Position(8, 14));
            	oceans.add(new Position(8, 15));
            	// tenth row
            	forests.add(new Position(9, 1));
            	forests.add(new Position(9, 2));
            	forests.add(new Position(9, 3));
            	oceans.add(new Position(9, 8));
            	forests.add(new Position(9, 10));
            	forests.add(new Position(9, 11));
            	// eleventh row
            	oceans.add(new Position(10, 8));
            	oceans.add(new Position(10, 9));
            	oceans.add(new Position(10, 10));
            	// twelfth row
            	oceans.add(new Position(11, 0));
            	mountains.add(new Position(11, 3));
            	mountains.add(new Position(11, 4));
            	mountains.add(new Position(11, 5));
            	oceans.add(new Position(11, 10));
            	oceans.add(new Position(11, 11));
            	oceans.add(new Position(11, 12));
            	oceans.add(new Position(11, 13));
            	oceans.add(new Position(11, 14));
            	oceans.add(new Position(11, 15));
            	// thirteenth row
            	oceans.add(new Position(12, 0));
            	oceans.add(new Position(12, 1));
            	forests.add(new Position(12, 8));
            	forests.add(new Position(12, 9));
            	oceans.add(new Position(12, 14));
            	oceans.add(new Position(12, 15));
            	// fourteenth row
            	oceans.add(new Position(13, 0));
            	oceans.add(new Position(13, 1));
            	oceans.add(new Position(13, 2));
            	oceans.add(new Position(13, 3));
            	oceans.add(new Position(13, 13));
            	oceans.add(new Position(13, 14));
            	oceans.add(new Position(13, 15));
            	// fifteenth row
            	oceans.add(new Position(14, 0));
            	oceans.add(new Position(14, 1));
            	oceans.add(new Position(14, 9));
            	hills.add(new Position(14, 5));
            	hills.add(new Position(14, 6));
            	oceans.add(new Position(14, 10));
            	oceans.add(new Position(14, 11));
            	oceans.add(new Position(14, 12));
            	oceans.add(new Position(14, 13));
            	oceans.add(new Position(14, 14));
            	oceans.add(new Position(14, 15));
            	// sixteenth row
            	oceans.add(new Position(15, 0));
            	oceans.add(new Position(15, 1));
            	oceans.add(new Position(15, 2));
            	oceans.add(new Position(15, 3));
            	oceans.add(new Position(15, 4));
            	oceans.add(new Position(15, 14));
            	oceans.add(new Position(15, 15));
            	
            	// insert all terrain tiles
            	for (Position oceanPosition : oceans) {
            		gameWorld.placeTile(oceanPosition, new TileConstant(oceanPosition, GameConstants.OCEANS));
            	}
            	for (Position forestPosition : forests) {
            		gameWorld.placeTile(forestPosition, new TileConstant(forestPosition, GameConstants.FOREST));
            	}
            	for (Position mountainPosition : mountains) {
            		gameWorld.placeTile(mountainPosition, new TileConstant(mountainPosition, GameConstants.MOUNTAINS));
            	}
            	for (Position hillPosition : hills) {
            		gameWorld.placeTile(hillPosition, new TileConstant(hillPosition, GameConstants.HILLS));
            	}
            }
        }).build();
    }
}
