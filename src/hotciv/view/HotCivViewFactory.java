package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.Figures.CityFigure;
import hotciv.view.Figures.UnitFigure;
import hotciv.view.FrameWork.GfxConstants;
import minidraw.framework.*;
import minidraw.standard.StandardDrawing;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 10:20
 */
public class HotCivViewFactory implements Factory {
    private BaseGame game;
    public HotCivViewFactory(BaseGame g) { game = g; }

    public DrawingView createDrawingView( DrawingEditor editor ) {
        // Create view in MVC
        DrawingView view = new MapView(editor, game);
        return view;
    }

    public Drawing createDrawing( DrawingEditor editor ) {
        // Create the model in MVC
        StandardDrawing drawing = new StandardDrawing();
        initializeDrawing(drawing);
        return drawing;
    }

    public JTextField createStatusField( DrawingEditor editor ) {
        // Create status field.
        return new JTextField("Dette er en tekst i statusfelt.");
    }

    private void initializeDrawing(Drawing drawing) {
        // Placing all cities in the GameWorld.
        for (Map.Entry<Position, CityImpl> entry : game.getGameWorld().getCityEntrySet()) {
            City city = entry.getValue();
            Position position = entry.getKey();
            int column = position.getColumn();
            int row = position.getRow();
            drawing.add(new CityFigure(city, new Point( GfxConstants.getXFromColumn(column),
                    GfxConstants.getYFromRow(row) )));
        }

        // Placing all units
        for (Map.Entry<Position, Unit> entry : game.getGameWorld().getUnitsEntrySet()) {
            Unit unit = entry.getValue();
            Position position = entry.getKey();
            int column = position.getColumn();
            int row = position.getRow();
            drawing.add(new UnitFigure(unit, new Point( GfxConstants.getXFromColumn(column),
                    GfxConstants.getYFromRow(row) )));
        }

    }
}
