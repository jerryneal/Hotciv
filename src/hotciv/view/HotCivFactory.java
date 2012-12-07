package hotciv.view;

import hotciv.common.BaseGame;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.DrawingView;
import minidraw.framework.Factory;

import javax.swing.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 10:20
 */
public class HotCivFactory implements Factory {
    private BaseGame game;

    public HotCivFactory(BaseGame g) {
        game = g;
    }

    public DrawingView createDrawingView(DrawingEditor editor) {
        // Create view in MVC
        DrawingView view = new MapView(editor, game);
        return view;
    }

    public Drawing createDrawing(DrawingEditor editor) {
        // Create the model in MVC
        HotCivDrawing drawing = new HotCivDrawing(game);
        return drawing;
    }

    public JTextField createStatusField(DrawingEditor editor) {
        // Create status field.
        return new JTextField("Dette er en tekst i statusfelt.");
    }
}
