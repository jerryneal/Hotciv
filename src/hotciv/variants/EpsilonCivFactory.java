package hotciv.variants;

import hotciv.common.strategy.AttackResolver;
import hotciv.common.strategy.GetWinner;
import hotciv.variants.strategies.Dice;
import hotciv.variants.strategies.EpsilonCivAttackResolver;
import hotciv.variants.strategies.TripleWinnerWins;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:23
 */
public class EpsilonCivFactory extends AlphaCivFactory {
    private Dice dice;

    public EpsilonCivFactory(Dice dice) {
        this.dice = dice;
    }

    @Override
    public AttackResolver createAttackResolverStrategy() {
        return new EpsilonCivAttackResolver(dice);
    }

    @Override
    public GetWinner createWinnerStrategy() {
        return new TripleWinnerWins();
    }
}
