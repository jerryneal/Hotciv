package hotciv.common;

import hotciv.common.observers.EndOfRoundObserver;
import hotciv.common.observers.WinnerObserver;
import hotciv.common.strategy.*;
import hotciv.framework.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a game instance, that does the most basic behaviour, and has a big constructor that specifies all the strategies this game uses.
 *
 * @author : Erik
 *         Created: 09-11-12, 11:22
 */
public class BaseGame implements Game {
    private GameWorld gameWorld;

    private Player playerTurn;

    private Set<AbstractUnit> movedUnits = new HashSet<AbstractUnit>();

    private int age = -4000;

    // Strategies
    private GetWinner getWinner;
    private NewAgeCalculator newAgeCalculator;
    private UnitFactory unitFactory;
    private WorldLayoutStrategy worldLayoutStrategy;
    private AttackResolver attackResolver;

    private Set<WinnerObserver> winnerObservers;
    private Set<EndOfRoundObserver> endOfRoundObservers;
    private Set<GameObserver> gameObservers;
    private Position tileFocus;

    public BaseGame(GameStrategyFactory factory) {
        // Making the observer lists.
        this.winnerObservers = new HashSet<WinnerObserver>();
        this.endOfRoundObservers = new HashSet<EndOfRoundObserver>();
        this.gameObservers = new HashSet<GameObserver>();


        // First strategies
        this.getWinner = factory.createWinnerStrategy(this);
        this.newAgeCalculator = factory.createNewAgeCalculatorStrategy(this);
        this.unitFactory = factory.createUnitFactoryStrategy(this);
        this.worldLayoutStrategy = factory.createWorldLayoutStrategy(this);
        this.attackResolver = factory.createAttackResolverStrategy(this);

        // Player starts
        playerTurn = Player.RED;

        // Gameworld
        this.gameWorld = new GameWorld(unitFactory, gameObservers);
        createWorld();
    }

    /**
     * Creates the world, based on the worldLayoutStrategy.
     */
    private void createWorld() {
        worldLayoutStrategy.createWorldLayout();
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
        return this.getWinner.getWinner();
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

        Unit unitAtTarget = getUnitAt(to);
        Tile targetTile = getTileAt(to);

        // Tests if we try to move to far.
        int distance = Position.getDistance(from, to);
        if (distance > unit.getMoveCount()) {
            return false;
        }

        // See if we try to move to an type of tile we cannot move to.
        if (targetTile.getTypeString().equals(GameConstants.MOUNTAINS) || targetTile.getTypeString().equals(GameConstants.OCEANS)) {
            return false;
        }

        //If there is a unit at the target, if it is an enemy, attack it, if it is the players unit, reject move.
        if (unitAtTarget != null) {
            if (unitAtTarget.getOwner() != unit.getOwner()) {
                if (attackResolver.doesAttackerWin(unit, unitAtTarget)) {
                    gameWorld.removeUnit(to);
                    Player winner = unit.getOwner();
                    for (WinnerObserver winnerObserver : winnerObservers) {
                        winnerObserver.playerWonBattle(winner);
                    }
                } else {
                    gameWorld.removeUnit(from);
                    return true; // move was valid.
                }
            } else /* if (unitAtTarget.getOwner() == unit.getOwner())*/ {
                return false;
            }
        }

        if (getCityAt(to) != null && getCityAt(to).getOwner() != playerTurn) {
            getCityAt(to).setOwner(playerTurn);
        }

        movedUnits.add(unit);
        unit.decreaseMoveCount(distance);

        gameWorld.removeUnit(from);
        gameWorld.placeUnit(to, unit);
        return true;

    }

    public void endOfTurn() {
        // Restore the move count of moved units.
        for (AbstractUnit movedUnit : movedUnits) {
            movedUnit.resetMoveCount();
        }

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
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.turnEnds(playerTurn, getAge());
        }
    }

    /**
     * Ends the round, with everything that includes.
     * This produces unit and increases the age.
     */
    private void endOfRound() {
        // Aging the world.
        this.age = newAgeCalculator.getNewAge();

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
                    unit = unitFactory.makeUnit(GameConstants.SETTLER, city.getOwner());
                }
            } else if (produces.equals(GameConstants.ARCHER)) {
                if (productionAmount >= GameConstants.ARCHER_PRICE) {
                    city.decreaseProductionAmount(GameConstants.ARCHER_PRICE);
                    unit = unitFactory.makeUnit(GameConstants.ARCHER, city.getOwner());
                }
            } else if (produces.equals(GameConstants.LEGION)) {
                if (productionAmount >= GameConstants.LEGION_PRICE) {
                    city.decreaseProductionAmount(GameConstants.LEGION_PRICE);
                    unit = unitFactory.makeUnit(GameConstants.LEGION, city.getOwner());
                }
            }
            if (unit != null) {
                gameWorld.placeUnitNear(cityPosition, unit);
            }
        }

        for (EndOfRoundObserver endOfRoundObserver : endOfRoundObservers) {
            endOfRoundObserver.endOfRound();
        }

    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        throw new UnsupportedOperationException();
    }

    public void changeProductionInCityAt(Position position, String unitType) {
        City city = gameWorld.getCity(position);
        if (city == null) {
            throw new IllegalArgumentException("Called changeProduction on a position with no city: " + position);
        }
        city.setProduction(unitType);

        // Calling the observers
        callWorldChangedAddObserver(position);
    }

    public void performUnitActionAt(Position position) {
        // First see if its the right players turn.
        Unit unit = getUnitAt(position);
        if (unit == null) {
            throw new IllegalArgumentException("There has to be a unit at the specified from position: " + position);
        }
        if (unit.getOwner() != getPlayerInTurn()) {
            return;
        }
        unit.performAction();

        // Calling the observers
        callWorldChangedAddObserver(position);
    }

    @Override
    public void addObserver(GameObserver observer) {
        this.gameObservers.add(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        this.tileFocus = position;
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.tileFocusChangedAt(position);
        }
    }

    @Override
    public Position getTileFocus() {
        return this.tileFocus;
    }

    private void callWorldChangedAddObserver(Position position) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.worldChangedAt(position);
        }
    }


    /**
     * Adds the specified WinnerObserver to this BaseGame.
     *
     * @param winnerObserver The WinnerObserver.
     */
    public void addWinnerObserver(WinnerObserver winnerObserver) {
        this.winnerObservers.add(winnerObserver);
    }

    /**
     * Adds the specified EndOfRoundObserver to this BaseGame.
     *
     * @param endOfRoundObserver The EndOfRoundObserver.
     */
    public void addEndOfRoundObserver(EndOfRoundObserver endOfRoundObserver) {
        this.endOfRoundObservers.add(endOfRoundObserver);
    }
}
