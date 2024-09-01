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

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Mirror;

public class MirrorEditor extends AbstractItemEditor<Mirror> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 710, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 710, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 710, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 710, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 710, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 710, 40, 40);
	private static final Rectangle BLACK_BUTTON = new Rectangle(360, 710, 40, 40);
	private static final Rectangle GRAY_BUTTON = new Rectangle(410, 710, 40, 40);
	
	private static final Rectangle PERSIST_ON_BUTTON = new Rectangle(10, 760, 40, 40);
	private static final Rectangle PERSIST_OFF_BUTTON = new Rectangle(60, 760, 40, 40);
	
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
		
		DrawUtil.drawColorButton(g, RED_BUTTON, Color.RED, color.equals(CSColor.RED));
		DrawUtil.drawColorButton(g, GREEN_BUTTON, Color.GREEN, color.equals(CSColor.GREEN));
		DrawUtil.drawColorButton(g, BLUE_BUTTON, Color.BLUE, color.equals(CSColor.BLUE));
		DrawUtil.drawColorButton(g, YELLOW_BUTTON, Color.YELLOW, color.equals(CSColor.YELLOW));
		DrawUtil.drawColorButton(g, MAGENTA_BUTTON, Color.MAGENTA, color.equals(CSColor.MAGENTA));
		DrawUtil.drawColorButton(g, CYAN_BUTTON, Color.CYAN, color.equals(CSColor.CYAN));
		DrawUtil.drawColorButton(g, WHITE_BUTTON, Color.WHITE, color.equals(CSColor.WHITE));
		DrawUtil.drawColorButton(g, BLACK_BUTTON, new Color(0, 0, 0), new Color(50, 50, 50), color.equals(CSColor.BLACK));
		DrawUtil.drawColorButton(g, GRAY_BUTTON, Color.GRAY, color.equals(CSColor.GRAY));
		
		DrawUtil.drawColorButton(g, PERSIST_ON_BUTTON, Color.YELLOW, persist);
		DrawUtil.drawColorButton(g, PERSIST_OFF_BUTTON, Color.GRAY.darker(), !persist);
	}
	
	@Override
	protected Mirror getNewEntityInstance() {
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
