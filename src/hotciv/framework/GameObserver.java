package hotciv.framework;

/** Defines the Observer role for a Game.
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
public interface GameObserver {
  /** invoked every time some change occurs on a position
   * in the world - a unit disappears or appears, a
   * city appears, a city changes player color, or any
   * other event that requires the GUI to redraw the
   * graphics on a particular position.
   * @param pos the position in the world that has changed state
   */
  public void worldChangedAt(Position pos);

  /** invoked just after the game's end of turn is called
   * to signal the new "player in turn" and world age state.
   * @param nextPlayer the next player that may move units etc.
   * @param age the present age of the world
   */
  public void turnEnds(Player nextPlayer, int age);
    
  /** invoked whenever the user changes focus to another
   * tile (for inspecting the tile's unit and city
   * properties.)
   * @param position the position of the tile that is
   * now inspected/has focus.
   */
  public void tileFocusChangedAt(Position position);
}
