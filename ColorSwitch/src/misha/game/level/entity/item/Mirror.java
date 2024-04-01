/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.player.Player;
import misha.game.level.Level;

public class Mirror extends Item {
	
	public boolean persistOnce;
	
	public Mirror(int x, int y, CSColor mirrorColor, boolean persistOnce) {
		super(x, y, 30, 30);
		super.color = mirrorColor;
		this.persistOnce = persistOnce;
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		
		if (!persist && !persistOnce) {
			removeItem();
			used = true;
		}
		
		persistOnce = false;

		player.setMirrored(!player.getMirrored());
		player.getMirrorPlayer().setColor(color);
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			// Draw player reflection
			g.setColor(color.getGraphicsColor());
			g.fillRect((int)x + 5, (int)y + 5, 20, 20);
			
			// Draw blue shade of the mirror
			g.setColor(new Color(0, 150, 255, 100));
			g.fillRect((int)x, (int)y, width, height);
			
			// Draw the light glares of mirrror
			g.setColor(new Color(255, 255, 255, 200));
			g.setStroke(new BasicStroke(3));
			g.drawLine((int)x + 12, (int)y + 3, (int)x + 27, (int)y + 17);
			g.drawLine((int)x + 21, (int)y + 4, (int)x + 26, (int)y + 9);
			
			// Draw the border of the mirror
			g.setStroke(new BasicStroke(1));
			g.setColor(persistOnce ? new Color(218, 165, 32) : Color.BLACK);
			g.drawRect((int)x, (int)y, width, height);
			
			if (level != null && level.getLevelManager() != null && level.getLevelManager().getPlayer().getMirrored()) {
				if (level == null)
					return;
				
				// Draw cracks on mirror
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.BLACK);
				g.drawLine((int)x + 5, (int)y + 5, (int)x + width / 2 + 5, (int)y + width / 2 - 5);
				g.drawLine((int)x + width / 2 + 5, (int)y + width / 2 - 5, (int)x + width / 2 - 5, (int)y + width / 2 + 5);
				g.drawLine((int)x + width - 5, (int)y + height - 5, (int)x + width / 2 - 5, (int)y + width / 2 + 5);
			}
		}
	}
	
	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		
		level.createGhostPlatforms(color);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", (int) x, (int) y, CSColor.getStringFromColor(color), persistOnce);
	}
	
}
