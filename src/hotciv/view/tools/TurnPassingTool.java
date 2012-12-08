package hotciv.view.tools;

import hotciv.framework.Game;
import hotciv.view.figures.ShieldFigure;
import hotciv.view.figures.TurnShield;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 07-12-12, 23:00
 */
public class TurnPassingTool extends ClickingTool {
    private final Game game;
    private final Drawing model;

    public TurnPassingTool(Game game, Drawing model) {
        this.game = game;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        Figure figure = model.findFigure(x, y);
        if (figure instanceof TurnShield) {
            game.endOfTurn();
        }
    }
}
