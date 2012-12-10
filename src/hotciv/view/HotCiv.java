package hotciv.view;

import hotciv.framework.Game;
import hotciv.variants.GameLogger;
import hotciv.variants.SemiCiv;
import hotciv.view.tools.*;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCiv {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new HotCiv();
    }

    public HotCiv() {
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

        CompositeTool compositeTool = new CompositeTool();

        Drawing drawing = editor.drawing();
        compositeTool.addTool(new TileSelectionTool(game));
        compositeTool.addTool(new TurnPassingTool(game, drawing));
        compositeTool.addTool(new UnitActionTool(game, drawing));
        compositeTool.addTool(new UnitMovingTool(game, drawing));
        compositeTool.addTool(new ProductionChangingTool(game, drawing));

        editor.setTool(compositeTool);
    }
}


