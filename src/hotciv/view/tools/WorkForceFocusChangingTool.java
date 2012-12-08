package hotciv.view.tools;

import hotciv.common.BaseGame;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.figures.WorkForceFocusFigure;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 00:17
 */
public class WorkForceFocusChangingTool extends ClickingTool {
    private final BaseGame game;
    private final Drawing drawing;

    public WorkForceFocusChangingTool(BaseGame game, Drawing drawing) {
        this.game = game;
        this.drawing = drawing;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        Figure figure = drawing.findFigure(x, y);
        if (figure instanceof WorkForceFocusFigure) {
            Position selected = game.getTileFocus();
            City city = game.getCityAt(selected);
            if (GameConstants.foodFocus.equals(city.getWorkforceFocus())) {
                game.changeWorkForceFocusInCityAt(selected, GameConstants.productionFocus);
            } else if (GameConstants.productionFocus.equals(city.getWorkforceFocus())) {
                game.changeWorkForceFocusInCityAt(selected, GameConstants.foodFocus);
            } else {
                throw new RuntimeException("Unrecognized workforceFocus in city at: " + selected);
            }
        }
    }
}
