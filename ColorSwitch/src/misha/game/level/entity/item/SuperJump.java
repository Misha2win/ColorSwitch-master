/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;

public class SuperJump extends Item {
	
	public SuperJump(int x, int y) {
		super("SuperJump", x, y, 30, 30);
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		level.getLevelManager().getPlayer().setYVelocity(-24f);
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
			Polygon p = new Polygon();
			p.addPoint((int)(x + width / 2), (int)(y + 5));
			p.addPoint((int)(x + width - 5), (int)(y + height / 2));
			p.addPoint((int)(x + 5), (int)(y + height / 2));
			g.fill(p);
			
			p = new Polygon();
			p.addPoint((int)(x + width / 2), (int)(y + height / 2 - 5 + 5));
			p.addPoint((int)(x + width - 5), (int)(y + height / 2 + 11));
			p.addPoint((int)(x + 5), (int)(y + height / 2 + 11));
			g.fill(p);
		}
	}
	
}
