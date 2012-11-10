package hotciv.common;

import hotciv.common.strategy.NewAgeCalculator;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.strategy.GetWinner;
import hotciv.common.strategy.UnitAction;

/**
 * This class build a Game instance, by setting the strategies the game should use, and inserts defaults for the rest.
 * @author : Erik
 * Date: 09-11-12, 11:20
 */
public class GameBuilder {
    private GetWinner getWinner;
    private NewAgeCalculator newAgeCalculator;
    private UnitAction unitAction;
    private UnitFactory unitFactory;

    public GameBuilder() {

    }
    public GameBuilder setWinnerStrategy(GetWinner getWinner) {
        this.getWinner = getWinner;
        return this;
    }
    public GameBuilder setAgingStrategy(NewAgeCalculator newAgeCalculator) {
        this.newAgeCalculator = newAgeCalculator;
        return this;
    }
    public GameBuilder setUnitActionStrategy(UnitAction unitAction) {
        this.unitAction = unitAction;
        return this;
    }
    public GameBuilder setUnitFactoryStrategy(UnitFactory unitFactory) {
        this.unitFactory = unitFactory;
        return this;
    }

    public BaseGame build() {
        // Inserting defaults.
        if (this.getWinner == null) {
            this.getWinner = BaseGame.DefaultStrategies.getWinner();
        }
        if (this.newAgeCalculator == null) {
            this.newAgeCalculator = BaseGame.DefaultStrategies.getNewAgeCalculator();
        }
        if (this.unitAction == null) {
            this.unitAction = BaseGame.DefaultStrategies.getUnitAction();
        }
        if (this.unitFactory == null) {
            this.unitFactory = BaseGame.DefaultStrategies.getUnitFactory();
        }

        return new BaseGame(getWinner, newAgeCalculator, unitAction, unitFactory);
    }
}
