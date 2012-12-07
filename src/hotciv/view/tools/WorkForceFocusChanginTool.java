package hotciv.view.tools;

import hotciv.common.BaseGame;
import minidraw.framework.Drawing;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 00:17
 */
public class WorkForceFocusChanginTool extends ClickingTool {
    private final BaseGame game;
    private final Drawing drawing;

    public WorkForceFocusChanginTool(BaseGame game, Drawing drawing) {
        this.game = game;
        this.drawing = drawing;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
