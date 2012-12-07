package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.variants.SemiCiv;
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

        editor.setTool(new HotCivController(game, editor.drawing()));
    }
}


