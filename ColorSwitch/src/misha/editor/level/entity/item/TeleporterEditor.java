/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Rectangle;

import misha.editor.level.LevelEditor;
import misha.game.level.entity.item.Teleporter;

public class TeleporterEditor extends AbstractItemEditor<Teleporter> {
	
	private static final Rectangle DO_TELEPORT_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle DO_NOT_TELEPORT_BUTTON = new Rectangle(60, 710, 40, 40);
	
	private boolean draggingPoint;
	private Point teleportPoint;
	
	public TeleporterEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public TeleporterEditor(LevelEditor levelEditor, Teleporter teleporter) {
		super(levelEditor, teleporter);
		
		if (teleporter == null)
			teleportPoint = new Point(-1, -1);
		else
			teleportPoint = new Point(teleporter.getX2(), teleporter.getY2());
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if (entity != null && teleportPoint.x != -1 && teleportPoint.y != -1) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillOval(teleportPoint.x - 5, teleportPoint.y - 5, 30, 30);
			
			g.setColor(Teleporter.PURPLE);
			g.fillOval(teleportPoint.x, teleportPoint.y, 20, 20);
		}
		
		super.drawColorButton(g, DO_TELEPORT_BUTTON, Color.MAGENTA.darker(), !teleportPoint.equals(new Point(-1, -1)));
		super.drawColorButton(g, DO_NOT_TELEPORT_BUTTON, Color.MAGENTA.darker().darker().darker(), teleportPoint.equals(new Point(-1, -1)));
	}
	
	@Override
	protected Teleporter getEntity() {
		return new Teleporter(point.x, point.y, teleportPoint.x, teleportPoint.y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			if (teleportPoint.x != -1 && teleportPoint.y != -1)
				draggingPoint = (Math.sqrt(Math.pow(e.getX() - teleportPoint.x - 10, 2) + Math.pow(e.getY() - teleportPoint.y - 10, 2)) <= 15);
			
			if (!draggingPoint) {
				super.mousePressed(e);
			 
				 if (entity != null && !dragging) {
					super.unfocusEntity();
				}
			} else {
				super.levelEditor.removeObjectFromLevel(entity);
				point = new Point((int) entity.getX(), (int) entity.getY());
				entity = new Teleporter(point.x, point.y, -1, -1);
			}
		} else {
			if (DO_TELEPORT_BUTTON.contains(e.getPoint())) {
				if (entity != null) {
					teleportPoint.setLocation((int) entity.getX(), (int) entity.getY());
					super.levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (DO_NOT_TELEPORT_BUTTON.contains(e.getPoint())) {
				if (entity != null) {
					teleportPoint.setLocation(-1, -1);
					super.levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) { // TODO use EntityEditor's pmp variable for dragging teleportPoint!
		if (e.getY() <= 600) {
			if (draggingPoint) {
				teleportPoint.setLocation(e.getX() - 10, e.getY() - 10);
			} else {
				super.mouseDragged(e);
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getY() <= 600) {
			if (draggingPoint) {
				teleportPoint = new Point(Math.round(teleportPoint.x / 10) * 10, Math.round(teleportPoint.y / 10) * 10);
				draggingPoint = false;
				super.createEntity();
			} else {
				super.mouseReleased(e);
			}
		}
	}
	
}
