package hotciv.variants.strategies;

import hotciv.common.AbstractUnit;
import hotciv.common.BaseGame;
import hotciv.common.strategy.NewAgeCalculator;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.Random;

/**
 * Ages the game a differently based on what period we are in.
 *
 * @author Erik
 *         Created: 16-11-12, 10:28
 */
public class PeriodicAgingStrategy implements NewAgeCalculator {
    private BaseGame game;

    public PeriodicAgingStrategy(BaseGame game) {
        this.game = game;
    }

    @Override
    public int getNewAge() {
        doEasterEgg();
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

    private void doEasterEgg() {
        if (game.getAge() != -1) {
            return;
        }
        // Ugly, check!
        // Easter egg, check!
        game.getGameWorld().placeUnitNear(new Position(8, 8), new AbstractUnit(2, getRandomPlayer()) {
            @Override
            public String getTypeString() {
                return "jesus";
            }

            @Override
            public int getDefensiveStrength() {
                Random random = new Random();
                if (random.nextBoolean()) {
                    return 1000;
                } else {
                    return 10;
                }
            }

            @Override
            public int getAttackingStrength() {
                return 1000;
            }
        });
    }

    private Player getRandomPlayer() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return Player.RED;
        } else {
            return Player.BLUE;
        }

    }
}
