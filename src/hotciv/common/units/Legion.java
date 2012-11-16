package hotciv.common.units;

import hotciv.common.AbstractUnit;
import hotciv.framework.*;

public class Legion extends AbstractUnit {
    /**
     * The constructor for a legion.
     * This constructor is only to be called through a UnitFactory!
     * @param owner The owner of the archer.
     */
	public Legion(Player owner) {
		super(1, owner);
	}

	@Override
	public final String getTypeString() {
		return GameConstants.LEGION;
	}
	
	@Override
	public int getDefensiveStrength() {
		return 2;
	}

	@Override
	public int getAttackingStrength() {
		return 4;
	}

}
