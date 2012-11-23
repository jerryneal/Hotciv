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
    private BaseGame game;

    public LinearAgeStrategy(BaseGame game) {
        this.game = game;
    }

    public int getNewAge() {
        return game.getAge() + 100;
    }
}
