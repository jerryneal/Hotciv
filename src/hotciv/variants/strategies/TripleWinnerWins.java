package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.observers.WinnerObserver;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.Player;

import java.util.HashMap;

/**
 * This strategy is an implementation of the EpsilonCiv and ZetaCiv winner strategies. It returns the player who first gets 3 battle wins.
 *
 * @author Erik
 *         Created: 16-11-12, 16:42
 */
public class TripleWinnerWins implements GetWinner, WinnerObserver {
    private HashMap<Player, Integer> playerWonCounter;

    public TripleWinnerWins(final BaseGame game) {
        this.playerWonCounter = new HashMap<Player, Integer>();

        // Observers
        game.addWinnerObserver(this);
    }

    Player winner;

    @Override
    public Player getWinner() {
        if (winner == null) {
            for (Player player : playerWonCounter.keySet()) {
                if (playerWonCounter.get(player) >= 3) {
                    winner = player;
                    break;
                }
            }
        }
        return winner;
    }

    @Override
    public void playerWonBattle(Player winner) {
        if (playerWonCounter.get(winner) == null) {
            playerWonCounter.put(winner, 1);
        } else {
            int playerWon = playerWonCounter.get(winner) + 1;
            playerWonCounter.put(winner, playerWon);
        }
    }
}
