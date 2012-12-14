package hotciv.standard.view.tools;

import hotciv.framework.Game;
import hotciv.variants.SemiCiv;
import hotciv.view.HotCivFactory;
import hotciv.view.tools.UnitActionTool;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 14-12-12, 14:14
 */
public class TestUnitActionTool {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new TestUnitActionTool();
    }

    public TestUnitActionTool() {
        Game game = new SemiCiv().newGame();
        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();

        Drawing drawing = editor.drawing();
        editor.setTool(new UnitActionTool(game, drawing));
    }
}
