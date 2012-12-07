package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.framework.*;
import hotciv.view.Figures.CityFigure;
import hotciv.view.Figures.UnitFigure;
import hotciv.view.FrameWork.GfxConstants;
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
    }

    @Override
    public void worldChangedAt(Position position) {
        // TODO: This is ugly.
        // Updating the unit.
        Unit unit = game.getUnitAt(position);
        int column = GfxConstants.getXFromColumn(position.getColumn());
        int row = GfxConstants.getYFromRow(position.getRow());
        Figure unitFigure;
        if ((unitFigure = this.findFigure(column, row)) != null) {
            if (unitFigure instanceof UnitFigure) {
                this.remove(unitFigure);
            }
        }
        if (unit != null) {
            this.add(new UnitFigure(unit, new Point(column, row)));
        }

        // Updating the unit.
        City city = game.getCityAt(position);
        column = position.getColumn();
        row = position.getRow();
        Figure cityFigure;
        if ((cityFigure = this.findFigure(column, row)) != null) {
            if (cityFigure instanceof CityFigure) {
                this.remove(cityFigure);
            }
        }
        if (city != null) {
            this.add(new UnitFigure(unit, new Point(column, row)));
        }

        this.requestUpdate();
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        // TODO
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        // TODO
    }
}
