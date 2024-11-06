/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EditableField.EditableFieldType;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.POINTS, EditableEntityType.FIELDS })
public class Teleporter extends Item {
	
	static { Entity.addSubclass(Teleporter.class); }
	
	public static final Color PURPLE = new Color(150, 0, 200);
	
	@EditableField
	private boolean hasDestination;
	
	@EditableField(value = { EditableFieldType.POINT }, visible = true, radius = 20)
	private int endX, endY;
	
	public Teleporter(int x, int y, boolean hasDestination, int x2, int y2) {
		super(x, y, 30, 30);
		this.hasDestination = hasDestination;
		this.endX = x2;
		this.endY = y2;
	}
	
	@Override
	public void onUse() {	
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		if (hasDestination) {
			if (!persist) {
				removeItem();
				used = true;
			}
			
			player.setPos(endX, endY);
		} else {
			hasDestination = true;
			endX = (int)(player.getX() + .5f);
			endY = (int)(player.getY() + .5f);
		}
	}
	
	public int getX2() {
		return endX;
	}
	
	public int getY2() {
		return endY;
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			// Draw the item itself
			g.setColor(Teleporter.PURPLE);
			g.fillRect((int)x, (int)y, width, height);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.PLAIN, 14));
			g.drawString("TP", x + 7, y + 20);
			
			// Draw the TP end point
			if (hasDestination) {
				g.setColor(Teleporter.PURPLE);
				g.fillOval((int)endX, (int)endY, 20, 20);
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s %s", (int) x, (int) y, hasDestination, (int) endX, (int) endY);
	}
	
	@Override
	public Entity clone() {
		return new Teleporter((int) x, (int) y, hasDestination, endX, endY);
	}
	
}
