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
    private Printer printer;

    public GameLogger(Game game) {
        this(game, new Printer() {
            @Override
            public void print(String str) {
                System.out.println(str);
            }
        });
    }

    public GameLogger(Game game, Printer printer) {
        this.game = game;
        this.printer = printer;
    }

    public interface Printer {
        public void print(String str);
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
        printer.print(getPlayerInTurn() + " moves " + unitType + " from " + from + " to " + to + ".");
        return game.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        printer.print(getPlayerInTurn() + " ends turn.");
        game.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        printer.print(getPlayerInTurn() + " changes work force focus in city at " + p + " to " + balance);
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        printer.print(getPlayerInTurn() + " changes production in city at " + p + " to " + unitType);
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        printer.print(getPlayerInTurn() + " performed unit action on unit at " + p);
        game.performUnitActionAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {
        game.addObserver(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        printer.print(getPlayerInTurn() + " set the tile focus to " + position);
        game.setTileFocus(position);
    }

    @Override
    public Position getTileFocus() {
        return game.getTileFocus();
    }
}
