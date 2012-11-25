package hotciv.common;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

/**
 * The default implementation of a city.
 * <p/>
 * It can everything specified in the City interface, except getWorkforceFocus().
 * It also provides methods to increase and decrease productionAmount.
 */
public class CityImpl implements City {
    private Player owner;
    private String produces;
    private int productionAmount;
    private String workForceFocus;
    private int size;

    /**
     * The default and only constructor for CityImpl.
     *
     * @param owner The owner of the city.
     */
    public CityImpl(Player owner) {
        this.owner = owner;
        // We set a city to pr. default produce settlers.
        this.produces = GameConstants.SETTLER;
        this.productionAmount = 0;

        // workForceFocus is on production pr. default.
        this.workForceFocus = GameConstants.productionFocus;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getProduction() {
        return produces;
    }

    @Override
    public void setProduction(String production) {
        if (production.equals(GameConstants.SETTLER) || production.equals(GameConstants.ARCHER) || production.equals(GameConstants.LEGION)) {
            this.produces = production;
        } else {
            throw new IllegalArgumentException("The set production unit should be a Settler, archer or legion");
        }
    }

    @Override
    public int getProductionAmount() {
        return this.productionAmount;
    }

    /**
     * Increases the production amount in this city by the specified amount.
     *
     * @param increase The amount.
     */
    public void increaseProductionAmount(int increase) {
        productionAmount += increase;
    }

    /**
     * Decreases the production amount in this city by the specified amount.
     *
     * @param amount
     */
    public void decreaseProductionAmount(int amount) {
        this.productionAmount -= amount;
    }

    @Override
    public String getWorkforceFocus() {
        return this.workForceFocus;
    }

    /**
     * Sets the workForceFocus for this city, se GameConstants for valid values.
     *
     * @param workForceFocus the workForceFocus.
     */
    public void setWorkForceFocus(String workForceFocus) {
        this.workForceFocus = workForceFocus;
    }

    /**
     * Sets the size of the city.
     *
     * @param size the size.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
