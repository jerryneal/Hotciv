package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
	private Player owner;
	private String production;
	
	public CityImpl(Player owner) {
		this.owner = owner;
		production = "0";
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
		return production;
	}

	public void productionInc(int increment){
		int i = Integer.parseInt(getProduction())+increment;
		production = Integer.toString(i);
	}

	@Override
	public String getWorkforceFocus() {
		// TODO Auto-generated method stub
		return null;
	}

}
