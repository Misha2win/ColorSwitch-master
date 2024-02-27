/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.ColorMixer;

public class ColorMixerEditor extends AbstractItemEditor<ColorMixer> {
	
	private static final Rectangle RED_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle GREEN_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle BLUE_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle YELLOW_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle MAGENTA_BUTTON = new Rectangle(210, 610, 40, 40);
	private static final Rectangle CYAN_BUTTON = new Rectangle(260, 610, 40, 40);
	private static final Rectangle WHITE_BUTTON = new Rectangle(310, 610, 40, 40);
	private static final Rectangle ADD_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle SUBTRACT_BUTTON = new Rectangle(60, 660, 40, 40);
	
	private boolean add;
	
	public ColorMixerEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public ColorMixerEditor(LevelEditor levelEditor, ColorMixer colorMixer) {
		super(levelEditor, colorMixer);
		
		if (colorMixer != null)
			add = colorMixer.getAdd();
		else
			add = true;
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
		
		g.setStroke(new BasicStroke(1));
		
		drawColorButton(g, ADD_BUTTON, Color.WHITE);
		g.setColor(Color.BLACK);
		g.fillRoundRect(ADD_BUTTON.x + 11, ADD_BUTTON.y + 17, 18, 6, 3, 3);
		g.fillRoundRect(ADD_BUTTON.x + 17, ADD_BUTTON.y + 11, 6, 18, 3, 3);
		g.setColor(Color.WHITE);
		g.fillRoundRect(ADD_BUTTON.x + 12, ADD_BUTTON.y + 18, 16, 4, 3, 3);
		g.fillRoundRect(ADD_BUTTON.x + 18, ADD_BUTTON.y + 12, 4, 16, 3, 3);
		
		
		drawColorButton(g, SUBTRACT_BUTTON, Color.GRAY);
		g.setColor(Color.BLACK);
		g.fillRoundRect(SUBTRACT_BUTTON.x + 11, SUBTRACT_BUTTON.y + 17, 18, 6, 3, 3);
		g.setColor(Color.WHITE);
		g.fillRoundRect(SUBTRACT_BUTTON.x + 12, SUBTRACT_BUTTON.y + 18, 16, 4, 3, 3);
	}
	
	@Override
	protected ColorMixer getEntity() {
		return new ColorMixer(color, point.x, point.y, add);
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
			} else if (ADD_BUTTON.contains(e.getPoint())) {
				add = true;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (SUBTRACT_BUTTON.contains(e.getPoint())) {
				add = false;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			}
		}
	}
	
}