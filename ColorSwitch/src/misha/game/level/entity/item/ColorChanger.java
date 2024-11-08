/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Color;
import java.awt.Graphics2D;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;

@EditableEntity({ EditableEntityType.POINTS, EditableEntityType.COLORS })
public class ColorChanger extends Item {
	
	static { Entity.addSubclass(ColorChanger.class); }
	
	public ColorChanger(CSColor color, int x, int y) {
		super(x, y, 30, 30);
		this.color = color;
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		level.getLevelManager().getPlayer().setColor(color);
		
		for (Obstacle obstacle : level.getObstacles()) {
			if (obstacle instanceof Prism) {
				Prism prism = (Prism) obstacle;
				if (prism.isCollidingBeams(level.getLevelManager().getPlayer()) != null)
					obstacle.setColor(color);
			}
		}
		
		if (!persist) {
			removeItem();
			used = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			if (color.equals(CSColor.BLACK))
				g.setColor(new Color(0, 0, 0));
			else
				g.setColor(color.getGraphicsColor().darker());
			g.fillRect((int)x, (int)y, width, height);
			
			if (color.equals(CSColor.BLACK))
				g.setColor(new Color(50, 50, 50));
			else
				g.setColor(color.getGraphicsColor());
			g.fillRect((int)x + 5, (int)y + 5, width - 10, height - 10);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s", CSColor.getStringFromColor(color), (int) x, (int) y);
	}
	
	@Override
	public Entity clone() {
		return new ColorChanger(color, (int) x, (int) y);
	}
	
}
