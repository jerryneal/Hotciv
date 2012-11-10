package hotciv.common.units;

import hotciv.framework.*;
import hotciv.common.UnitImpl;

public class Legion extends UnitImpl {
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
