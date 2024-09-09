/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import misha.editor.DrawUtil;
import misha.editor.Util;
import misha.editor.level.entity.MasterEntityEditor;
import misha.game.level.entity.Entity;

public abstract class MasterSelector<T extends Entity> {
	
	private ArrayList<Class<? extends Entity>> subclasses;
	private ArrayList<Rectangle> buttons;
	private ArrayList<Entity> drawEntities;
	
	protected int highlightIndex;
	
	protected MasterSelector(Class<T> type) {
		subclasses = new ArrayList<>();
		buttons = new ArrayList<>();
		drawEntities = new ArrayList<>();
		
		getAllSubclasses(type);
		
		createButtons();
		
		createDrawEntities();
	}
	
	protected abstract void getAllSubclasses(Class<?> type);
	
	private void createButtons() {
		for (int i = 0; i < subclasses.size(); i++) {
			buttons.add(new Rectangle(10 + i * 50, 660, 40, 40));
		}
	}
	
	private void createDrawEntities() {
		for (int i = 0; i < buttons.size(); i++) {
			Entity entity = (Entity) Util.createEntityInstance(subclasses.get(i));
			
			Rectangle rect = buttons.get(i);
			if (entity.getWidth() > 30 || entity.getHeight() > 0)
				entity.setDimensions(30, 30);
			entity.setPos(rect.x + rect.width / 2 - entity.getWidth() / 2, rect.y + rect.height / 2 - entity.getHeight() / 2);
			
			
			drawEntities.add(entity);
		}
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < buttons.size(); i++) {
			DrawUtil.drawButton(g, buttons.get(i), i == highlightIndex);
			drawEntities.get(i).draw(g);
		}
	}
	

	public MasterEntityEditor<?> getEditor(Point point) {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).contains(point)) {
				highlightIndex = i;
				
				@SuppressWarnings("unchecked")
				T entityInstance = (T) Util.createEntityInstance(subclasses.get(i));
				return new MasterEntityEditor<>(entityInstance, false);
			}
		}
		
		return null;
	}
	
}
