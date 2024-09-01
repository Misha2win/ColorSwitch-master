/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.platform;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.event.MouseEvent;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.platform.HealthGate;

public class HealthGateEditor extends AbstractPlatformEditor<HealthGate> {
	
	private static final Rectangle GREATER_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle LESS_BUTTON = new Rectangle(60, 710, 40, 40);
	private static final Rectangle ADD_BUTTON = new Rectangle(10, 760, 40, 40);
	private static final Rectangle SUBTRACT_BUTTON = new Rectangle(60, 760, 40, 40);
	
	private boolean more;
	private int health;
	
	public HealthGateEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public HealthGateEditor(LevelEditor levelEditor, HealthGate platform) {
		super(levelEditor, platform);
		
		if (platform != null) {
			more = platform.getRule();
			health = platform.getHealthRule();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		DrawUtil.drawColorButton(g, GREATER_BUTTON, Color.WHITE, more);
		g.setColor(Color.GRAY.darker().darker());
		Polygon upArrow = new Polygon();
		upArrow.addPoint(GREATER_BUTTON.x + 20, GREATER_BUTTON.y + 13);
		upArrow.addPoint(GREATER_BUTTON.x + 27, GREATER_BUTTON.y + 27);
		upArrow.addPoint(GREATER_BUTTON.x + 12, GREATER_BUTTON.y + 27);
		g.fill(upArrow);
		
		DrawUtil.drawColorButton(g, LESS_BUTTON, Color.WHITE, !more);
		g.setColor(Color.GRAY.darker().darker());
		Polygon downArrow = new Polygon();
		downArrow.addPoint(LESS_BUTTON.x + 27, LESS_BUTTON.y + 13);
		downArrow.addPoint(LESS_BUTTON.x + 12, LESS_BUTTON.y + 13);
		downArrow.addPoint(LESS_BUTTON.x + 20, LESS_BUTTON.y + 27);
		g.fill(downArrow);
		
		
		DrawUtil.drawColorButton(g, ADD_BUTTON, Color.WHITE, false);
		g.setColor(Color.BLACK);
		g.fillRoundRect(ADD_BUTTON.x + 11, (ADD_BUTTON.y + 5 + 30 / 2) - 3, 18, 6, 3, 3);
		g.fillRoundRect((ADD_BUTTON.x + 5 + 30 / 2) - 3, ADD_BUTTON.y + 11, 6, 18, 3, 3);
		g.setColor(Color.WHITE);
		g.fillRoundRect(ADD_BUTTON.x + 12, (ADD_BUTTON.y + 5 + 30 / 2) - 2, 16, 4, 3, 3);
		g.fillRoundRect((ADD_BUTTON.x + 5 + 30 / 2) - 2, ADD_BUTTON.y + 12, 4, 16, 3, 3);
		
		DrawUtil.drawColorButton(g, SUBTRACT_BUTTON, Color.WHITE, false);
		g.setColor(Color.BLACK);
		g.fillRoundRect(SUBTRACT_BUTTON.x + 11, (SUBTRACT_BUTTON.y + 5 + 30 / 2) - 3, 18, 6, 3, 3);
		g.setColor(Color.WHITE);
		g.fillRoundRect(SUBTRACT_BUTTON.x + 12, (SUBTRACT_BUTTON.y + 5 + 30 / 2) - 2, 16, 4, 3, 3);
		
	}

	@Override
	protected HealthGate getNewEntityInstance() {
		return new HealthGate(more, health, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			
			if (entity != null && !dragging)
				super.unfocusEntity();
		} else {
			if (GREATER_BUTTON.contains(e.getPoint())) {
				more = true;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					rectangle.setBounds((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
					super.createEntity();
				}
			} else if (LESS_BUTTON.contains(e.getPoint())) {
				more = false;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					rectangle.setBounds((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
					super.createEntity();
				}
			} else if (ADD_BUTTON.contains(e.getPoint())) {
				health += 10;
				if (health > 100 ) {
					health = 100;
				}
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					rectangle.setBounds((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
					super.createEntity();
				}
			} else if (SUBTRACT_BUTTON.contains(e.getPoint())) {
				health -= 10;
				if (health < 0 ) {
					health = 0;
				}
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					rectangle.setBounds((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());
					super.createEntity();
				}
			}
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
