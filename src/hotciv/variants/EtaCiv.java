package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.GameFactory;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 18:17
 */
public class EtaCiv implements GameFactory {
    @Override
    public BaseGame newGame() {
        return new BaseGame(new EtaCivFactory());
    }
}
