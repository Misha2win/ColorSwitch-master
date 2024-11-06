/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.PLATFORMS })
public class Lava extends Element {
	
	static { Entity.addSubclass(Lava.class); }
	
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
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", (int) x, (int) y, width, height);
	}
	
	@Override
	public Entity clone() {
		return new Lava((int) x, (int) y, width, height);
	}
	
}
