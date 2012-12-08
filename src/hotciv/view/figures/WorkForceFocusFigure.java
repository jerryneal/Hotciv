package hotciv.view.figures;

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
    private final static String hammerUrl = "hammer";
    private final static String blankUrl = "blank";

    public WorkForceFocusFigure() {
        super(blankUrl, iconPoint);
    }

    /**
     * Sets the workForceFocus icon
     * <p/>
     * Call with an empty string or null to set it to a blank icon.
     *
     * @param workForceFocus The workForceFocus for the selected city, or null.
     */
    public void setWorkForceFocus(String workForceFocus) {
        if (GameConstants.foodFocus.equals(workForceFocus)) {
            set(appleUrl, iconPoint);
        } else if (GameConstants.productionFocus.equals(workForceFocus)) {
            set(hammerUrl, iconPoint);
        } else {
            set(blankUrl, iconPoint);
        }
    }
}
