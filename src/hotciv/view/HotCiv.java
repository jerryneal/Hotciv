package hotciv.view;

import hotciv.common.BaseGame;
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
        BaseGame game = new SemiCiv().newGame();

        // Uncomment to print working directory.
        /*URL location = HotCiv.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());*/

        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();

        CompositeTool compositeTool = new CompositeTool();

        Drawing drawing = editor.drawing();
        compositeTool.addTool(new TurnPassingTool(game, drawing));
        compositeTool.addTool(new UnitActionTool(game, drawing));
        compositeTool.addTool(new UnitMovingTool(game, drawing));
        compositeTool.addTool(new WorkForceFocusChanginTool(game, drawing));
        compositeTool.addTool(new TileSelectionTool(game));

        editor.setTool(compositeTool);
    }
}


