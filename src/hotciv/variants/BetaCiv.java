package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameBuilder;
import hotciv.common.strategy.GetWinner;
import hotciv.common.strategy.NewAgeCalculator;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.Map;

/**
 * @author: Erik
 * Date: 09-11-12, Time: 11:38
 */
public class BetaCiv {
    private BetaCiv() {

    }

    public static Game getGame() {
        return new GameBuilder().setWinnerStrategy(new GetWinner() {
            Player winner = null;
            @Override
            public Player getWinner(BaseGame game) {
                // The winner is the player that first conquers all cities in the world.
                if (winner == null) {
                    Player potentialWinner = null;
                    for (Map.Entry<Position, CityImpl> cityEntry : game.getGameWorld().getCityEntrySet()) {
                        City city = cityEntry.getValue();
                        if (potentialWinner == null) {
                            potentialWinner = city.getOwner();
                        }
                        if (potentialWinner == city.getOwner()) {
                            winner = potentialWinner;
                        }
                        else {
                            winner = null;
                            break;
                        }
                    }
                }

                return winner;
            }
        }).setAgingStrategy(new NewAgeCalculator() {
            @Override
            public int getNewAge(BaseGame game) {
                int age = game.getAge();
                if (age >= -4000 && age < -100) {
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
        }).build();
    }
}
