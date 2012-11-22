package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.NewAgeCalculator;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 15:42
 */
public class LinearAgeStrategy implements NewAgeCalculator {
    public int getNewAge(BaseGame game) {
        return game.getAge() + 100;
    }
}
