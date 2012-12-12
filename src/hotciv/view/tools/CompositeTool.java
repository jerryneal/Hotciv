package hotciv.view.tools;

import minidraw.framework.Tool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 00:08
 */
public class CompositeTool implements Tool {
    private Set<Tool> tools = new HashSet<Tool>();

    @Override
    public void mouseDown(MouseEvent mouseEvent, int x, int y) {
        for (Tool tool : tools) {
            tool.mouseDown(mouseEvent, x, y);
        }
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent, int x, int y) {
        for (Tool tool : tools) {
            tool.mouseDrag(mouseEvent, x, y);
        }
    }

    @Override
    public void mouseUp(MouseEvent mouseEvent, int x, int y) {
        for (Tool tool : tools) {
            tool.mouseUp(mouseEvent, x, y);
        }
    }

    @Override
    public void mouseMove(MouseEvent mouseEvent, int x, int y) {
        for (Tool tool : tools) {
            tool.mouseMove(mouseEvent, x, y);
        }

    }

    @Override
    public void keyDown(KeyEvent keyEvent, int i) {
        for (Tool tool : tools) {
            tool.keyDown(keyEvent, i);
        }
    }

    public void addTool(Tool tool) {
        this.tools.add(tool);
    }
}
