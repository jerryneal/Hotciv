package hotciv.view.figures;

import hotciv.framework.Player;
import hotciv.view.framework.GfxConstants;

import java.awt.*;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 13:19
 */
public class TurnShield extends ShieldFigure {
    public TurnShield() {
        super(new Point(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y));
        this.setPlayer(Player.RED);
    }
}
