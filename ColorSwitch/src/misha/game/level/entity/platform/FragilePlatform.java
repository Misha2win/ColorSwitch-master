/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.entity.Updatable;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.PLATFORMS, EditableEntityType.COLORS })
public class FragilePlatform extends Platform implements Updatable {
	
	static { Entity.addSubclass(FragilePlatform.class); }
	
	private boolean breakPlatform;
	private boolean broken;
	
	public FragilePlatform(CSColor color, int x, int y, int w, int h) {
		super(color, x, y, w, h);
		
		this.broken = false;
		this.breakPlatform = false;
	}
	
	public boolean isBroken() {
		return broken;
	}
	
	@Override
	public void update() {
		if (level != null && !broken) {
			Player player = level.getLevelManager().getPlayer();
			if (breakPlatform) {
				if (!player.getColor().collidesWith(color) || !PhysicsEngine.willCollide(player, 0, 1, this, 0, 0)) {
					broken = true;
					color = CSColor.GRAY;
				}
			} else {
				if (player.getColor().collidesWith(color) && PhysicsEngine.willCollide(player, 0, 1, this, 0, 0)) {
					breakPlatform = true;
				}
			}
		}
	}
	
	@Override
	public void onCollision(Entity e) {
		if (!broken) {
			super.onCollision(e);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (!broken) {
			super.draw(g);
			
			g.setColor(Color.GRAY);
			g.drawLine((int) x, (int) y, (int) x + width, (int) y + height);
			g.drawLine((int) x + width, (int) y, (int) x, (int) y + height);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s %s", CSColor.getStringFromColor(color), (int) x, (int) y, width, height); 
	}
	
	@Override
	public Entity clone() {
		return new FragilePlatform(color, (int) x, (int) y, width, height);
	}
	
}
