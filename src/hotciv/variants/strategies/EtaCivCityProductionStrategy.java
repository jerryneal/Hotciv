package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.strategy.CityProductionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;

import java.util.*;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 19:52
 */
public class EtaCivCityProductionStrategy implements CityProductionStrategy {
    private BaseGame game;

    private Map<CityImpl, Integer> foodInCities = new HashMap<CityImpl, Integer>();

    public EtaCivCityProductionStrategy(BaseGame game) {
        this.game = game;
    }

    @Override
    public void produceOnCity(CityImpl city) {
        GameWorld gameWorld = game.getGameWorld();

        int productionIncrease = 0;
        int foodIncrease = 0;

        int citySize = city.getSize();

        String focus = city.getWorkforceFocus();

        if (citySize > 0) {
            productionIncrease += 1;
            foodIncrease += 1;

            Position cityPosition = gameWorld.getCityPosition(city);

            String centerTile = game.getTileAt(cityPosition).getTypeString();
            productionIncrease += productionFromTile(centerTile);
            foodIncrease += foodFromTile(centerTile);

            List<Tile> tilesAroundCity = new ArrayList<Tile>();
            for (Position position : cityPosition.getPositionsWithinDistance(1)) {
                tilesAroundCity.add(game.getTileAt(position));
            }
            Comparator<Tile> tileSorter;
            if (GameConstants.foodFocus.equals(focus)) {
                tileSorter = new FoodTileSorter();
            } else if (GameConstants.productionFocus.equals(focus)) {
                tileSorter = new ProductionTileSorter();
            } else {
                throw new RuntimeException("The focus of a city can only be foodFocus or productionFocus, it was: " + focus);
            }

            Collections.sort(tilesAroundCity, tileSorter);

            for (int i = 0; i < citySize - 1; i++) {
                String typeString = tilesAroundCity.get(i).getTypeString();
                productionIncrease += productionFromTile(typeString);
                foodIncrease += foodFromTile(typeString);
            }
        }

        // Production is stored in the city.
        city.increaseProductionAmount(productionIncrease);

        // Food is stored internally.
        Integer prevFood = foodInCities.get(city);
        if (prevFood == null) {
            prevFood = 0;
        }
        int food = prevFood + foodIncrease;
        foodInCities.put(city, food);

        if (food > 5 + (citySize) * 3 && citySize < 9) {
            foodInCities.put(city, 0);
            city.setSize(citySize + 1);
        }
    }

    private int productionFromTile(String type) {
        int res;
        if (GameConstants.PLAINS.equals(type)) {
            res = 0;
        } else if (GameConstants.OCEANS.equals(type)) {
            res = 0;
        } else if (GameConstants.FOREST.equals(type)) {
            res = 3;
        } else if (GameConstants.MOUNTAINS.equals(type)) {
            res = 1;
        } else if (GameConstants.HILLS.equals(type)) {
            res = 2;
        } else {
            throw new IllegalArgumentException("Unrecognized tile type. ");
        }
        return res;
    }

    private int foodFromTile(String type) {
        int res;
        if (GameConstants.PLAINS.equals(type)) {
            res = 3;
        } else if (GameConstants.OCEANS.equals(type)) {
            res = 1;
        } else if (GameConstants.FOREST.equals(type)) {
            res = 0;
        } else if (GameConstants.MOUNTAINS.equals(type)) {
            res = 0;
        } else if (GameConstants.HILLS.equals(type)) {
            res = 0;
        } else {
            throw new IllegalArgumentException("Unrecognized tile type. ");
        }
        return res;
    }

    private class FoodTileSorter implements Comparator<Tile> {
        @Override
        public int compare(Tile tile1, Tile tile2) {
            int res = foodFromTile(tile2.getTypeString()) - foodFromTile(tile1.getTypeString());
            if (res == 0) {
                return productionFromTile(tile2.getTypeString()) - productionFromTile(tile1.getTypeString());
            }
            return res;
        }
    }

    private class ProductionTileSorter implements Comparator<Tile> {
        @Override
        public int compare(Tile tile1, Tile tile2) {
            int res = productionFromTile(tile2.getTypeString()) - productionFromTile(tile1.getTypeString());
            if (res == 0) {
                return foodFromTile(tile2.getTypeString()) - foodFromTile(tile1.getTypeString());
            }
            return res;
        }
    }
}
