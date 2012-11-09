package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.GameBuilder;
import hotciv.common.strategy.AgingStrategy;
import hotciv.common.strategy.GetWinnerStrategy;
import hotciv.framework.Game;
import hotciv.framework.Player;

/**
 * @author: Erik
 * Date: 09-11-12, Time: 11:38
 */
public class BetaCiv {
    private BetaCiv() {

    }
    public static Game getGame() {
        return new GameBuilder().setAgingStrategy(new AgingStrategy() {
            @Override
            public int getNewAge(BaseGame game) {
                int age = game.getAge();
                if (age >= -4000 && age < -100) {
                    return age + 100;
                }
                else if (age < 50) {
                    switch (age) {
                        case -100: return -1;
                        case -1: return 1;
                        case 1: return 50;
                    }
                }
                else if (age < 1750) {
                    return age + 50;
                }
                else if (age < 1900) {
                    return age + 25;
                }
                else if (age < 1970) {
                    return age + 5;
                }
                else if (age >= 1970) {
                    return age + 1;
                }
                throw new RuntimeException("Unrecognized age: " + age);
            }
        }).build();
    }
}
