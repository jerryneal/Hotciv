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
    private final static String redShieldUrl = "redshield";
    private final static String blueShieldUrl = "blueshield";
    private final static String blankUrl = "blank";

    private Point shieldPoint;

    public ShieldFigure(Point shieldPoint) {
        super(blankUrl, shieldPoint);
        this.shieldPoint = shieldPoint;
    }

    public void setPlayer(Player player) {
        switch (player) {
            case RED:
                set(redShieldUrl, shieldPoint);
                break;
            case BLUE:
                set(blueShieldUrl, shieldPoint);
                break;
        }
    }

    public void setBlank() {
        set(blankUrl, shieldPoint);
    }
}
