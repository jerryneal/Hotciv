package hotciv.view.tools;

import hotciv.common.BaseGame;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.framework.GfxConstants;

import java.awt.event.MouseEvent;

/**
 * TODO: Docment
 *
 * @author: Erik
 * Date: 08-12-12, 00:23
 */
public class TileSelectionTool extends ClickingTool {
    private final BaseGame game;

    public TileSelectionTool(BaseGame game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent, int x, int y) {
        int posX = GfxConstants.getTileXFromPixel(x);
        int posY = GfxConstants.getTileYFromPixel(y);
        if (posX > 0 && posX < GameConstants.WORLDSIZE && posY > 0 && posY < GameConstants.WORLDSIZE) {
            game.setTileFocus(new Position(posY, posX));
        }
    }
}
