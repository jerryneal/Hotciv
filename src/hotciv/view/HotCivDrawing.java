package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.framework.*;
import hotciv.view.figures.CityFigure;
import hotciv.view.figures.ShieldFigure;
import hotciv.view.figures.TextFigure;
import hotciv.view.figures.UnitFigure;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Figure;
import minidraw.standard.StandardDrawing;

import java.awt.*;
import java.util.Map;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 11:49
 */
public class HotCivDrawing extends StandardDrawing implements GameObserver {
    private BaseGame game;
    private TextFigure ageTextField;
    private final ShieldFigure turnShield;

    public HotCivDrawing(BaseGame game) {
        this.game = game;
        game.addObserver(this);

        // Placing all cities in the GameWorld.
        for (Map.Entry<Position, CityImpl> entry : game.getGameWorld().getCityEntrySet()) {
            City city = entry.getValue();
            Position position = entry.getKey();
            int column = position.getColumn();
            int row = position.getRow();
            this.add(new CityFigure(city,
                    new Point(GfxConstants.getXFromColumn(column),
                            GfxConstants.getYFromRow(row))));
        }

        // Placing all units
        for (Map.Entry<Position, Unit> entry : game.getGameWorld().getUnitsEntrySet()) {
            Unit unit = entry.getValue();
            Position position = entry.getKey();
            int column = position.getColumn();
            int row = position.getRow();
            this.add(new UnitFigure(unit,
                    new Point(GfxConstants.getXFromColumn(column),
                            GfxConstants.getYFromRow(row))));
        }

        ageTextField = new TextFigure("4000 BC",
                new Point(GfxConstants.AGE_TEXT_X,
                        GfxConstants.AGE_TEXT_Y));
        add(ageTextField);

        turnShield = new ShieldFigure();
        add(turnShield);
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
                Position cityPosition = game.getGameWorld().getCityPosition(cityFigure.getCity());
                // Cities can't move, and sometimes we hit the city when we didn't mean to.
                if (position.equals(cityPosition)) {
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
