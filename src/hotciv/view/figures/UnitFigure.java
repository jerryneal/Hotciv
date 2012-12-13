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
        g.drawImage(fImage, fDisplayBox.x,
                fDisplayBox.y - GfxConstants.UNIT_OFFSET_Y,
                fDisplayBox.width, fDisplayBox.height, null);

        // Draw the owner circle
        Color color =
                GfxConstants.getColorForPlayer(unit.getOwner());
        g.setColor(color);
        g.fillOval(fDisplayBox.x, fDisplayBox.y, 8, 6);
        g.setColor(Color.black);
        g.drawOval(fDisplayBox.x, fDisplayBox.y, 8, 6);

        // Draw the 'movable' box
        g.setColor(unit.getMoveCount() > 0 ?
                Color.green : Color.red);
        g.fillOval(fDisplayBox.x, fDisplayBox.y + 7, 8, 6);
        g.setColor(Color.black);
        g.drawOval(fDisplayBox.x, fDisplayBox.y + 7, 8, 6);
    }
}
