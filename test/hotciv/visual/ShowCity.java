package hotciv.visual;

import hotciv.variants.AlphaCiv;
import hotciv.view.Figures.CityFigure;
import hotciv.view.FrameWork.GfxConstants;
import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;

/** Test the CityFigure.
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
public class ShowCity {
  
  public static void main(String[] args) {

    Game game = new AlphaCiv().newGame();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click to see city graphics update...",  
                               new HotCivFactory3(game) );
    editor.open();
    CityStub city = new CityStub();

    CityFigure cf = new CityFigure( city,
                                    new Point( GfxConstants.getXFromColumn(4),
                                               GfxConstants.getYFromRow(7) ) );
    editor.drawing().add(cf);
    editor.setTool( new ChangeCityTool(city, cf) );

  }
}

class ChangeCityTool extends NullTool {
  private CityStub city;
  private CityFigure cityFigure;
  public ChangeCityTool(CityStub c, CityFigure cf) {
    city = c;
    cityFigure = cf;
  }
  public void mouseDown(MouseEvent e, int x, int y) {
    city.makeAChange();
    cityFigure.changed();
  }
}

class HotCivFactory3 implements Factory {
  private Game game;
  public HotCivFactory3(Game g) { game = g; }

  public DrawingView createDrawingView( DrawingEditor editor ) {
    DrawingView view = 
      new MapView(editor, game);
    return view;
  }

  public Drawing createDrawing( DrawingEditor editor ) {
    return new StandardDrawing();
  }

  public JTextField createStatusField( DrawingEditor editor ) {
    return null;
  }
}

// a test stub implementation just to 
// force some graphical updates.
class CityStub implements City {
  boolean redOwns = true;
  // a testing method just to make some
  // state changes
  public void  makeAChange() {
    redOwns = ! redOwns;
  }
  public Player getOwner() {
    return (redOwns ? Player.RED : Player.BLUE);
  }

  public int getSize() {
    return (redOwns ? 4 : 9);
  }
  public String getProduction() {
    return null;
  }
  public void setProduction(String production) {

  }
  public int getProductionAmount() {
    return 0;
  }

  public String getWorkforceFocus() {
    return null;
  }
}
