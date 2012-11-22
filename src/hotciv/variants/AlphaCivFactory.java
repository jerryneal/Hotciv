package hotciv.variants;

import hotciv.common.*;
import hotciv.common.strategy.*;
import hotciv.common.units.Archer;
import hotciv.common.units.Legion;
import hotciv.common.units.Settler;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

/**
 * // TODO: Doc
 * <p/>
 * // TODO: Change docs in methods and export the anonymous classes.
 *
 * @author Erik
 *         Created: 22-11-12, 13:09
 */
public class AlphaCivFactory implements GameStrategyFactory {

    /**
     * Gets an instance of the default strategy to calculate the winner of the game.
     *
     * @return The default GetWinner
     */
    @Override
    public GetWinner createWinnerStrategy() {
        return new GetWinner() {
            public Player getWinner(BaseGame game) {
                if (game.getAge() >= -3000) {
                    return Player.RED;
                } else {
                    return null;
                }
            }
        };
    }

    /**
     * Gets an instance of the default strategy to calculate the new age of the game after each round.
     *
     * @return the default NewAgeCalculator
     */
    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy() {
        return new NewAgeCalculator() {
            public int getNewAge(BaseGame game) {
                return game.getAge() + 100;
            }
        };
    }

    /**
     * Gets an instance of the default strategy for making units.
     *
     * @return the default UnitFactory.
     */
    @Override
    public UnitFactory createUnitFactoryStrategy() {
        return new UnitFactory() {
            public AbstractUnit makeUnit(BaseGame game, String typeString, Player owner) {
                if (GameConstants.ARCHER.equals(typeString)) {
                    return new Archer(owner);
                } else if (GameConstants.SETTLER.equals(typeString)) {
                    return new Settler(owner);
                } else if (GameConstants.LEGION.equals(typeString)) {
                    return new Legion(owner);
                } else {
                    throw new RuntimeException("Unrecognized unit type: " + typeString);
                }
            }
        };
    }

    /**
     * Gets an instance of the default strategy for creating the world.
     *
     * @return the default WorldLayoutStrategy.
     */
    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new WorldLayoutStrategy() {
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
        };
    }

    /**
     * Gets the default attackResolver where the attacker always wins.
     *
     * @return The attackResolver
     */
    @Override
    public AttackResolver createAttackResolverStrategy() {
        return new AttackResolver() {
            @Override
            public boolean doesAttackerWin(BaseGame game, Unit attacker, Unit defender) {
                return true;
            }
        };
    }
}
