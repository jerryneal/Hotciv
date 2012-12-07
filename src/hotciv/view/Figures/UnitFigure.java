package hotciv.view.Figures;

import hotciv.framework.Unit;
import minidraw.standard.ImageFigure;

import java.awt.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 11:06
 */
public class UnitFigure extends ImageFigure {
    private Unit unit;
    private Point position;

    public UnitFigure(Unit unit, Point p) {
        super(unit.getTypeString(), p);
        position = p;
        this.unit = unit;
    }

    public void draw(Graphics g) {
        // draw background color
        super.draw(g);

        // TODO: Draw something more.
    }

    public Unit getUnit() {
        return unit;
    }
}
