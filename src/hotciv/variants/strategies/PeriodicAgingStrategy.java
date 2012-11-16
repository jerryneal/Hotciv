package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.NewAgeCalculator;

/**
 * //TODO: Doc
 *
 * @author: Erik
 * Created: 16-11-12, 10:28
 */
public class PeriodicAgingStrategy implements NewAgeCalculator {
    @Override
    public int getNewAge(BaseGame game) {
        int age = game.getAge();
        if (-4000 <= age && age < -100) {
            return age + 100;
        } else if (age < 50) {
            switch (age) {
                case -100:
                    return -1;
                case -1:
                    return 1;
                case 1:
                    return 50;
            }
        } else if (age < 1750) {
            return age + 50;
        } else if (age < 1900) {
            return age + 25;
        } else if (age < 1970) {
            return age + 5;
        } else if (age >= 1970) {
            return age + 1;
        }
        throw new RuntimeException("Unrecognized age: " + age);
    }
}
