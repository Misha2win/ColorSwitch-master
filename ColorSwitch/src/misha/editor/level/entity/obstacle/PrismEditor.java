/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.obstacle.Prism;

public class PrismEditor extends AbstractObstacleEditor<Prism> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 710, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 710, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 710, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 710, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 710, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 710, 40, 40);
	private static final Rectangle BLACK_BUTTON = new Rectangle(360, 710, 40, 40);
	private static final Rectangle UP_BUTTON = new Rectangle(10, 760, 40, 40);
	private static final Rectangle RIGHT_BUTTON = new Rectangle(60, 760, 40, 40);
	private static final Rectangle DOWN_BUTTON = new Rectangle(110, 760, 40, 40);
	private static final Rectangle LEFT_BUTTON = new Rectangle(160, 760, 40, 40);
	
	private Point point;
	private int direction;
	
	public PrismEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public PrismEditor(LevelEditor levelEditor, Prism prism) {
		super(levelEditor, prism);
		
		if (prism != null) {
			color = entity.getColor();
		} else {
			color = CSColor.BLACK;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect((int) entity.getX() - 5, (int) entity.getY() - 5, entity.getWidth() + 10, entity.getHeight() + 10);
			
			entity.draw(g);
		}
		
		DrawUtil.drawColorButton(g, RED_BUTTON, Color.RED, color.equals(CSColor.RED));
		DrawUtil.drawColorButton(g, GREEN_BUTTON, Color.GREEN, color.equals(CSColor.GREEN));
		DrawUtil.drawColorButton(g, BLUE_BUTTON, Color.BLUE, color.equals(CSColor.BLUE));
		DrawUtil.drawColorButton(g, YELLOW_BUTTON, Color.YELLOW, color.equals(CSColor.YELLOW));
		DrawUtil.drawColorButton(g, MAGENTA_BUTTON, Color.MAGENTA, color.equals(CSColor.MAGENTA));
		DrawUtil.drawColorButton(g, CYAN_BUTTON, Color.CYAN, color.equals(CSColor.CYAN));
		DrawUtil.drawColorButton(g, WHITE_BUTTON, Color.WHITE, color.equals(CSColor.WHITE));
		DrawUtil.drawColorButton(g, BLACK_BUTTON, new Color(0, 0, 0), new Color(50, 50, 50), color.equals(CSColor.BLACK));
		
		DrawUtil.drawColorButton(g, UP_BUTTON, Color.WHITE, direction == Prism.UP);
		g.setColor(Color.GRAY.darker().darker());
		Polygon upArrow = new Polygon();
		upArrow.addPoint(UP_BUTTON.x + 20, UP_BUTTON.y + 12);
		upArrow.addPoint(UP_BUTTON.x + 27, UP_BUTTON.y + 27);
		upArrow.addPoint(UP_BUTTON.x + 12, UP_BUTTON.y + 27);
		g.fill(upArrow);
		
		DrawUtil.drawColorButton(g, RIGHT_BUTTON, Color.WHITE, direction == Prism.RIGHT);
		g.setColor(Color.GRAY.darker().darker());
		Polygon rightArrow = new Polygon();
		rightArrow.addPoint(RIGHT_BUTTON.x + 12, RIGHT_BUTTON.y + 12);
		rightArrow.addPoint(RIGHT_BUTTON.x + 27, RIGHT_BUTTON.y + 20);
		rightArrow.addPoint(RIGHT_BUTTON.x + 12, RIGHT_BUTTON.y + 27);
		g.fill(rightArrow);
		
		DrawUtil.drawColorButton(g, DOWN_BUTTON, Color.WHITE, direction == Prism.DOWN);
		g.setColor(Color.GRAY.darker().darker());
		Polygon downArrow = new Polygon();
		downArrow.addPoint(DOWN_BUTTON.x + 27, DOWN_BUTTON.y + 12);
		downArrow.addPoint(DOWN_BUTTON.x + 12, DOWN_BUTTON.y + 12);
		downArrow.addPoint(DOWN_BUTTON.x + 20, DOWN_BUTTON.y + 27);
		g.fill(downArrow);
		
		DrawUtil.drawColorButton(g, LEFT_BUTTON, Color.WHITE, direction == Prism.LEFT);
		g.setColor(Color.GRAY.darker().darker());
		Polygon leftArrow = new Polygon();
		leftArrow.addPoint(LEFT_BUTTON.x + 27, LEFT_BUTTON.y + 12);
		leftArrow.addPoint(LEFT_BUTTON.x + 12, LEFT_BUTTON.y + 20);
		leftArrow.addPoint(LEFT_BUTTON.x + 27, LEFT_BUTTON.y + 27);
		g.fill(leftArrow);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			if (entity != null) {
				if (entity.getRect().contains(e.getPoint())) {
					super.beginDraggingEntity(e.getPoint());
				}
				
				if (entity != null && !dragging)
					super.unfocusEntity();
			} else {
				point = new Point(Math.round(e.getX() / 10) * 10, Math.round(e.getY() / 10) * 10);
				super.createEntity();
				ignoreInput = true;
			}
		} else {
			if (RED_BUTTON.contains(e.getPoint())) {
				color = CSColor.RED;
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
			} else if (UP_BUTTON.contains(e.getPoint())) {
				direction = Prism.UP;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					point = new Point((int) entity.getX(), (int) entity.getY());
					super.createEntity();
				}
			} else if (RIGHT_BUTTON.contains(e.getPoint())) {
				direction = Prism.RIGHT;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					point = new Point((int) entity.getX(), (int) entity.getY());
					super.createEntity();
				}
			} else if (DOWN_BUTTON.contains(e.getPoint())) {
				direction = Prism.DOWN;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					point = new Point((int) entity.getX(), (int) entity.getY());
					super.createEntity();
				}
			} else if (LEFT_BUTTON.contains(e.getPoint())) {
				direction = Prism.LEFT;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					point = new Point((int) entity.getX(), (int) entity.getY());
					super.createEntity();
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (ignoreInput)
			return;
		
		if (e.getY() <= 600) {
			if (dragging) {
				super.dragEntity(e.getPoint());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (ignoreInput) {
			ignoreInput = false;
			return;
		}
		
		if (e.getY() <= 600) {
			if (dragging) {
				super.dropEntity();
			}
		}
	}

	@Override
	protected Prism getEntity() {
		return new Prism(color, point.x, point.y, direction);
	}
	
}
