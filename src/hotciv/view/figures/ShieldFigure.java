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
    private Point shieldPoint;

    public ShieldFigure(Point shieldPoint) {
        super(GfxConstants.NOTHING, shieldPoint);
        this.shieldPoint = shieldPoint;
    }

    public void setPlayer(Player player) {
        switch (player) {
            case RED:
                set(GfxConstants.RED_SHIELD, shieldPoint);
                break;
            case BLUE:
                set(GfxConstants.BLUE_SHIELD, shieldPoint);
                break;
        }
    }

    public void setBlank() {
        set(GfxConstants.NOTHING, shieldPoint);
    }
}
