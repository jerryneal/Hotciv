package hotciv.view.figures;

import hotciv.framework.GameConstants;
import hotciv.view.framework.GfxConstants;
import minidraw.standard.ImageFigure;

import java.awt.*;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 01:06
 */
public class ProductionFigure extends ImageFigure {
    private final static Point iconPoint = new Point(GfxConstants.CITY_PRODUCTION_X, GfxConstants.CITY_PRODUCTION_Y);
    private final static String archerUrl = "archer";
    private final static String legionUrl = "legion";
    private final static String settlerUrl = "settler";
    private final static String blankUrl = "blank";

    public ProductionFigure() {
        super(blankUrl, iconPoint);
    }

    /**
     * Sets the production focus icon
     * <p/>
     * Call with an empty string or null to set it to a blank icon.
     *
     * @param production The production of the city currently selected.
     */
    public void setProduction(String production) {
        if (GameConstants.ARCHER.equals(production)) {
            set(archerUrl, iconPoint);
        } else if (GameConstants.LEGION.equals(production)) {
            set(legionUrl, iconPoint);
        } else if (GameConstants.SETTLER.equals(production)) {
            set(settlerUrl, iconPoint);
        } else {
            set(blankUrl, iconPoint);
        }
    }
}
