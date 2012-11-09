package hotciv.standard.units;

import hotciv.framework.*;
import hotciv.standard.UnitImpl;

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
		return 0;
	}

	@Override
	public int getAttackingStrength() {
		return 0;
	}

}
