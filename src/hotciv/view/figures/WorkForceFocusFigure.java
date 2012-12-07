package hotciv.view.Figures;

import hotciv.framework.GameConstants;
import hotciv.view.framework.GfxConstants;
import minidraw.standard.ImageFigure;

import java.awt.*;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 00:19
 */
public class WorkForceFocusFigure extends ImageFigure {
    private final static Point iconPoint = new Point(GfxConstants.WORKFORCEFOCUS_X, GfxConstants.WORKFORCEFOCUS_Y);
    private final static String appleUrl = "apple";
    private final static String hammerurl = "hammer";

    public WorkForceFocusFigure() {
        super(appleUrl, iconPoint);
    }

    public void setWorkForceFocus(String workForceFocus) {
        if (GameConstants.foodFocus.equals(workForceFocus)) {
            set(appleUrl, iconPoint);
        } else if (GameConstants.productionFocus.equals(workForceFocus)) {
            set(hammerurl, iconPoint);
        }
    }
}
