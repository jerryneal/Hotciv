package hotciv.view.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.figures.UnitFigure;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 10:23
 */
public class UnitMovingTool extends NullTool {
    private Game game;
    private Drawing model;

    private Figure draggingFigure = null;
    private int initialDraggingX = 0;
    private int initialDraggingY = 0;
    private int draggingY = 0;
    private int draggingX = 0;
    private Position moveFrom;
    private boolean dragged = false;

    public UnitMovingTool(Game game, Drawing model) {
        this.game = game;
        this.model = model;
    }

    @Override
    public void mouseDown(MouseEvent mouseEvent, int x, int y) {
        dragged = false;
        Figure figure = model.findFigure(x, y);
        if (figure instanceof UnitFigure) {
            this.draggingFigure = figure;
            this.draggingX = x;
            this.draggingY = y;
            this.initialDraggingX = x;
            this.initialDraggingY = y;
            this.moveFrom = new Position(GfxConstants.getTileYFromPixel(y), GfxConstants.getTileXFromPixel(x));
        }
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent, int x, int y) {
        dragged = true;
        if (this.draggingFigure != null) {
            int deltaX = x - draggingX;
            draggingX = x;
            int deltaY = y - draggingY;
            draggingY = y;
            draggingFigure.moveBy(deltaX, deltaY);
        }
    }

    int count = 0;

    @Override
    public void mouseUp(MouseEvent mouseEvent, int x, int y) {
        if (draggingFigure != null && dragged) {
            draggingFigure.moveBy(initialDraggingX - x, initialDraggingY - y);
            draggingFigure = null;
            draggingX = 0;
            draggingY = 0;

            Position moveTo = new Position(GfxConstants.getTileYFromPixel(y), GfxConstants.getTileXFromPixel(x));
            game.moveUnit(moveFrom, moveTo);
        }
    }
}
