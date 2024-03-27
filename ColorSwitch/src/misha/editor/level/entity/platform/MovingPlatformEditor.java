/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.platform;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.platform.MovingPlatform;

public class MovingPlatformEditor extends AbstractPlatformEditor<MovingPlatform> {
	
	private static final int POINT_RADIUS = 3;
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 710, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 710, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 710, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 710, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 710, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 710, 40, 40);
	private static final Rectangle BLACK_BUTTON = new Rectangle(360, 710, 40, 40);
	
	private Point point;
	private boolean draggingPoint;
	
	public MovingPlatformEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public MovingPlatformEditor(LevelEditor levelEditor, MovingPlatform movingPlatform) {
		super(levelEditor, movingPlatform);
		
		if (movingPlatform != null)
			point = new Point((int) movingPlatform.getX2(), (int) movingPlatform.getY2());
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null) {
			g.setColor(Color.WHITE);
			g.drawOval((int) point.x - POINT_RADIUS, (int) point.y - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);
			g.drawLine((int) entity.getX(), (int) entity.getY(), point.x, point.y);
		}
		
		drawColorButton(g, RED_BUTTON, Color.RED, color.equals(CSColor.RED));
		drawColorButton(g, GREEN_BUTTON, Color.GREEN, color.equals(CSColor.GREEN));
		drawColorButton(g, BLUE_BUTTON, Color.BLUE, color.equals(CSColor.BLUE));
		drawColorButton(g, YELLOW_BUTTON, Color.YELLOW, color.equals(CSColor.YELLOW));
		drawColorButton(g, MAGENTA_BUTTON, Color.MAGENTA, color.equals(CSColor.MAGENTA));
		drawColorButton(g, CYAN_BUTTON, Color.CYAN, color.equals(CSColor.CYAN));
		drawColorButton(g, WHITE_BUTTON, Color.WHITE, color.equals(CSColor.WHITE));
		drawColorButton(g, BLACK_BUTTON, new Color(0, 0, 0), new Color(50, 50, 50), color.equals(CSColor.BLACK));
	}
	
	@Override
	protected MovingPlatform getEntity() {
		return new MovingPlatform(color, rectangle.x, rectangle.y, point.x, point.y, rectangle.width, rectangle.height);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			if (point != null)
				draggingPoint = (Math.sqrt(Math.pow(e.getX() - point.x, 2) + Math.pow(e.getY() - point.y, 2)) <= 2 * POINT_RADIUS);
			
			if (!draggingPoint && (entity == null || entity.getRect().contains(e.getPoint()))) {
				super.mousePressed(e);
				
				if (entity == null) {
					point = new Point(Math.round(e.getX() / 10) * 10, Math.round(e.getY() / 10) * 10);
				}
			}
			
			if (entity != null && !draggingPoint && !dragging) {
				super.unfocusEntity();
			}
		} else {
			if (RED_BUTTON.contains(e.getPoint())) {
				color = CSColor.RED;
				if (entity != null)
					if (entity != null)
					entity.setColor(color);
			} else if (GREEN_BUTTON.contains(e.getPoint())) {
				color = CSColor.GREEN;
				if (entity != null)
					entity.setColor(color);
			} else if (BLUE_BUTTON.contains(e.getPoint())) {
				color = CSColor.BLUE;
				if (entity != null)
					entity.setColor(color);
			} else if (YELLOW_BUTTON.contains(e.getPoint())) {
				color = CSColor.YELLOW;
				if (entity != null)
					entity.setColor(color);
			} else if (MAGENTA_BUTTON.contains(e.getPoint())) {
				color = CSColor.MAGENTA;
				if (entity != null)
					entity.setColor(color);
			} else if (CYAN_BUTTON.contains(e.getPoint())) {
				color = CSColor.CYAN;
				if (entity != null)
					entity.setColor(color);
			} else if (WHITE_BUTTON.contains(e.getPoint())) {
				color = CSColor.WHITE;
				if (entity != null)
					entity.setColor(color);
			} else if (BLACK_BUTTON.contains(e.getPoint())) {
				color = CSColor.BLACK;
				if (entity != null)
					entity.setColor(color);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getY() <= 600) {
			if (ignoreInput)
				return;
			
			if (draggingPoint) {
				point = e.getPoint();
			} else if (entity == null || dragging) {
				super.mouseDragged(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getY() <= 600) {
			if (ignoreInput) {
				ignoreInput = false;
				return;
			}
			
			if (draggingPoint) {
				point.setLocation(Math.round(point.getX() / 10) * 10, Math.round(point.getY() / 10) * 10);
				super.levelEditor.removeObjectFromLevel(entity);
				rectangle.setBounds((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
				super.createEntity();
			} else if (entity == null || dragging) {
				super.mouseReleased(e);
			}
		}
		
		draggingPoint = false;
	}
	
}
