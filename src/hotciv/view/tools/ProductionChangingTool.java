package hotciv.view.tools;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.figures.ProductionFigure;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 01:05
 */
public class ProductionChangingTool extends ClickingTool {
    private final Game game;
    private final Drawing drawing;

    public ProductionChangingTool(Game game, Drawing drawing) {
        this.game = game;
        this.drawing = drawing;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        Figure figure = drawing.findFigure(x, y);
        if (figure instanceof ProductionFigure) {
            Position selected = game.getTileFocus();
            City city = game.getCityAt(selected);
            if (GameConstants.ARCHER.equals(city.getProduction())) {
                game.changeProductionInCityAt(selected, GameConstants.LEGION);
            } else if (GameConstants.LEGION.equals(city.getProduction())) {
                game.changeProductionInCityAt(selected, GameConstants.SETTLER);
            } else if (GameConstants.SETTLER.equals(city.getProduction())) {
                game.changeProductionInCityAt(selected, GameConstants.ARCHER);
            } else {
                throw new RuntimeException("Unrecognized type of production in city at: " + selected);
            }
        }
    }
}
