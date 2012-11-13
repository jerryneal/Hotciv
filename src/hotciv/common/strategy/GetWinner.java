package hotciv.common.strategy;

import hotciv.common.BaseGame;
import hotciv.framework.Player;

/**
 * This interface describes an interface that calculates who the winner is.
 * @author : Erik
 * Date: 09-11-12, Time: 11:26
 */
public interface GetWinner {
    /**
     * Calculates the winner of the current game, or null if there is no winner yet.
     * @param game The game.
     * @return The winner.
     */
    public Player getWinner(BaseGame game);
}


