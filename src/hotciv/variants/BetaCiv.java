package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.ConquerWinnerStrategy;
import hotciv.variants.strategies.PeriodicAgingStrategy;

/**
 * @author: Erik
 * Date: 09-11-12, Time: 11:38
 */
public class BetaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of BetaCiv.
     *
     * @return The game.
     */
    public Game getGame() {
        return new GameBuilder()
                .setWinnerStrategy(new ConquerWinnerStrategy())
                .setAgingStrategy(new PeriodicAgingStrategy())
                .build();
    }
}
