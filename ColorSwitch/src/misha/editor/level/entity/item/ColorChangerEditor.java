/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.ColorChanger;

public class ColorChangerEditor extends AbstractItemEditor<ColorChanger> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 610, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 610, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 610, 40, 40);
	private static final Rectangle BLACK_BUTTON = new Rectangle(360, 610, 40, 40);
	
	public ColorChangerEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public ColorChangerEditor(LevelEditor levelEditor, ColorChanger colorChanger) {
		super(levelEditor, colorChanger);
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
		drawColorButton(g, BLACK_BUTTON, new Color(0, 0, 0), new Color(50, 50, 50));
	}
	
	@Override
	protected ColorChanger getEntity() {
		return new ColorChanger(color, point.x, point.y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
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
			}
		}
		
	}
	
}