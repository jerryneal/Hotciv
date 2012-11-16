package hotciv.variants.units;

import hotciv.common.units.Archer;
import hotciv.framework.Player;

/**
 * // TODO: Doc.
 * @author: Erik
 * Created: 16-11-12, Time: 10:18
 */
public class GammaArcher extends Archer {
    private boolean fortified;
    public GammaArcher(Player owner) {
        super(owner);
        this.fortified = false;
    }
    public void switchFortify() {
        fortified = !fortified;
    }
    @Override
    public int getMoveCount() {
        if (fortified) {
            return 0;
        }
        else {
            return super.getMoveCount();
        }
    }
    @Override
    public int getDefensiveStrength() {
        if (fortified) {
            return super.getDefensiveStrength() * 2;
        }
        else {
            return super.getDefensiveStrength();
        }
    }
}