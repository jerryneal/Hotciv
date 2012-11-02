package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
	private Player owner;
	public CityImpl(Player owner) {
		this.owner = owner;
	}

	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public String getProduction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWorkforceFocus() {
		// TODO Auto-generated method stub
		return null;
	}

}
