package hotciv.visual;

import hotciv.framework.Game;
import hotciv.variants.AlphaCiv;
import hotciv.view.figures.TextFigure;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.DrawingView;
import minidraw.framework.Factory;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;
import minidraw.standard.StandardDrawing;
import minidraw.standard.StdViewWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Test the TextFigure to display age in
 * the status panel.
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
public class ShowText {

    public static void main(String[] args) {

        Game game = new AlphaCiv().newGame();

        DrawingEditor editor =
                new MiniDrawApplication("Click to see age text change...",
                        new HotCivFactory3(game));
        editor.open();
        TextFigure tf = new TextFigure("4000 BC",
                new Point(GfxConstants.AGE_TEXT_X,
                        GfxConstants.AGE_TEXT_Y));
        editor.drawing().add(tf);
        editor.setTool(new ChangeAgeTool(tf));

    }
}

class ChangeAgeTool extends NullTool {
    private TextFigure textFigure;

    public ChangeAgeTool(TextFigure tf) {
        textFigure = tf;
    }

    int count = 0;

    public void mouseDown(MouseEvent e, int x, int y) {
        count++;
        textFigure.setText("" + (4000 - count * 100) + " BC");
    }
}

class HotCivFactory2 implements Factory {
    private Game game;

    public HotCivFactory2(Game g) {
        game = g;
    }

    public DrawingView createDrawingView(DrawingEditor editor) {
        DrawingView view =
                new StdViewWithBackground(editor, "hotciv-background");
        return view;
    }

    public Drawing createDrawing(DrawingEditor editor) {
        return new StandardDrawing();
    }

    public JTextField createStatusField(DrawingEditor editor) {
        return null;
    }
}
