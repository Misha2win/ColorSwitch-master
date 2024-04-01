/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.point;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;
import misha.game.level.Level;

public class PortalPoint extends Point {
	
	private final int portalLinkID;
	private PortalPoint linkedPortal;
	
	private boolean cooldown;
	
	public PortalPoint(int x, int y, int portalLinkID) {
		super(CSColor.BLUE, x, y, 20, 20);
		this.portalLinkID = portalLinkID;
	}
	
	public void setCooldown(boolean bool) {
		cooldown = bool;
	}
	
	public int getPortalLink() {
		return portalLinkID;
	}

	@Override
	public void onCollision(Entity entity) {
		if (linkedPortal != null && !cooldown) {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				player.setPos(linkedPortal.getX(), linkedPortal.getY());
				player.setXVelocity(0);
				player.setYVelocity(0);
				linkedPortal.cooldown = true;
			}
		}
	}
	
	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		
		for (Point point : level.getPoints()) {
			if (point instanceof PortalPoint) {
				PortalPoint portalPoint = ((PortalPoint) point);
				if (point != this && portalPoint.portalLinkID == this.portalLinkID) {
					this.linkedPortal = portalPoint;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s", (int) x, (int) y, portalLinkID);
	}
	
}
