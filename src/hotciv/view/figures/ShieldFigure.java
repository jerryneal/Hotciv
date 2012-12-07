package hotciv.view.figures;

import hotciv.framework.Player;
import hotciv.view.framework.GfxConstants;
import minidraw.standard.ImageFigure;

import java.awt.*;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 07-12-12, 23:03
 */
public class ShieldFigure extends ImageFigure {
    private final static Point shieldPoint = new Point(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y);
    private final static String redShieldUrl = "redshield";
    private final static String blueShieldUrl = "blueshield";

    public ShieldFigure() {
        super(redShieldUrl, shieldPoint);
    }

    public void setPlayerInTurn(Player player) {
        switch (player) {
            case RED:
                set(redShieldUrl, shieldPoint);
                break;
            case BLUE:
                set(blueShieldUrl, shieldPoint);
                break;
        }
    }
}
