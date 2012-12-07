package hotciv.framework;

import hotciv.common.BaseGame;

/**
 * This is a Game factory, that produces an instance of Game
 *
 * @author Erik
 *         Created: 16-11-12, Time: 09:49
 */
public interface GameFactory {
    public BaseGame newGame();
}
