package hotciv.standard.view.tools;

import hotciv.framework.Game;
import hotciv.variants.SemiCiv;
import hotciv.view.HotCivFactory;
import hotciv.view.tools.UnitMovingTool;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 14-12-12, 14:16
 */
public class TestUnitMovingTool {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new TestUnitMovingTool();
    }

    public TestUnitMovingTool() {
        Game game = new SemiCiv().newGame();
        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();

        Drawing drawing = editor.drawing();
        editor.setTool(new UnitMovingTool(game, drawing));
    }
}
