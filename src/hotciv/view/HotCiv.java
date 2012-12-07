package hotciv.view;

import hotciv.common.BaseGame;
import hotciv.variants.SemiCiv;
import minidraw.standard.*;
import minidraw.framework.*;

import javax.swing.*;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/** Show a basic world.
 *
 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Computer Science Department
 Aarhus University

 This source code is provided WITHOUT ANY WARRANTY either
 expressed or implied. You may study, use, modify, and
 distribute it for non-commercial purposes. For any
 commercial use, see http://www.baerbak.com/
 */
public class HotCiv {
    public static void main(String[] args) {
        new HotCiv();
    }

    Map<Position, Figure> cityFigureMap = new HashMap<Position, Figure>();

    public HotCiv() {
        BaseGame game = new SemiCiv().newGame();

        DrawingEditor editor = new MiniDrawApplication( "HotCiv", new HotCivViewFactory(game) );
        editor.open();

        editor.setTool(new HotCivTool(game));
    }
}


