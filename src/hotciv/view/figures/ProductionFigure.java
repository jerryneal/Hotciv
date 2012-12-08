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

    public ProductionFigure() {
        super(GfxConstants.NOTHING, iconPoint);
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
            set(GfxConstants.ARCHER, iconPoint);
        } else if (GameConstants.LEGION.equals(production)) {
            set(GfxConstants.LEGION, iconPoint);
        } else if (GameConstants.SETTLER.equals(production)) {
            set(GfxConstants.SETTLER, iconPoint);
        } else {
            set(GfxConstants.NOTHING, iconPoint);
        }
    }
}