/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.point;

import java.awt.Graphics2D;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

public class SpawnPoint extends Point {
	
	private boolean isObtainable;
	private boolean isActive;
	
	public SpawnPoint(int x, int y, boolean isActive, boolean isObtainable) {
		super(CSColor.RED, x, y, 20, 20);
		this.isActive = isActive;
		this.isObtainable = isObtainable;
	}
	
	public boolean getIsObtainable() {
		return isObtainable;
	}
	
	public void setIsObtainable(boolean newIsObtainable) {
		isObtainable = newIsObtainable;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(boolean active) {
		isActive = active;
	}

	@Override
	public void draw(Graphics2D g) {
		if (isActive) {
			g.setColor(color.getGraphicsColor());
		} else {
			g.setColor(color.getGraphicsColor().darker().darker());
		}
		
		g.fillOval((int)x, (int)y, width, height);
	}

	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			if (isObtainable && !isActive) {
				level.getActiveSpawnPoint().isActive = false;
				isActive = true;
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", (int) x, (int) y, isActive, isObtainable);
	}
	
}
