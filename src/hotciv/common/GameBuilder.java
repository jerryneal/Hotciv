package hotciv.common;

import hotciv.common.strategy.AgingStrategy;
import hotciv.common.strategy.GetWinnerStrategy;
import hotciv.framework.Player;

/**
 * This class build a Game instance, by setting the strategies the game should use, and inserts defaults for the rest.
 * @author : Erik
 * Date: 09-11-12, 11:20
 */
public class GameBuilder {
    GetWinnerStrategy winnerStrategy;
    AgingStrategy agingStrategy;
    public GameBuilder() {

    }
    public GameBuilder setWinnerStrategy(GetWinnerStrategy winnerStrategy) {
        this.winnerStrategy = winnerStrategy;
        return this;
    }
    public GameBuilder setAgingStrategy(AgingStrategy agingStrategy) {
        this.agingStrategy = agingStrategy;
        return this;
    }
    public BaseGame build() {
        // Inserting default where appropriate.
        if (this.winnerStrategy == null) {
            this.winnerStrategy = getDefaultWinnerStrategy();
        }
        if (this.agingStrategy == null) {
            this.agingStrategy = getDefaultAgingStrategy();
        }
        BaseGame res = new BaseGame(winnerStrategy, agingStrategy);
        return res;
    }

    private AgingStrategy getDefaultAgingStrategy() {
        return new AgingStrategy() {
            @Override
            public int getNewAge(BaseGame game) {
                return game.getAge() + 100;
            }
        };
    }

    private static GetWinnerStrategy getDefaultWinnerStrategy() {
        return new GetWinnerStrategy() {
            @Override
            public Player getWinner(BaseGame game) {
                if (game.getAge() >= -3000) {
                    return Player.RED;
                }
                else {
                    return null;
                }
            }
        };
    }
}
