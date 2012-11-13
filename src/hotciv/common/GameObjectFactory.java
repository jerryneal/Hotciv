package hotciv.common;


import hotciv.common.strategy.UnitFactory;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * This class is responsible for creating everything that goes on the map. Tiles, cities and units.
 * Where variants exists, it creates the instances through strategies.
 *
 * @author: Erik
 * Date: 11-11-12, 12:30
 */
public class GameObjectFactory {
    private UnitFactory unitFactory;
    private BaseGame game;

    protected GameObjectFactory(BaseGame game, UnitFactory unitFactory) {
        this.unitFactory = unitFactory;
        this.game = game;
    }
    public UnitImpl makeArcher(Player owner) {
        return unitFactory.makeUnit(game, GameConstants.ARCHER, owner);
    }
    public UnitImpl makeSettler(Player owner) {
        return unitFactory.makeUnit(game, GameConstants.SETTLER, owner);
    }
    public UnitImpl makeLegion(Player owner) {
        return unitFactory.makeUnit(game, GameConstants.LEGION, owner);
    }
    public TileConstant makeTile(Position position, String typeString) {
        return new TileConstant(position, typeString);
    }
    public CityImpl makeCity(Player owner) {
        return new CityImpl(owner);
    }
}
