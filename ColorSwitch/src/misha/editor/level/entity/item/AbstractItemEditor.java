/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.item.Item;

public abstract class AbstractItemEditor<T extends Item> extends EntityEditor<T> {
	
	protected Point point;
	
	public AbstractItemEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public AbstractItemEditor(LevelEditor levelEditor, T item) {
		super(levelEditor, item);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect((int) entity.getX() - 5, (int) entity.getY() - 5, entity.getWidth() + 10, entity.getHeight() + 10);
			
			entity.draw(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (entity == null) {
			point = new Point(Math.round(e.getX() / 10) * 10, Math.round(e.getY() / 10) * 10);
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
