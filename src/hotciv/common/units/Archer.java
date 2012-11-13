package hotciv.common.units;

import hotciv.framework.*;
import hotciv.common.UnitImpl;

public class Archer extends UnitImpl {
    /**
     * The constructor for an archer.
     * This constructor is only to be called through a UnitFactory!
     * @param owner The owner of the archer.
     */
	public Archer(Player owner) {
		super(1, owner);
	}

	@Override
	public final String getTypeString() {
		return GameConstants.ARCHER;
	}
	
	@Override
	public int getDefensiveStrength() {
		return 3;
	}

	@Override
	public int getAttackingStrength() {
		return 2;
	}

}
