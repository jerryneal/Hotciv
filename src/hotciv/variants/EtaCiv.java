package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 18:17
 */
public class EtaCiv implements GameFactory {
    @Override
    public Game newGame() {
        return new BaseGame(new EtaCivFactory());
    }
}
