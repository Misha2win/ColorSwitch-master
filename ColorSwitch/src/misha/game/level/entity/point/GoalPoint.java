/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.point;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.point.GoalPointEditor;
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
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s", (int) x, (int) y);
	}
	
	@Override
	public EntityEditor<?> getEntityEditor(LevelEditor levelEditor) {
		return new GoalPointEditor(levelEditor, this);
	}
	
}
