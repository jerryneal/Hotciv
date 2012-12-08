package hotciv.view;

import hotciv.framework.*;
import hotciv.view.figures.*;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Figure;
import minidraw.standard.StandardDrawing;

import java.awt.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 11:49
 */
public class HotCivDrawing extends StandardDrawing implements GameObserver {
    private Game game;
    private TextFigure ageTextField;
    private ShieldFigure turnShield;
    private UnitShieldFigure unitShield;

    public HotCivDrawing(Game game) {
        this.game = game;
        game.addObserver(this);

        // Placing all cities and units in the GameWorld.
        for (int x = 0; x < GameConstants.WORLDSIZE; x++) {
            for (int y = 0; y < GameConstants.WORLDSIZE; y++) {
                Position position = new Position(y, x);
                City city = game.getCityAt(position);
                if (city != null) {
                    this.add(new CityFigure(city,
                            new Point(GfxConstants.getXFromColumn(x),
                                    GfxConstants.getYFromRow(y))));
                }

                Unit unit = game.getUnitAt(position);
                if (unit != null) {
                    this.add(new UnitFigure(unit,
                            new Point(GfxConstants.getXFromColumn(x),
                                    GfxConstants.getYFromRow(y))));
                }
            }
        }

        ageTextField = new TextFigure("4000 BC",
                new Point(GfxConstants.AGE_TEXT_X,
                        GfxConstants.AGE_TEXT_Y));
        add(ageTextField);

        turnShield = new ShieldFigure();
        add(turnShield);

        unitShield = new UnitShieldFigure();
        add(unitShield);

    }

    @Override
    public void worldChangedAt(Position position) {
        int column = GfxConstants.getXFromColumn(position.getColumn());
        int row = GfxConstants.getYFromRow(position.getRow());

        // TODO: This is ugly.
        // Updating the city.
        City city = game.getCityAt(position);
        Figure figure;
        if ((figure = this.findFigure(column, row)) != null) {
            if (figure instanceof CityFigure) {
                CityFigure cityFigure = (CityFigure) figure;
                // Cities can't move, and sometimes we hit the city when we didn't mean to.
                if (game.getCityAt(position) != null) {
                    this.remove(figure);
                }
            }
        }
        if (city != null) {
            this.add(new CityFigure(city, new Point(column, row)));
        }

        // Updating the unit.
        Unit unit = game.getUnitAt(position);
        figure = null;
        if ((figure = this.findFigure(column, row)) != null) {
            if (figure instanceof UnitFigure) {
                this.remove(figure);
            }
        }
        if (unit != null) {
            this.add(new UnitFigure(unit, new Point(column, row)));
        }

        requestUpdate();
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        ageTextField.setText(Integer.toString(Math.abs(age)) + ((age > 0) ? " AC" : " BC"));
        turnShield.setPlayerInTurn(nextPlayer);
        requestUpdate();
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        // TODO
    }


}
