/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.point;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.point.SpawnPoint;

public class SpawnPointEditor extends AbstractPointEditor<SpawnPoint> {
	
	private static final Rectangle ACTIVE_ON_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle ACTIVE_OFF_BUTTON = new Rectangle(60, 710, 40, 40);
	private static final Rectangle OBTAINABLE_ON_BUTTON = new Rectangle(10, 760, 40, 40);
	private static final Rectangle OBTAINABLE_OFF_BUTTON = new Rectangle(60, 760, 40, 40);
	
	private boolean isActive;
	private boolean isObtainable;
	
	public SpawnPointEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public SpawnPointEditor(LevelEditor levelEditor, SpawnPoint spawnPoint) {
		super(levelEditor, spawnPoint);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		DrawUtil.drawColorButton(g, ACTIVE_ON_BUTTON, Color.RED, isActive);
		DrawUtil.drawColorButton(g, ACTIVE_OFF_BUTTON, Color.RED.darker().darker(), !isActive);
		DrawUtil.drawColorButton(g, OBTAINABLE_ON_BUTTON, Color.GREEN, isObtainable);
		DrawUtil.drawColorButton(g, OBTAINABLE_OFF_BUTTON, Color.RED, !isObtainable);
	}
	
	@Override
	protected SpawnPoint getEntity() {
		return new SpawnPoint(point.x, point.y, isActive, isObtainable);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		} else {
			if (ACTIVE_ON_BUTTON.contains(e.getPoint())) {
				isActive = true;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (ACTIVE_OFF_BUTTON.contains(e.getPoint())) {
				isActive = false;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (OBTAINABLE_ON_BUTTON.contains(e.getPoint())) {
				isObtainable = true;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (OBTAINABLE_OFF_BUTTON.contains(e.getPoint())) {
				isObtainable = false;
				if (entity != null) {
					point = new Point((int) entity.getX(), (int) entity.getY());
					levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			}
		}
	}
	
}
