package hotciv.common;

import hotciv.common.strategy.*;
import hotciv.framework.Game;

/**
 * This class build a Game instance, by setting the strategies the game should use, and inserts defaults for the rest.
 *
 * @author : Erik
 *         Date: 09-11-12, 11:20
 */
public class GameBuilder {
    private GetWinner getWinner;
    private NewAgeCalculator newAgeCalculator;
    private UnitFactory unitFactory;
    private WorldLayoutStrategy worldLayoutStrategy;
    private AttackResolver attackResolver;

    /**
     * Constructs a new GameBuilder.
     */
    public GameBuilder() {

    }

    /**
     * Sets the GetWinner strategy.
     *
     * @param getWinner The strategy.
     * @return This GameBuilder.
     */
    public GameBuilder setWinnerStrategy(GetWinner getWinner) {
        this.getWinner = getWinner;
        return this;
    }

    /**
     * Sets the NewAgeCalculator strategy.
     *
     * @param newAgeCalculator The strategy.
     * @return This GameBuilder.
     */
    public GameBuilder setAgingStrategy(NewAgeCalculator newAgeCalculator) {
        this.newAgeCalculator = newAgeCalculator;
        return this;
    }

    /**
     * Sets the UnitFactory strategy.
     *
     * @param unitFactory The strategy.
     * @return This GameBuilder.
     */
    public GameBuilder setUnitFactoryStrategy(UnitFactory unitFactory) {
        this.unitFactory = unitFactory;
        return this;
    }

    /**
     * Sets the WorldLayoutStrategy.
     *
     * @param worldLayoutStrategy The strategy.
     * @return this GameBuilder.
     */
    public GameBuilder setWorldLayoutStrategy(WorldLayoutStrategy worldLayoutStrategy) {
        this.worldLayoutStrategy = worldLayoutStrategy;
        return this;
    }

    /**
     * TODO: Doc.
     *
     * @param resolver
     * @return
     */
    public GameBuilder setAttackResolverStrategy(AttackResolver resolver) {
        this.attackResolver = resolver;
        return this;
    }

    /**
     * Builds the game based on the strategies set previously. It inserts defaults from BaseGame.DefaultStrategies if no other strategy was set.
     *
     * @return The new game instance.
     */
    public Game build() {
        // Inserting defaults.
        if (this.getWinner == null) {
            this.getWinner = BaseGame.DefaultStrategies.getWinner();
        }
        if (this.newAgeCalculator == null) {
            this.newAgeCalculator = BaseGame.DefaultStrategies.getNewAgeCalculator();
        }
        if (this.unitFactory == null) {
            this.unitFactory = BaseGame.DefaultStrategies.getUnitFactory();
        }
        if (this.worldLayoutStrategy == null) {
            this.worldLayoutStrategy = BaseGame.DefaultStrategies.getWorldLayoutStrategy();
        }
        if (this.attackResolver == null) {
            this.attackResolver = BaseGame.DefaultStrategies.getAttackResolver();
        }

        return new BaseGame(getWinner, newAgeCalculator, unitFactory, worldLayoutStrategy, attackResolver);
    }
}
