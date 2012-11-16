package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.UnitImpl;
import hotciv.common.strategy.UnitAction;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.variants.units.GammaArcher;

/**
 * //TODO: Doc.
 *
 * @author: Erik
 * Created: 16-11-12, Time: 10:21
 */
public class GammaUnitAction implements UnitAction {
    public void performAction(BaseGame game, Position position) {
        GameWorld<UnitImpl, CityImpl> gameWorld = game.getGameWorld();
        Unit unit = game.getUnitAt(position);
        String typeString = unit.getTypeString();
        if (GameConstants.ARCHER.equals(typeString)) {
            if (unit instanceof GammaArcher) {
                GammaArcher archer = (GammaArcher) unit;
                archer.switchFortify();
            } else {
                throw new RuntimeException("I expect an archer to be instanceof GammaArcher, since i specified it with the unitfactory. ");
            }
        } else if (GameConstants.SETTLER.equals(typeString)) {
            // Move is invalid if there is already a city.
            if (game.getCityAt(position) != null)
                return;
            // Remove the settler.
            Unit settler = game.getUnitAt(position);
            Player owner = settler.getOwner();
            gameWorld.removeUnit(position);

            // Place a city with same owner.
            gameWorld.placeCity(position, new CityImpl(owner));
        } else {
            // Do nothing!
        }

    }
}
