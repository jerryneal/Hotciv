package hotciv.common;

import hotciv.common.strategy.*;
import hotciv.common.units.Archer;
import hotciv.common.units.Legion;
import hotciv.common.units.Settler;
import hotciv.framework.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a game instance, that does the most basic behaviour, and has a big constructor that specifies all the strategies this game uses.
 *
 * @author : Erik
 *         Date: 09-11-12, 11:22
 */
public class BaseGame implements Game {
    private GameWorld gameWorld;

    private Player playerTurn;

    private Set<Unit> movedUnits = new HashSet<Unit>();

    private int age = -4000;

    // Strategies
    private GetWinner getWinner;
    private NewAgeCalculator newAgeCalculator;
    private UnitFactory unitFactory;
    private WorldLayoutStrategy worldLayoutStrategy;
    private AttackResolver attackResolver;

    private Map<Player, Integer> attackWonCounter = new HashMap<Player, Integer>();

    private int roundCount = 1;

    /**
     * The default and only constructor for BaseGame.
     * The constructor is protected since only the GameBuilder is supposed to call it.
     * All the parameters are the different strategies used by this BaseGame.
     */
    protected BaseGame(GetWinner getWinner, NewAgeCalculator newAgeCalculator, UnitFactory unitFactory, WorldLayoutStrategy worldLayoutStrategy, AttackResolver attackResolver) {
        // First strategies
        this.getWinner = getWinner;
        this.newAgeCalculator = newAgeCalculator;
        this.unitFactory = unitFactory;
        this.worldLayoutStrategy = worldLayoutStrategy;
        this.attackResolver = attackResolver;

        // Player starts
        playerTurn = Player.RED;

        // Gameworld
        this.gameWorld = new GameWorld(this);
        createWorld();
    }

    /**
     * Creates the world, based on the worldLayoutStrategy.
     */
    private void createWorld() {
        worldLayoutStrategy.createWorldLayout(this);
    }

    public Tile getTileAt(Position position) {
        return gameWorld.getTile(position);
    }

    public AbstractUnit getUnitAt(Position position) {
        return gameWorld.getUnit(position);
    }

    public CityImpl getCityAt(Position position) {
        return gameWorld.getCity(position);
    }

    /**
     * Gets the GameWorld in this BaseGame. The GameWorld is the internal representation of the world.
     *
     * @return The GameWorld
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public Player getPlayerInTurn() {
        return playerTurn;
    }

    public Player getWinner() {
        return this.getWinner.getWinner(this);
    }

    public int getAge() {
        return this.age;
    }

    public boolean moveUnit(Position from, Position to) {
        AbstractUnit unit = getUnitAt(from);
        if (unit == null) {
            throw new IllegalArgumentException("There has to be a unit at the specified from position: " + from);
        }

        // Can only move my own unit
        if (unit.getOwner() != playerTurn) {
            return false;
        }

        AbstractUnit unitAtTarget = getUnitAt(to);
        Tile targetTile = getTileAt(to);

        // Tests if we try to move to far.
        int distance = Position.getDistance(from, to);
        if (distance > unit.getMoveCount() || movedUnits.contains(unit)) {
            return false;
        }

        // See if we try to move to an type of tile we cannot move to.
        if (targetTile.getTypeString().equals(GameConstants.MOUNTAINS) || targetTile.getTypeString().equals(GameConstants.OCEANS)) {
            return false;
        }

        //If there is a unit at the target, if it is an enemy, attack it, if it is the players unit, reject move.
        if (unitAtTarget != null) {
            if (unitAtTarget.getOwner() != unit.getOwner()) {
                if (attackResolver.doesAttackerWin(this, unit, unitAtTarget)) {
                    gameWorld.removeUnit(to);
                    Integer attacksWon = attackWonCounter.get(unit.getOwner());
                    if (attacksWon == null) {
                        attackWonCounter.put(unit.getOwner(), 1);
                    } else {
                        attackWonCounter.put(unit.getOwner(), attacksWon + 1);
                    }
                } else {
                    gameWorld.removeUnit(from);
                    return true; // move was valid.
                }
            } else /* if (unitAtTarget.getOwner() == unit.getOwner())*/ {
                return false;
            }
        }

        // TODO: is taking a city an attack?
        if (getCityAt(to) != null && getCityAt(to).getOwner() != playerTurn) {
            getCityAt(to).setOwner(playerTurn);
        }

        movedUnits.add(unit);

        gameWorld.removeUnit(from);
        gameWorld.placeUnit(to, unit);
        return true;

    }

    public int getAttacksWon(Player player) {
        Integer attacksWon = attackWonCounter.get(player);
        if (attacksWon == null) {
            return 0;
        }
        return attacksWon;
    }

    public void endOfTurn() {
        // Restore unit moved.
        movedUnits.clear();

        // Change player in turn.
        switch (playerTurn) {
            case RED:
                playerTurn = Player.BLUE;
                break;
            case BLUE:
                playerTurn = Player.RED;
                endOfRound();
                break;
            default:
                throw new RuntimeException("Unrecognized player: " + playerTurn);
        }
    }

    /**
     * Ends the round, with everything that includes.
     * This produces unit and increases the age.
     */
    private void endOfRound() {
        // Aging the world.
        this.age = newAgeCalculator.getNewAge(this);

        roundCount++;

        // Making the Cities produce something and make the units they can.
        for (Map.Entry<Position, CityImpl> cityEntry : gameWorld.getCityEntrySet()) {
            CityImpl city = cityEntry.getValue();
            Position cityPosition = cityEntry.getKey();

            city.increaseProductionAmount(6);
            int productionAmount = city.getProductionAmount();
            String produces = city.getProduction();

            AbstractUnit unit = null;

            if (produces.equals(GameConstants.SETTLER)) {
                if (productionAmount >= GameConstants.SETTLER_PRICE) {
                    city.decreaseProductionAmount(GameConstants.SETTLER_PRICE);
                    unit = unitFactory.makeUnit(this, GameConstants.SETTLER, city.getOwner());
                }
            } else if (produces.equals(GameConstants.ARCHER)) {
                if (productionAmount >= GameConstants.ARCHER_PRICE) {
                    city.decreaseProductionAmount(GameConstants.ARCHER_PRICE);
                    unit = unitFactory.makeUnit(this, GameConstants.ARCHER, city.getOwner());
                }
            } else if (produces.equals(GameConstants.LEGION)) {
                if (productionAmount >= GameConstants.LEGION_PRICE) {
                    city.decreaseProductionAmount(GameConstants.LEGION_PRICE);
                    unit = unitFactory.makeUnit(this, GameConstants.LEGION, city.getOwner());
                }
            }
            if (unit != null) {
                gameWorld.placeUnitNear(cityPosition, unit);
            }
        }
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        throw new UnsupportedOperationException();
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        City city = gameWorld.getCity(p);
        if (city == null) {
            throw new IllegalArgumentException("Called changeProduction on a position with no city: " + p);
        }
        city.setProduction(unitType);
    }

    public void performUnitActionAt(Position p) {
        // First see if its the right players turn.
        AbstractUnit unit = getUnitAt(p);
        if (unit != null && unit.getOwner() != getPlayerInTurn()) {
            return;
        }
        unit.performAction();
    }

    public UnitFactory getUnitFactory() {
        return unitFactory;
    }

    public void resetAttacksWon() {
        this.attackWonCounter.clear();
    }

    public int getRoundCount() {
        return roundCount;
    }

    /**
     * This class holds all the default strategies for the game.
     * When using the GameBuilder to make a game, these are inserted as defaults when no other strategy is specified.
     */
    public static class DefaultStrategies {
        /**
         * Gets an instance of the default strategy to calculate the new age of the game after each round.
         *
         * @return the default NewAgeCalculator
         */
        public static NewAgeCalculator getNewAgeCalculator() {
            return new NewAgeCalculator() {
                public int getNewAge(BaseGame game) {
                    return game.getAge() + 100;
                }
            };
        }

        /**
         * Gets an instance of the default strategy to calculate the winner of the game.
         *
         * @return The default GetWinner
         */
        public static GetWinner getWinner() {
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
         * Gets an instance of the default strategy for making units.
         *
         * @return the default UnitFactory.
         */
        public static UnitFactory getUnitFactory() {
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
        public static WorldLayoutStrategy getWorldLayoutStrategy() {
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

                    UnitFactory unitFactory = game.getUnitFactory();

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
         * TODO Doc.
         *
         * @return
         */
        public static AttackResolver getAttackResolver() {
            return new AttackResolver() {
                @Override
                public boolean doesAttackerWin(BaseGame game, AbstractUnit attacker, AbstractUnit defender) {
                    return true;
                }
            };
        }
    }
}
