package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.ZetaCivWinnerStrategy;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 16-11-12, 17:13
 */
public class ZetaCiv implements GameFactory {

    @Override
    public Game getGame() {
        return new GameBuilder()
                .setWinnerStrategy(new ZetaCivWinnerStrategy())
                .build();
    }
}
