package hotciv.standard.view.tools;

import hotciv.framework.Game;
import hotciv.variants.GameLogger;
import hotciv.variants.SemiCiv;
import hotciv.view.HotCivFactory;
import hotciv.view.tools.TileSelectionTool;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 14-12-12, 14:11
 */
public class TestTileSelectionTool {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new TestTileSelectionTool();
    }

    public TestTileSelectionTool() {
        Game game = new SemiCiv().newGame();
        game = new GameLogger(game, new GameLogger.Printer() {
            @Override
            public void print(String str) {
                if (editor != null) {
                    editor.showStatus(str);
                }
            }
        });
        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();

        Drawing drawing = editor.drawing();
        editor.setTool(new TileSelectionTool(game));
    }
}
