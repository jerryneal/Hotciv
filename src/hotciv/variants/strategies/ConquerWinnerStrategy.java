package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.Map;

/**
 * This strategy calculates the winner based on who has captured all existing cities. It returns null if no winner is found.
 * If a winner is found, it will continue to return that winner. Even if a new city is created or something else.
 *
 * @author Erik
 *         Created: 16-11-12, 10:26
 */
public class ConquerWinnerStrategy implements GetWinner {
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
                } else {
                    winner = null;
                    break;
                }
            }
        }

        return winner;
    }
}
