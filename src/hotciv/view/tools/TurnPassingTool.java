package hotciv.view.tools;

import hotciv.common.BaseGame;
import hotciv.view.figures.ShieldFigure;
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
    private final BaseGame game;
    private final Drawing model;

    public TurnPassingTool(BaseGame game, Drawing model) {
        this.game = game;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        Figure figure = model.findFigure(x, y);
        if (figure instanceof ShieldFigure) {
            game.endOfTurn();
        }
    }
}
