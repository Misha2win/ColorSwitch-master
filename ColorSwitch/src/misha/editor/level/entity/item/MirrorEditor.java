/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Mirror;

public class MirrorEditor extends AbstractItemEditor<Mirror> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 610, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 610, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 610, 40, 40);
	private static final Rectangle BLACK_BUTTON = new Rectangle(360, 610, 40, 40);
	private static final Rectangle GRAY_BUTTON = new Rectangle(410, 610, 40, 40);
	
	private static final Rectangle PERSIST_ON_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle PERSIST_OFF_BUTTON = new Rectangle(60, 660, 40, 40);
	
	private boolean persist;
	
	public MirrorEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public MirrorEditor(LevelEditor levelEditor, Mirror mirror) {
		super(levelEditor, mirror);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		super.drawColorButton(g, PERSIST_ON_BUTTON, Color.YELLOW);
		super.drawColorButton(g, PERSIST_OFF_BUTTON, Color.GRAY.darker());
		
		super.drawColorButton(g, RED_BUTTON, Color.RED);
		super.drawColorButton(g, GREEN_BUTTON, Color.GREEN);
		super.drawColorButton(g, BLUE_BUTTON, Color.BLUE);
		super.drawColorButton(g, YELLOW_BUTTON, Color.YELLOW);
		super.drawColorButton(g, MAGENTA_BUTTON, Color.MAGENTA);
		super.drawColorButton(g, CYAN_BUTTON, Color.CYAN);
		super.drawColorButton(g, WHITE_BUTTON, Color.WHITE);
		super.drawColorButton(g, BLACK_BUTTON, new Color(0, 0, 0), new Color(50, 50, 50));
		super.drawColorButton(g, GRAY_BUTTON, Color.GRAY);
		
	}
	
	@Override
	protected Mirror getEntity() {
		return new Mirror(point.x, point.y, color, persist);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		} else {
			if (PERSIST_ON_BUTTON.contains(e.getPoint())) {
				persist = true;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (PERSIST_OFF_BUTTON.contains(e.getPoint())) {
				persist = false;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (RED_BUTTON.contains(e.getPoint())) {
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
			} else if (GRAY_BUTTON.contains(e.getPoint())) {
				color = CSColor.GRAY;
				if (entity != null)
					entity.setColor(color);
			}
		}
	}
	
}
