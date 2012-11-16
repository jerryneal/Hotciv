package hotciv.variants.units;

import hotciv.common.units.Archer;
import hotciv.framework.Player;

/**
 * An archer that toggles its fortified status when performAction is called.
 *
 * @author Erik
 *         Created: 16-11-12, Time: 10:18
 */
public class FortifyingArcher extends Archer {
    private boolean fortified;

    public FortifyingArcher(Player owner) {
        super(owner);
        this.fortified = false;
    }

    @Override
    public int getMoveCount() {
        if (fortified) {
            return 0;
        } else {
            return super.getMoveCount();
        }
    }

    @Override
    public int getDefensiveStrength() {
        if (fortified) {
            return super.getDefensiveStrength() * 2;
        } else {
            return super.getDefensiveStrength();
        }
    }

    @Override
    public void performAction() {
        fortified = !fortified;
    }
}