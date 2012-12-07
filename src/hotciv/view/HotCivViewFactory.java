package hotciv.view;

import hotciv.framework.Game;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.DrawingView;
import minidraw.framework.Factory;
import minidraw.standard.StandardDrawing;

import javax.swing.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 10:20
 */
public class HotCivViewFactory implements Factory {
    private Game game;
    public HotCivViewFactory(Game g) { game = g; }

    public DrawingView createDrawingView( DrawingEditor editor ) {
        DrawingView view = new MapView(editor, game);
        return view;
    }

    public Drawing createDrawing( DrawingEditor editor ) {
        return new StandardDrawing();
    }

    public JTextField createStatusField( DrawingEditor editor ) {
        return null;
    }
}
