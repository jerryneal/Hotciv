package hotciv.standard.units;

import hotciv.framework.*;
import hotciv.standard.UnitImpl;

public class Settler extends UnitImpl {
	public Settler(Player owner) {
		super(1, owner);
	}

	@Override
	public String getTypeString() {
		return GameConstants.SETTLER;
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
