/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.item.DamagePack;

public class DamagePackEditor extends AbstractItemEditor<DamagePack> {
	
	public DamagePackEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public DamagePackEditor(LevelEditor levelEditor, DamagePack damagePack) {
		super(levelEditor, damagePack);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	@Override
	protected DamagePack getEntity() {
		return new DamagePack(point.x, point.y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		}
	}
	
}
