/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.point;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.POINTS })
public class GoalPoint extends Point {
	
	static { Entity.addSubclass(GoalPoint.class); }
	
	public GoalPoint(int x, int y) {
		super(CSColor.GREEN, x, y, 20, 20);
	}

	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			level.getLevelManager().nextLevel();
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s", (int) x, (int) y);
	}
	
	@Override
	public Entity clone() {
		return new GoalPoint((int) x, (int) y);
	}
	
}
