package hotciv.common.strategy;

import hotciv.common.BaseGame;
import hotciv.framework.Player;

/**
 * This interface describes an interface that calculates who the winner is.
 * @author : Erik
 * Date: 09-11-12, Time: 11:26
 */
public interface GetWinner {
    public Player getWinner(BaseGame game);
}


