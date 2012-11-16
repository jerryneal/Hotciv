package hotciv.variants.units;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.UnitImpl;
import hotciv.common.units.Settler;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

/**
 * //TODO: Doc
 *
 * @author: Erik
 * Created: 16-11-12, 11:03
 */
public class GammaSettler extends Settler {
    private BaseGame game;

    /**
     * The constructor for a settler.
     * This constructor is only to be called through a UnitFactory!
     *
     * @param owner The owner of the archer.
     */
    public GammaSettler(BaseGame game, Player owner) {
        super(owner);
        this.game = game;
    }

    @Override
    public void performAction() {
        GameWorld<UnitImpl, CityImpl> gameWorld = game.getGameWorld();
        Position position = gameWorld.getUnitPosition(this);
        // Move is invalid if there is already a city.
        if (game.getCityAt(position) != null)
            return;
        // Remove the settler.
        gameWorld.removeUnit(position);

        // Place a city with same owner.
        gameWorld.placeCity(position, new CityImpl(this.getOwner()));
    }
}
