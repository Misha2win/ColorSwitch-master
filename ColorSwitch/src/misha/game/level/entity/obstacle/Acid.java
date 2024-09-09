/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.editor.level.entity.obstacle.HealthPoolEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.PLATFORMS })
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
	
	@Override
	public Entity clone() {
		return new Acid((int) x, (int) y, width, height);
	}
	
	@Override
	public EntityEditor<?> getEntityEditor(LevelEditor levelEditor) {
		return new HealthPoolEditor(levelEditor, this);
	}
	
}
