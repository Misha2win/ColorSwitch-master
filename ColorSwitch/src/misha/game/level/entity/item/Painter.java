/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.POINTS, EditableEntityType.COLORS, EditableEntityType.FIELDS })
public class Painter extends Item {
	
	static { Entity.addSubclass(Painter.class); }
	
	private final int AURA_RADIUS = 5;
	
	private boolean active;
	
	@EditableField
	private boolean persistOnce;
	
	public Painter(CSColor color, int x, int y, boolean persistOnce) {
		super(x, y, 30, 30);
		super.color = color;
		this.persistOnce = persistOnce;
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		if (!persist && !persistOnce) {
			removeItem();
			used = true;
		}
		
		persistOnce = false;
		active = !active;
	}
	
	@Override
	public void update() {
		if (level != null && level.getLevelManager() != null) {
			Player player = level.getLevelManager().getPlayer();
			
			if (active) {
				for (Platform platform : level.getPlatforms()) {
					if (platform.getColor().equals(CSColor.BLACK) || platform.getColor().equals(color))
						continue;
						
					boolean paint = false; // XXX
					paint = PhysicsEngine.willCollide(player, player.getXVelocity(), player.getYVelocity() - AURA_RADIUS, platform, 0, 0);
					if (!paint)
						paint = PhysicsEngine.willCollide(player, player.getXVelocity() + AURA_RADIUS, player.getYVelocity(), platform, 0, 0);
					if (!paint)
						paint = PhysicsEngine.willCollide(player, player.getXVelocity(), player.getYVelocity() + AURA_RADIUS, platform, 0, 0);
					if (!paint)
						paint = PhysicsEngine.willCollide(player, player.getXVelocity() - AURA_RADIUS, player.getYVelocity(), platform, 0, 0);
					
					if (paint)
						platform.setColor(color);
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = color.getGraphicsColor();
		
		if (!used) {
			Color gold = new Color(255, 215, 0);
			g.setColor(persistOnce ? gold : Color.WHITE.darker());
			g.fillRect((int) x, (int) y, width, height);
			g.setColor(persistOnce ? gold : Color.WHITE);
			g.fillOval((int) x, (int) y, width, height);
			
			g.setColor(new Color(139, 69, 19));
			g.fillRect((int) x + width / 2 - 3, (int) y + height / 2, 6, 13);
			g.fillRect((int) x + width / 2 - 10, (int) y + height / 2, 20, 5);
			
			g.setColor(c);
			g.fillRect((int) x + width / 2 - 10, (int) y + height / 2 - 10, 20, 10);
		}
		
		if (active && level != null && level.getLevelManager() != null) {
			Player player = level.getLevelManager().getPlayer();
			
			g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
			g.fillRect((int) player.getX() - AURA_RADIUS, (int) player.getY() - AURA_RADIUS, player.getWidth() + 2 * AURA_RADIUS, player.getHeight() + 2 * AURA_RADIUS);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", color, (int) x, (int) y, persistOnce);
	}
	
	@Override
	public Entity clone() {
		return new Painter(color, (int) x, (int) y, persistOnce);
	}
	
}
