package hotciv.view.figures;

import hotciv.framework.Player;
import hotciv.view.framework.GfxConstants;
import minidraw.standard.ImageFigure;

import java.awt.*;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 13:01
 */
public class UnitShieldFigure extends ImageFigure {
    private final static Point shieldPoint = new Point(GfxConstants.UNIT_SHIELD_X, GfxConstants.UNIT_SHIELD_Y);
    private final static String redShieldUrl = "redshield";
    private final static String blueShieldUrl = "blueshield";

    public UnitShieldFigure() {
        super(redShieldUrl, shieldPoint);
    }

    public void setUnitOwner(Player player) {
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
