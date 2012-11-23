package hotciv.common.observers;

import hotciv.framework.Player;

/**
 * This observer is called after a player has won a battle.
 *
 * @author Erik
 *         Created: 23-11-12, 11:10
 */
public interface WinnerObserver {
    public void playerWonBattle(Player winner);
}
