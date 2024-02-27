/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;

public class DamagePack extends Item {
	
	public DamagePack(int x, int y) {
		super("DamagePack", x, y, 30, 30);
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		level.getLevelManager().getPlayer().addHealth(-20);
		
		if (!persist) {
			removeItem();
			used = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			g.setColor(Color.RED);
			g.fillRect((int)x, (int)y, width, height);
			
			g.setColor(Color.WHITE);
			g.fillRoundRect((int)x + 2, (int)(y + height / 2) - 3, width - 4, 6, 3, 3);
		}
	}
	
}
