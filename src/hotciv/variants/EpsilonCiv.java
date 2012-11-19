package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.EpsilonCivAttackResolver;
import hotciv.variants.strategies.TripleWinnerWins;

/**
 * Conforming to the EpsilonCiv specification, this class creates an instance of a game where battles are resolves using the
 * EpsilonCiv algorithm, and the winner of the game is whoever wins 3 battles first.
 *
 * @author Erik
 *         Created: 16-11-12, 15:38
 */
public class EpsilonCiv implements GameFactory {
    @Override
    public Game getGame() {
        return new GameBuilder()
                .setAttackResolverStrategy(new EpsilonCivAttackResolver(new RandomDice()))
                .setWinnerStrategy(new TripleWinnerWins())
                .build();
    }
}
