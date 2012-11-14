package hotciv.common;

import hotciv.common.strategy.NewAgeCalculator;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.strategy.GetWinner;
import hotciv.common.strategy.UnitAction;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.common.units.Archer;
import hotciv.common.units.Legion;
import hotciv.common.units.Settler;
import hotciv.framework.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a game instance, that does the most basic behaviour, and has a big constructor that specifies all the strategies this game uses.
 * @author : Erik
 * Date: 09-11-12, 11:22
 */
public class BaseGame implements Game {
    private GameWorld<UnitImpl, CityImpl> gameWorld = new GameWorld<UnitImpl, CityImpl>();

    private Player playerTurn;

    private Set<Unit> movedUnits = new HashSet<Unit>();

    private int age = -4000;

    // Strategies
    private GetWinner getWinner;
    private NewAgeCalculator newAgeCalculator;
    private UnitAction unitAction;
    private UnitFactory unitFactory;
    private WorldLayoutStrategy worldLayoutStrategy;

    /**
     * The default and only constructor for BaseGame.
     * The constructor is protected since only the GameBuilder is supposed to call it.
     * All the parameters are the different strategies used by this BaseGame.
     */
    protected BaseGame(GetWinner getWinner, NewAgeCalculator newAgeCalculator, UnitAction unitAction, UnitFactory unitFactory, WorldLayoutStrategy worldLayoutStrategy) {
        // First strategies
        this.getWinner = getWinner;
        this.newAgeCalculator = newAgeCalculator;
        this.unitAction = unitAction;
        this.unitFactory = unitFactory;
        this.worldLayoutStrategy = worldLayoutStrategy;

        // Player starts
        playerTurn = Player.RED;

        createWorld();
    }
    private void createWorld() {
        worldLayoutStrategy.createWorldLayout(this);
    }
    public Tile getTileAt(Position position) {
        return gameWorld.getTile(position);
    }

    public UnitImpl getUnitAt(Position position) {
        return gameWorld.getUnit(position);
    }

    public CityImpl getCityAt(Position position) {
        return gameWorld.getCity(position);
    }

    public GameWorld<UnitImpl, CityImpl> getGameWorld() {
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
        UnitImpl unit = getUnitAt(from);

        // Can only move my own unit
        if (unit.getOwner() != playerTurn) {
            return false;
        }

        UnitImpl unitAtTarget = getUnitAt(to);
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
        if (unitAtTarget != null ) {
            if (unitAtTarget.getOwner() != unit.getOwner()) {
                // Attacking unit always win in AlphaCiv.
                gameWorld.removeUnit(to);
            }
            else /* if (unitAtTarget.getOwner() == unit.getOwner())*/ {
                return false;
            }
        }

        if (getCityAt(to) != null && getCityAt(to).getOwner() != playerTurn ) {
            getCityAt(to).setOwner(playerTurn);
        }

        movedUnits.add(unit);

        // Moves the unit.
        gameWorld.removeUnit(from);
        gameWorld.placeUnit(to, unit);
        return true;

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
    private void endOfRound() {
        // Aging the world.
        this.age = newAgeCalculator.getNewAge(this);

        // Making the Cities produce something and make the units they can.
        for (Map.Entry<Position, CityImpl> cityEntry : gameWorld.getCityEntrySet()) {
            CityImpl city = cityEntry.getValue();
            Position cityPosition = cityEntry.getKey();

            city.increaseProductionAmount(6);
            int productionAmount = city.getProductionAmount();
            String produces = city.getProduction();

            UnitImpl unit = null;

            if (produces.equals(GameConstants.SETTLER)) {
                if (productionAmount >= 30) {
                    city.decreaseProductionAmount(30);
                    unit = unitFactory.makeUnit(this, GameConstants.SETTLER, city.getOwner());
                }
            }
            else if (produces.equals(GameConstants.ARCHER)) {
                if (productionAmount >= 10) {
                    city.decreaseProductionAmount(10);
                    unit = unitFactory.makeUnit(this, GameConstants.ARCHER, city.getOwner());
                }
            }
            else if (produces.equals(GameConstants.LEGION)) {
                if (productionAmount >= 15) {
                    city.decreaseProductionAmount(15);
                    unit = unitFactory.makeUnit(this, GameConstants.LEGION, city.getOwner());
                }
            }
            if (unit != null) {
                gameWorld.placeUnitNear(cityPosition, unit);
            }
        }
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        City city = gameWorld.getCity(p);
        city.setProduction(unitType);
    }

    public void performUnitActionAt(Position p) {
        // First see if its the right players turn.
        UnitImpl unit = getUnitAt(p);
        if (unit != null && unit.getOwner() != getPlayerInTurn()){
            return;
        }
        unitAction.performAction(this, p);
    }

    public UnitFactory getUnitFactory() {
        return unitFactory;
    }

    // Holds the default strategies for the game.
    public static class DefaultStrategies {
        public static NewAgeCalculator getNewAgeCalculator() {
            return new NewAgeCalculator() {
                public int getNewAge(BaseGame game) {
                    return game.getAge() + 100;
                }
            };
        }
        public static GetWinner getWinner() {
            return new GetWinner() {
                public Player getWinner(BaseGame game) {
                    if (game.getAge() >= -3000) {
                        return Player.RED;
                    }
                    else {
                        return null;
                    }
                }
            };
        }
        public static UnitAction getUnitAction() {
            return new UnitAction() {
                public void performAction(BaseGame game, Position position) {
                    // Empty pr. design.
                }
            };
        }

        public static UnitFactory getUnitFactory() {
            return new UnitFactory() {
                public UnitImpl makeUnit(BaseGame game, String typeString, Player owner) {
                    if (GameConstants.ARCHER.equals(typeString)) {
                        return new Archer(owner);
                    }
                    else if (GameConstants.SETTLER.equals(typeString)) {
                        return new Settler(owner);
                    }
                    else if (GameConstants.LEGION.equals(typeString)) {
                        return new Legion(owner);
                    }
                    else {
                        throw new RuntimeException("Unrecognized unit type: " + typeString);
                    }
                }
            };
        }
        
        public static WorldLayoutStrategy getWorldLayoutStrategy() {
        	return new WorldLayoutStrategy() {
        		public void createWorldLayout(BaseGame game) {
                    GameWorld<UnitImpl, CityImpl> gameWorld = game.getGameWorld();
                    String[] worldLayout = new String[] {
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
                    gameWorld.placeUnit(new Position(2, 0), unitFactory.makeUnit(game, GameConstants.ARCHER, Player.RED));
                    // Red has a settler at (4,3)
                    gameWorld.placeUnit(new Position(4, 3), unitFactory.makeUnit(game, GameConstants.SETTLER, Player.RED));
                    // Blue has a legion at (3,2)
                    gameWorld.placeUnit(new Position(3, 2), unitFactory.makeUnit(game, GameConstants.LEGION, Player.BLUE));
        		}
        	};
        }
    }
}
