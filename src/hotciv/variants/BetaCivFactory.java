package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.common.strategy.NewAgeCalculator;
import hotciv.variants.strategies.ConquerWinnerStrategy;
import hotciv.variants.strategies.PeriodicAgingStrategy;

/**
 * * A factory that creates the strategies needed by the game to make it act like BetaCiv.
 *
 * @author Erik
 *         Created: 22-11-12, 13:15
 */
public class BetaCivFactory extends AlphaCivFactory {
    @Override
    public GetWinner createWinnerStrategy(BaseGame game) {
        return new ConquerWinnerStrategy(game);
    }

    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game) {
        return new PeriodicAgingStrategy(game);
    }
}
