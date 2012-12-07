package hotciv.view.tools;

import hotciv.common.BaseGame;
import hotciv.framework.Position;
import hotciv.view.figures.UnitFigure;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;
import minidraw.framework.Tool;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 07-12-12, 23:19
 */
public class UnitActionTool extends ClickingTool {
    private final BaseGame game;
    private final Drawing model;

    public UnitActionTool(BaseGame game, Drawing model) {
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
                int posX = GfxConstants.getTileXFromPixel(x);
                int posY = GfxConstants.getTileXFromPixel(y);
                game.performUnitActionAt(new Position(posY, posX));
            }
        }
    }
}
