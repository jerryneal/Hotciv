package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.ZetaCivWinnerStrategy;

/**
 * Instance of the ZetaCiv specification, a identical to AlphaCiv except in regards to how winning the game is handled.
 *
 * @author Erik
 *         Created: 16-11-12, 17:13
 */
public class ZetaCiv implements GameFactory {

    @Override
    public Game newGame() {
        return new GameBuilder()
                .setWinnerStrategy(new ZetaCivWinnerStrategy())
                .build();
    }
}
