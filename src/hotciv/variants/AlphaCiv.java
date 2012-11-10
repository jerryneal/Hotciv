package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.*;

/**
 * @author: Erik
 * Date: 06-11-12, Time: 12:52
 */
public class AlphaCiv {
    private AlphaCiv() {

    }
    public static Game getGame() {
        return new GameBuilder().build();
    }

}
