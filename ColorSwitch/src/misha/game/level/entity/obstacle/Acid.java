/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

public class Acid extends Element {
	
	public Acid(int x, int y, int w, int h) {
		super(CSColor.GREEN, x, y, w, h, 0.5f);
	}
	
	@Override
	public void update() {
	}

	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).addHealth(damageOnHit);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", (int) x, (int) y, width, height);
	}
	
}
