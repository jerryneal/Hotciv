package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.variants.SemiCiv;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * Show a basic world.
 * <p/>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Computer Science Department
 * Aarhus University
 * <p/>
 * This source code is provided WITHOUT ANY WARRANTY either
 * expressed or implied. You may study, use, modify, and
 * distribute it for non-commercial purposes. For any
 * commercial use, see http://www.baerbak.com/
 */
public class HotCiv {
    private DrawingEditor editor;

    public static void main(String[] args) {
        new HotCiv();
    }

    public HotCiv() {
        BaseGame game = new SemiCiv().newGame();

        this.editor = new MiniDrawApplication("HotCiv", new HotCivFactory(game));

        editor.open();


        editor.setTool(new HotCivTool(game));
    }
}


