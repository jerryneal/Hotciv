package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.City;
import hotciv.framework.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * This strategy calculates the winner based on who has captured all existing cities. It returns null if no winner is found.
 * If a winner is found, it will continue to return that winner. Even if a new city is created or something else.
 *
 * @author Erik
 *         Created: 16-11-12, 10:26
 */
public class ConquerWinnerStrategy implements GetWinner {
    private BaseGame game;

    public ConquerWinnerStrategy(BaseGame game) {
        this.game = game;
    }

    Player winner = null;

    @Override
    public Player getWinner() {
        // The winner is the player that first conquers all cities in the world.
        Set<Player> playerHasCity = new HashSet<Player>();
        if (winner == null) {
            for (City c : game.getGameWorld().getCities()) {
                playerHasCity.add(c.getOwner());
            }
            if (playerHasCity.size() == 1) {
                winner = playerHasCity.iterator().next();
            }
        }

        return winner;
    }
}
