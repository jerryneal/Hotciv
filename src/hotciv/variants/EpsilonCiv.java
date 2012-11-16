package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.EpsilonCivAttackResolver;
import hotciv.variants.strategies.TripleWinnerWins;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 16-11-12, 15:38
 */
public class EpsilonCiv implements GameFactory {
    @Override
    public Game getGame() {
        return new GameBuilder()
                .setAttackResolverStrategy(new EpsilonCivAttackResolver())
                .setWinnerStrategy(new TripleWinnerWins())
                .build();
    }
}
