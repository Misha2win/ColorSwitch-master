/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.point;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.point.Point;

public abstract class AbstractPointEditor<T extends Point> extends EntityEditor<T> {
	
	protected java.awt.Point point;
	
	public AbstractPointEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public AbstractPointEditor(LevelEditor levelEditor, T item) {
		super(levelEditor, item);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillOval((int) entity.getX() - 5, (int) entity.getY() - 5, entity.getWidth() + 10, entity.getHeight() + 10);
			
			entity.draw(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (entity == null) {
			point = new java.awt.Point(Math.round(e.getX() / 10) * 10, Math.round(e.getY() / 10) * 10);
			super.createEntity();
			ignoreInput = true;
		} 

		if (entity.getRect().contains(e.getPoint())) {
			super.beginDraggingEntity(e.getPoint());
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (ignoreInput)
			return;
		
		if (dragging) {
			super.dragEntity(e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (ignoreInput) {
			dragging = false;
			ignoreInput = false;
			return;
		}
		
		if (dragging) {
			super.dropEntity();
		}
	}
	
}
