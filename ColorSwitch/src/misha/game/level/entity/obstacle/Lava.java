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

public class Lava extends Element {
	
	public Lava(int x, int y, int w, int h) {
		super(CSColor.RED, x, y, w, h, -0.5f);
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
	
}
