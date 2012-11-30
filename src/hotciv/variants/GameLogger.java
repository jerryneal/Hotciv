package hotciv.variants;

import hotciv.framework.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 30-11-12, 09:24
 */
public class GameLogger implements Game {
    private Game game;

    public GameLogger(Game game) {
        this.game = game;
    }

    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        String unitType = "unit";
        Unit unit;
        if ((unit = game.getUnitAt(from)) != null) {
            unitType = unit.getTypeString();
        }
        System.out.println(getPlayerInTurn() + " moves " + unitType + " from " + from + " to " + to + ".");
        return game.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println(getPlayerInTurn() + " ends turn.");
        game.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        System.out.println(getPlayerInTurn() + " changes work force focus in city at " + p + " to " + balance);
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println(getPlayerInTurn() + " changes production in city at " + p + " to " + unitType);
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println(getPlayerInTurn() + " performed unit action un unit at " + p);
        game.performUnitActionAt(p);
    }
}
