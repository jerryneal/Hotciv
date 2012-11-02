package hotciv.standard.units;

import hotciv.framework.*;
import hotciv.standard.UnitImpl;

public class Archer extends UnitImpl {
	public Archer(Player owner) {
		super(1, owner);
	}

	@Override
	public String getTypeString() {
		return GameConstants.ARCHER;
	}
	
	@Override
	public int getDefensiveStrength() {
		return 0;
	}

	@Override
	public int getAttackingStrength() {
		return 0;
	}

}
