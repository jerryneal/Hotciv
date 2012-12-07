package hotciv.view.tools;

import minidraw.framework.Tool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 07-12-12, 23:13
 */
public abstract class ClickingTool implements Tool {
    private boolean dragged = false;

    @Override
    public void mouseDown(MouseEvent mouseEvent, int x, int y) {
        dragged = false;
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent, int x, int y) {
        dragged = true;
    }

    @Override
    public void mouseUp(MouseEvent mouseEvent, int x, int y) {
        if (!dragged) {
            mouseClicked(mouseEvent, x, y);
        }
    }

    /**
     * This method is called when the user only clicks, and does not drag anything.
     * Its called on mouseUp.
     *
     * @param mouseEvent The mouseEvent
     * @param x          The X coordinate of the click.
     * @param y          The Y coordinate of the click.
     */
    public abstract void mouseClicked(MouseEvent mouseEvent, int x, int y);

    @Override
    public void mouseMove(MouseEvent mouseEvent, int i, int i2) {
        // Nothing
    }

    @Override
    public void keyDown(KeyEvent keyEvent, int i) {
        // Nothing
    }
}
