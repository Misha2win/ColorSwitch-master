/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.POINTS, EditableEntityType.COLORS, EditableEntityType.FIELDS })
public class Painter extends Item {
	
	private final int AURA_RADIUS = 5;
	
	private boolean active;
	
	@EditableField
	private boolean persistOnce;
	
	public Painter(int x, int y, boolean persistOnce) {
		super(x, y, 30, 30);
		super.color = CSColor.GRAY;
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
			
			color = level.getLevelManager().getPlayer().getColor();
			
			if (active) {
				for (Platform platform : level.getPlatforms()) {
					if (platform.getColor().equals(CSColor.BLACK) || platform.getColor().equals(color))
						continue;
						
					boolean paint = false;
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
		if (!used) {
			Color c = color.getGraphicsColor();
			g.setColor(c);
			g.fillRect((int) x, (int) y, width, height);
			g.setColor(c.darker());
			g.fillOval((int) x, (int) y, width, height);
			
			if (active && level != null && level.getLevelManager() != null) {
				Player player = level.getLevelManager().getPlayer();
				
				g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
				g.fillRect((int) player.getX() - AURA_RADIUS, (int) player.getY() - AURA_RADIUS, player.getWidth() + 2 * AURA_RADIUS, player.getHeight() + 2 * AURA_RADIUS);
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s", (int) x, (int) y, persistOnce);
	}
	
	@Override
	public Entity clone() {
		return new Painter((int) x, (int) y, persistOnce);
	}
	
	@Override
	public EntityEditor<?> getEntityEditor(LevelEditor levelEditor) {
		return null;
	}
	
}
