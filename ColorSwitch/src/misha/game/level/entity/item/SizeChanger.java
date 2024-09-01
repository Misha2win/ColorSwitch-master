/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Polygon;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.game.level.entity.player.Player;

import java.awt.Color;

public class SizeChanger extends Item {
	
	private boolean enlarge;
	
	public SizeChanger(int x, int y, boolean enlarge) {
		super(x, y, 30, 30);
		
		this.enlarge = enlarge;
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		if (enlarge) {
			player.setDimensions(player.getWidth() + 10, player.getHeight() + 10);
		} else {
			if (player.getWidth() > 10)
				player.setDimensions(player.getWidth() - 10, player.getHeight() - 10);
		}
		
		
		if (!persist) {
			removeItem();
			used = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			g.setColor(Color.RED);
			g.fillRect((int)x, (int)y, width, height);
			
			g.setColor(Color.WHITE);
			Polygon p = new Polygon();
			if (enlarge) {
				p.addPoint((int)(x + width / 2), (int)(y + 5));
				p.addPoint((int)(x + width - 5), (int)(y + height / 2) - 2);
				p.addPoint((int)(x + 5), (int)(y + height / 2) - 2);
			} else {
				p.addPoint((int)(x + width / 2), (int)(y + height / 2) - 2);
				p.addPoint((int)(x + width - 5), (int)(y + 5));
				p.addPoint((int)(x + 5), (int)(y + 5));
			}
			g.fill(p);
			
			p = new Polygon();
			if (enlarge) {
				p.addPoint((int)(x + width / 2), (int)(y + height - 5));
				p.addPoint((int)(x + width - 5), (int)(y + height / 2 + 2));
				p.addPoint((int)(x + 5), (int)(y + height / 2 + 2));
			} else {
				p.addPoint((int)(x + width / 2), (int)(y + height / 2 + 2));
				p.addPoint((int)(x + width - 5), (int)(y + height -5));
				p.addPoint((int)(x + 5), (int)(y + height - 5));
			}
			g.fill(p);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s", (int) x, (int) y, enlarge);
	}
	
	@Override
	public EntityEditor<?> getEntityEditor(LevelEditor levelEditor) {
		return null;
		//return new SizeChangerEditor(levelEditor, this);
	}
	
}
