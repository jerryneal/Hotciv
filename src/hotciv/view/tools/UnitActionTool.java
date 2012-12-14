package hotciv.view.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.figures.UnitFigure;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 07-12-12, 23:19
 */
public class UnitActionTool extends ClickingTool {
    private final Game game;
    private final Drawing model;

    public UnitActionTool(Game game, Drawing model) {
        this.game = game;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        // Equals button1 and shift.
        if (mouseEvent.isShiftDown()) {
            Figure figure = model.findFigure(x, y);
            if (figure instanceof UnitFigure) {
                UnitFigure unitFigure = (UnitFigure) figure;
                Position position = GfxConstants.getPositionFromXY(x, y);
                game.performUnitActionAt(position);
            }
        }
    }
}
