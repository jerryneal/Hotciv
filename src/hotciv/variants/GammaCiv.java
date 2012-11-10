package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameBuilder;
import hotciv.common.UnitImpl;
import hotciv.common.strategy.UnitAction;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.units.Archer;
import hotciv.framework.*;

/**
 * This variant handles unit actions, that the settler can fortify and the archer can
 *
 * @author: Erik
 * Date: 09-11-12, 23:35
 */
public class GammaCiv {
    private GammaCiv() {

    }

    public static Game getGame() {
        return new GameBuilder().setUnitFactoryStrategy(new UnitFactory() {
            public UnitImpl makeUnit(String typeString, Player owner) {
                if (GameConstants.ARCHER.equals(typeString)) {
                    return new GammaArcher(owner);
                } else {
                    // Return default implementation.
                    return BaseGame.DefaultStrategies.getUnitFactoryStrategy().makeUnit(typeString, owner);
                }
            }
        })
        .setUnitActionStrategy(new UnitAction() {
            @Override
            public void performAction(BaseGame game, Position position) {
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
                    // Remove the settler.
                    Unit settler = game.getUnitAt(position);
                    Player owner = settler.getOwner();
                    game.removeUnitAt(position);

                    // Place a city with same owner.
                    game.placeCityAt(position, new CityImpl(owner));
                } else {
                    // Do nothing!
                }

            }
        }).build();
    }

    private static class GammaArcher extends Archer {
        private boolean fortified;
        public GammaArcher(Player owner) {
            super(owner);
            this.fortified = false;
        }
        private void switchFortify() {
            fortified = !fortified;
        }
        @Override
        public int getMoveCount() {
            if (fortified) {
                return 0;
            }
            else {
                return super.getMoveCount();
            }
        }
        @Override
        public int getDefensiveStrength() {
            if (fortified) {
                return super.getDefensiveStrength() * 2;
            }
            else {
                return super.getDefensiveStrength();
            }
        }
    }
}
