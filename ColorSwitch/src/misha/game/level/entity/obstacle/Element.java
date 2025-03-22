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
	
	private static final double PULSE_FREQUENCY = 250;

	protected float damageOnHit;
	
	public Element(CSColor color, int x, int y, int w, int h, float dmg) {
		super(color, x, y, w, h);
		damageOnHit = dmg;
	}
	
	public float getDamage() {
		return damageOnHit;
	}
	
	private Color getPulsingColor(int alpha) {
		double sin = (0.5) + (Math.sin(System.currentTimeMillis() / PULSE_FREQUENCY) + 1) / 6;
		Color c = color.getGraphicsColor().darker();
		return new Color((int) (sin * c.getRed()), (int) (sin * c.getGreen()), (int) (sin * c.getBlue()), alpha);
	}
	
	private boolean isCollidable() {
		if (level != null && level.getLevelManager() != null) {
			Player player = level.getLevelManager().getPlayer();
			Player mirrorPlayer = player.getMirrorPlayer();
			if (!level.getLevelManager().getDebugMode()) {
				if (player.getColor().collidesWith(color) || (player.getMirrored() && mirrorPlayer.getColor().collidesWith(color))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int alpha = isCollidable() ? 255 : 50;
		g.setColor(getPulsingColor(alpha));

		g.fillRect((int)x, (int)y, width, height);
	}
	
	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).addHealth(damageOnHit);
		}
	}
	
}
