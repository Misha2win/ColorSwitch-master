/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.platform.Platform;

public class PlatformEditor extends AbstractPlatformEditor<Platform> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 610, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 610, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 610, 40, 40);
	
	public PlatformEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public PlatformEditor(LevelEditor levelEditor, Platform platform) {
		super(levelEditor, platform);
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		drawColorButton(g, RED_BUTTON, Color.RED);
		drawColorButton(g, GREEN_BUTTON, Color.GREEN);
		drawColorButton(g, BLUE_BUTTON, Color.BLUE);
		drawColorButton(g, YELLOW_BUTTON, Color.YELLOW);
		drawColorButton(g, MAGENTA_BUTTON, Color.MAGENTA);
		drawColorButton(g, CYAN_BUTTON, Color.CYAN);
		drawColorButton(g, WHITE_BUTTON, Color.WHITE);
	}

	@Override
	protected Platform getEntity() {
		return new Platform(color, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			
			if (entity != null && !dragging)
				super.unfocusEntity();
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
