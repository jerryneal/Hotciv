package hotciv.view;

import hotciv.framework.*;
import hotciv.view.figures.*;
import hotciv.view.framework.GfxConstants;
import minidraw.framework.Figure;
import minidraw.standard.StandardDrawing;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 07-12-12, 11:49
 */
public class HotCivDrawing extends StandardDrawing implements GameObserver {
    private Game game;
    private TextFigure ageTextField;
    private ShieldFigure turnShield;

    private WorkForceFocusFigure workForceFocusFigure;
    private ShieldFigure unitShield;
    private ShieldFigure cityShield;
    private ProductionFigure productionFigure;
    private TextFigure movesLeftText;

    public HotCivDrawing(Game game) {
        this.game = game;
        game.addObserver(this);
        placeAllUnitsAndCities();


        ageTextField = new TextFigure("4000 BC",
                new Point(GfxConstants.AGE_TEXT_X,
                        GfxConstants.AGE_TEXT_Y));
        add(ageTextField);

        turnShield = new TurnShield();
        turnShield.setPlayer(Player.RED);
        add(turnShield);

        unitShield = new ShieldFigure(new Point(GfxConstants.UNIT_SHIELD_X, GfxConstants.UNIT_SHIELD_Y));
        add(unitShield);

        cityShield = new ShieldFigure(new Point(GfxConstants.CITY_SHIELD_X, GfxConstants.CITY_SHIELD_Y));
        add(cityShield);

        productionFigure = new ProductionFigure();
        add(productionFigure);

        workForceFocusFigure = new WorkForceFocusFigure();
        add(workForceFocusFigure);

        movesLeftText = new TextFigure("", new Point(GfxConstants.UNIT_COUNT_X, GfxConstants.UNIT_COUNT_Y));
        add(movesLeftText);
    }

    private void placeAllUnitsAndCities() {
        // Placing all cities and units in the GameWorld.
        for (int x = 0; x < GameConstants.WORLDSIZE; x++) {
            for (int y = 0; y < GameConstants.WORLDSIZE; y++) {
                Position position = new Position(y, x);
                City city = game.getCityAt(position);
                if (city != null) {
                    this.add(new CityFigure(city,
                            new Point(GfxConstants.getXFromColumn(x),
                                    GfxConstants.getYFromRow(y))));
                }

                Unit unit = game.getUnitAt(position);
                if (unit != null) {
                    this.add(new UnitFigure(unit,
                            new Point(GfxConstants.getXFromColumn(x),
                                    GfxConstants.getYFromRow(y))));
                }
            }
        }
    }

    @Override
    public void worldChangedAt(Position position) {
        lock();

        Iterator<Figure> figureIterator = this.iterator();
        List<Figure> figureList = new ArrayList<Figure>();
        while (figureIterator.hasNext()) {
            Figure next = figureIterator.next();
            if (next instanceof UnitFigure || next instanceof CityFigure) {
                figureList.add(next);
            }
        }

        for (Figure figure : figureList) {
            this.remove(figure);
        }

        placeAllUnitsAndCities();

        tileFocusChangedAt(game.getTileFocus());

        unlock();


        requestUpdate();
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        ageTextField.setText(Integer.toString(Math.abs(age)) + ((age >= 0) ? " AD" : " BC"));
        turnShield.setPlayer(nextPlayer);
        requestUpdate();
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        Unit unit = game.getUnitAt(position);
        if (unit != null) {
            unitShield.setPlayer(unit.getOwner());
            movesLeftText.setText(Integer.toString(unit.getMoveCount()));
        } else {
            unitShield.setBlank();
            movesLeftText.setText("");
        }

        City city = game.getCityAt(position);
        if (city != null) {
            workForceFocusFigure.setWorkForceFocus(city.getWorkforceFocus());
            cityShield.setPlayer(city.getOwner());
            productionFigure.setProduction(city.getProduction());
        } else {
            workForceFocusFigure.setWorkForceFocus(null);
            cityShield.setBlank();
            productionFigure.setProduction(null);
        }
    }


}
