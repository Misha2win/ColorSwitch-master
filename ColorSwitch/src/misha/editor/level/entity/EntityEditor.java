/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import misha.editor.Editor;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.ColorChanger;

public abstract class EntityEditor<T extends Entity> implements KeyListener, MouseListener, MouseMotionListener {
	
	protected LevelEditor levelEditor;
	protected T entity;
	
	protected CSColor color;
	
	protected boolean dragging;
	protected Point pmp;
	
	protected boolean ignoreInput;
	
	public EntityEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public EntityEditor(LevelEditor levelEditor, T entity) {
		this.levelEditor = levelEditor;
		this.entity = entity;
		this.pmp = new Point();
		
		if (entity != null) {
			this.color = entity.getColor();
		} else {
			this.color = CSColor.BLACK;
		}
	}
	
	public T getStoredEntity() {
		return entity;
	}
	
	protected abstract T getNewEntityInstance();
	
	protected void createEntity() { // TODO maybe only this class should have access to LevelEditor
		entity = getNewEntityInstance();
		levelEditor.addObjectToLevel(entity);
	}
	
	protected void setColor(CSColor color) {
		if (entity != null)
			entity.setColor(color);
		
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, Editor.WIDTH, 600);
	}
	
	protected void beginDraggingEntity(Point initial) {
		dragging = true;
		
		pmp.x = initial.x;
		pmp.y = initial.y;
	}
	
	protected void dragEntity(Point mouse) {
		entity.translate(mouse.x - pmp.x, mouse.y - pmp.y);
		
		pmp.x = mouse.x;
		pmp.y = mouse.y;
	}
	
	protected void dropEntity() {
		entity.setPos(Math.round(entity.getX() / 10) * 10, Math.round(entity.getY() / 10) * 10);
		
		dragging = false;
	}
	
	protected void unfocusEntity() {
		entity = null;
		ignoreInput = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			levelEditor.removeObjectFromLevel(entity);
			entity = null;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
}
