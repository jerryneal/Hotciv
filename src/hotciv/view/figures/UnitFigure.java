package hotciv.view.figures;

import hotciv.framework.Unit;
import hotciv.view.framework.GfxConstants;
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
        super.draw(g);

        g.setColor(GfxConstants.getColorForPlayer(unit.getOwner()));
        g.fillOval(position.x, position.y,
                10, 10);
    }

    @Override
    protected void basicMoveBy(int x, int y) {
        super.basicMoveBy(x, y);
        position = new Point(position.x + x, position.y + y);
    }

    public Unit getUnit() {
        return unit;
    }
}
