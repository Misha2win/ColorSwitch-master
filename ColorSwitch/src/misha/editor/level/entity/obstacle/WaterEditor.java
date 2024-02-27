/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.obstacle;

import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.obstacle.Water;

public class WaterEditor extends AbstractElementEditor<Water> {
	
	public WaterEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public WaterEditor(LevelEditor levelEditor, Water water) {
		super(levelEditor, water);
	}

	@Override
	protected Water getEntity() {
		return new Water(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			
			if (entity != null && !dragging)
				super.unfocusEntity();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getY() <= 600) {
			if (ignoreInput)
				return;
			
			super.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getY() <= 600) {
			if (ignoreInput) {
				ignoreInput = false;
				return;
			}
			
			super.mouseReleased(e);
		}
	}
	
}
