package hotciv.standard.view.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.variants.SemiCiv;
import hotciv.view.HotCivFactory;
import hotciv.view.tools.ProductionChangingTool;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 14-12-12, 14:07
 */
public class TestProductionChangingTool {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new TestProductionChangingTool();
    }

    public TestProductionChangingTool() {
        Game game = new SemiCiv().newGame();
        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();

        Drawing drawing = editor.drawing();
        editor.setTool(new ProductionChangingTool(game, drawing));

        game.setTileFocus(new Position(8, 12));
    }
}
