/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;

import misha.editor.DrawUtil;
import misha.editor.Util;
import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.MasterEntityEditor;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.Entity;

public class MasterEntitySelector<T extends Entity> extends AbstractSelector<T> {
	
	private static final ArrayList<MasterEntitySelector<?>> SELECTORS = new ArrayList<>();
	
	private ArrayList<Class<T>> subclasses;
	private ArrayList<Rectangle> buttons;
	private ArrayList<Entity> drawEntities;
	
	private MasterEntitySelector(Class<T> type) {
		subclasses = new ArrayList<>();
		buttons = new ArrayList<>();
		drawEntities = new ArrayList<>();
		highlightOption = NOTHING;
		
		getAllSubclasses(type);
		
		createButtons();
		
		createDrawEntities();
		
		SELECTORS.add(this);
	}
	
	@SuppressWarnings("unchecked")
	private void getAllSubclasses(Class<T> type) {
		for (Class<? extends Entity> clazz : Entity.getAllSubclasses()) {
			if (type.isAssignableFrom(clazz)) {
				subclasses.add((Class<T>) clazz);
			}
		}
	}
	
	private void createButtons() {
		for (int i = 0; i < subclasses.size(); i++) {
			buttons.add(new Rectangle(10 + i * 50, 660, 40, 40));
		}
	}
	
	private void createDrawEntities() {
		for (int i = 0; i < buttons.size(); i++) {
			Entity entity = (Entity) Util.createEntityInstance(subclasses.get(i));
			
			boolean editSize = false;
			if (entity.getClass().isAnnotationPresent(EditableEntity.class)) {
	        	for (EditableEntityType type : entity.getClass().getAnnotation(EditableEntity.class).value()) {
	    			if (type == EditableEntityType.PLATFORMS) {
	    				editSize = true;
	    				break;
	    			}
	    		}
	        }
			
			Rectangle rect = buttons.get(i);
			if (editSize || entity.getWidth() > 30 || entity.getHeight() > 30)
				entity.setDimensions(30, 30);
			entity.setPos(rect.x + rect.width / 2 - entity.getWidth() / 2, rect.y + rect.height / 2 - entity.getHeight() / 2);
			
			drawEntities.add(entity);
		}
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < buttons.size(); i++) {
			DrawUtil.drawButton(g, buttons.get(i), i == highlightOption);
			drawEntities.get(i).draw(g);
		}
	}
	

	public MasterEntityEditor<T> getEditor() {
		return new MasterEntityEditor<>(Util.createEntityInstance(subclasses.get(highlightOption)), false);
	}
	
	public boolean mousePressed(MouseEvent e) {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).contains(e.getPoint())) {
				highlightOption = i;
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Entity> MasterEntitySelector<T> getSelector(Class<T> type) {
		for (MasterEntitySelector<?> selector : SELECTORS) {
			if ((((ParameterizedType) selector.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).equals(type)) {
				selector.highlightOption = NOTHING;
				return (MasterEntitySelector<T>) selector;
		    }
		}
		
		MasterEntitySelector<T> selector = new MasterEntitySelector<>(type);
		SELECTORS.add(selector);
		return selector;
	}
	
}
