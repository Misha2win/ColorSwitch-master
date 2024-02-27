/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.obstacle;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.obstacle.Element;

public abstract class AbstractElementEditor<T extends Element> extends AbstractObstacleEditor<T> {
	
	protected Rectangle rectangle;
	
	public AbstractElementEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public AbstractElementEditor(LevelEditor levelEditor, T platform) {
		super(levelEditor, platform);
		
		rectangle = new Rectangle(-10, -10, 0, 0);
	}
	
	public Rectangle getNormalRect(Rectangle rectangle) {
		int rx = rectangle.x + (rectangle.width < 0 ? rectangle.width : 0);
		int ry = rectangle.y + (rectangle.height < 0 ? rectangle.height : 0);
		int rw = (rectangle.width <= 0 ? -rectangle.width + 10 : rectangle.width);
		int rh = (rectangle.height <= 0 ? -rectangle.height + 10 : rectangle.height);
		return new Rectangle(rx, ry, rw, rh);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect((int) entity.getX() - 5, (int) entity.getY() - 5, entity.getWidth() + 10, entity.getHeight() + 10);
			
			entity.draw(g);
		} else {
			Color c = color.getGraphicsColor();
			g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
			g.fill((rectangle.width <= 0 || rectangle.height <= 0) ? getNormalRect(rectangle) : rectangle);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (entity == null) {
			rectangle.x = Math.round((e.getPoint().x) / 10) * 10;
			rectangle.y = Math.round((e.getPoint().y) / 10) * 10;
		} else {
			if (entity.getRect().contains(e.getPoint())) {
				super.beginDraggingEntity(e.getPoint());
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (entity == null) {
			rectangle.width = Math.round((e.getPoint().x - rectangle.x) / 10) * 10;
			rectangle.height = Math.round((e.getPoint().y - rectangle.y) / 10) * 10;
		} else if (dragging) {
			super.dragEntity(e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (entity == null) {
			rectangle = getNormalRect(rectangle);
			if (rectangle.width != rectangle.height || rectangle.width != 10) {
				super.createEntity();
			}
		} else if (dragging) {
			super.dropEntity();
		}
		
		rectangle.x = -10;
		rectangle.y = -10;
		rectangle.width = 0;
		rectangle.height = 0;
		dragging = false;
	}
	
}
