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
import misha.game.level.entity.item.SuperJump;

public class SuperJumpEditor extends AbstractItemEditor<SuperJump> {
	
	public SuperJumpEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public SuperJumpEditor(LevelEditor levelEditor, SuperJump superJump) {
		super(levelEditor, superJump);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	@Override
	protected SuperJump getEntity() {
		return new SuperJump(point.x, point.y);
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
