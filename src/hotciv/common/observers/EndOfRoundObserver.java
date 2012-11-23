package hotciv.common.observers;

/**
 * This observer is called after every end of round, if attached to a BaseGame.
 *
 * @author Erik
 *         Created: 23-11-12, 11:30
 */
public interface EndOfRoundObserver {
    public void endOfRound();
}
