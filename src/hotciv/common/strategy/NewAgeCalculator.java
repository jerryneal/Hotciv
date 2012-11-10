package hotciv.common.strategy;

import hotciv.common.BaseGame;

/**
 * @author : Erik
 * Date: 09-11-12, 11:46
 */
public interface NewAgeCalculator {
    /**
     * Is called in the end of each round to get the new age of the game.
     * @param game The game instance.
     * @return The new age.
     */
    public int getNewAge(BaseGame game);
}
