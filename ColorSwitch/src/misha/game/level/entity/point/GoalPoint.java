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

public class GoalPoint extends Point {
	
	public GoalPoint(int x, int y) {
		super(CSColor.GREEN, x, y, 20, 20);
	}

	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			level.getLevelManager().nextLevel();
		}
	}
	
}
