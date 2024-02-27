/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import misha.game.level.entity.Entity;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.entity.Updatable;
import misha.game.level.entity.player.Player;

public abstract class Item extends Entity implements Updatable {
	
	protected String itemName;
	protected boolean isCollected;
	protected boolean used;
	protected boolean dropped;
	protected boolean persist;
	
	public Item(String itemName, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.itemName = itemName;
		isCollected = false;
		dropped = false;
		persist = false;
	}
	
	public void onCollect() {
		if (level == null)
			return;
		
		if (level.getLevelManager().getPlayer().hasInventorySpace()) {
			isCollected = true;
			collectItem();
		}
	}
	
	public abstract void onUse();
	
	@Override
	public void update() {
	}
	
	public boolean getUsed() {
		return used;
	}
	
	public void setRefresh(boolean newRefresh) {
		persist = newRefresh;
	}
	
	public boolean getIsCollected() {
		return isCollected;
	}
	
	public void setIsCollected(boolean newIsCollected) {
		isCollected = newIsCollected;
	}
	
	public void collectItem() {
		if (level == null)
			return;
		
		level.getLevelManager().getPlayer().addInventory(this);
	}
	
	public void dropItem() {
		removeItem();
		isCollected = false;
		dropped = true;
		
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		x = player.getX() + player.getWidth() / 2 - 15;
		y = player.getY() + player.getHeight() / 2 - 15;
	}
	
	public void removeItem() {
		if (level == null)
			return;
		
		level.getLevelManager().getPlayer().removeInventory(this);
		PhysicsEngine.calcPhysics(level.getLevelManager()); // Attempt to pick up an item?
	}
	
	public String getName() {
		return itemName;
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			if (!isCollected) {
				onCollect();
				if (persist)
					isCollected = false;
			}
		}
	}
	
}
