/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

public abstract class Element extends Obstacle {

	protected float damageOnHit;
	
	public Element(CSColor color, int x, int y, int w, int h, float dmg) {
		super(color, x, y, w, h);
		damageOnHit = dmg;
	}
	
	public float getDamage() {
		return damageOnHit;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color.getGraphicsColor().darker());
		
		if (level != null && level.getLevelManager() != null) {
			Player player = level.getLevelManager().getPlayer();
			Player mirrorPlayer = player.getMirrorPlayer();
			if (!level.getLevelManager().getDebugMode()) {
				if (player.getColor().collidesWith(color) || (player.getMirrored() && mirrorPlayer.getColor().collidesWith(color))) {
					Color c = color.getGraphicsColor().darker();
					g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
				}
			}
		}

		g.fillRect((int)x, (int)y, width, height);
	}
	
	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).addHealth(damageOnHit);
		}
	}
	
}
