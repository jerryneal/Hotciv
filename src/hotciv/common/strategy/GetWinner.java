package hotciv.common.strategy;

import hotciv.framework.Player;

/**
 * This interface describes a strategy that calculates who the winner is.
 *
 * @author : Erik
 *         Date: 09-11-12, Time: 11:26
 */
public interface GetWinner {
    /**
     * Calculates the winner of the current game, or null if there is no winner yet.
     *
     * @return The winner.
     */
    public Player getWinner();
}


