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
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import misha.editor.Editor;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.editor.level.entity.EditableField.EditableFieldType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.util.DrawUtil;
import misha.util.Util;

public class MasterEntityEditor<T extends Entity> implements KeyListener, MouseListener, MouseMotionListener {
	
	private EditableEntity entityAnnotation;
	private ArrayList<Field> editableFields;
	
	private boolean inLevel;
	
	private T entity;
	private boolean ready;
	
	private ArrayList<Rectangle> buttons;
	private ArrayList<FieldEditor> fieldEditors;
	private ArrayList<Entity> drawingEntities;
	
	// Variables for dragging entities
	private Point pmp;
	private boolean dragging;
	
	// Variables for creating/editing Rectangular entities
	private Rectangle rectangle;
	private boolean resizing;
	
	// Variables for moving point varaibles in Entity class
	private boolean movingPoint;
	private Field[] movingPointFields;
	
	private boolean ignoreInput;
	
	public MasterEntityEditor(T entity, boolean ready) {
		this.entity = entity;
		this.ready = ready;
		this.rectangle = new Rectangle();
		this.inLevel = ready;
		
		if (entity == null) {
			throw new IllegalArgumentException("The provided entity must not be null!");
		}
		
		// The parameterized class should not be abstract
        if (Modifier.isAbstract(entity.getClass().getModifiers())) {
        	throw new IllegalArgumentException("The generic superclass " + entity.getClass().getName() + " is abstract!");
        }
        
        // The parameterized class should have a EditableEntity annotation
        if (entity.getClass().isAnnotationPresent(EditableEntity.class)) {
        	entityAnnotation = entity.getClass().getAnnotation(EditableEntity.class);
        } else {
        	throw new IllegalStateException(entity.getClass().getName() + " needs " + EditableEntity.class.getName() + " annotation!");
        }
		
        getEditableFields();
		
		createButtons();
		
		createDrawingEntities();
	}
	
	private void getEditableFields() {
		editableFields = new ArrayList<>();
		
		Class<?> clazz = this.entity.getClass();
		
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(EditableField.class)) {
					field.setAccessible(true);
					editableFields.add(field);
				}
			}
			
			clazz = clazz.getSuperclass();
		}
	}
	
	private void createButtons() {
		buttons = new ArrayList<>();
		fieldEditors = new ArrayList<>();
		
		for (EditableEntityType editableType : entityAnnotation.value()) {
			if (editableType == EditableEntityType.COLORS) {
				Field[] colorFields = CSColor.class.getFields();
				
				int y = 710;
				if (buttons.size() > 0) {
					y = buttons.get(buttons.size() - 1).y  + 50;
				}
				
				int counter = 0;
				for (Field field : colorFields) {
					if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
						try {
							buttons.add(new Rectangle(10 + counter * 50, y, 40, 40));
							fieldEditors.add(new FieldEditor(editableFields.get(editableFields.size() - 1), field.get(null)));
							counter++;
						} catch (Exception e) {
							buttons.remove(buttons.size() - 1);
							System.err.println("Unable to access field " + field.getName());
							e.printStackTrace();
						}
					}
				}
			} else if (editableType == EditableEntityType.FIELDS) {
				loop:
				for (int i = 0; i < editableFields.size() - 1; i++) { // Don't check last index since it is Entity#color
					Field field = editableFields.get(i);
					
					boolean isRangeField = false;
					boolean isLimitedField = false;
					
					EditableField annotation = field.getAnnotation(EditableField.class);
					for (EditableFieldType type : annotation.value()) {
						if (type == EditableFieldType.POINT) {
							continue loop;
						} else if (type == EditableFieldType.RANGE) {
							isRangeField = true;
						} else if (type == EditableFieldType.LIMITED) {
							isLimitedField = true;
						}
					}
					
					if (isRangeField && isLimitedField) {
						throw new IllegalStateException("A field cannot be both ranged and limited!");
					}
					
					int y = buttons.size() > 0 ? buttons.get(buttons.size() - 1).y + 50 : 710;
					if (isLimitedField) {
						for (int j = 0, k = 10; j < annotation.range().length; j++, k += 50) {
							buttons.add(new Rectangle(k, y, 40, 40));
						}
					} else {
						buttons.add(new Rectangle(10, y, 40, 40));
						buttons.add(new Rectangle(60, y, 40, 40));
					}
					
					if (field.getType().equals(int.class)) {
						if (isRangeField) {
							try {
								fieldEditors.add(FieldEditor.getRangedDecrementer(field, annotation.range()[2], annotation.range()[0]));
								fieldEditors.add(FieldEditor.getRangedIncrementer(field, annotation.range()[2], annotation.range()[1]));
							} catch (ArrayIndexOutOfBoundsException e) {
								System.err.println("The range section of the EditableField annotation is incomplete!");
								throw e;
							}
						} else if (isLimitedField) {
							for (int j = 0; j < annotation.range().length; j++) {
								fieldEditors.add(new FieldEditor(field, annotation.range()[j]));
							}
						} else {
							fieldEditors.add(FieldEditor.getDecrementer(field, 10));
							fieldEditors.add(FieldEditor.getIncrementer(field, 10));
						}
					} else if (field.getType().equals(boolean.class)) {
						fieldEditors.add(new FieldEditor(field, true));
						fieldEditors.add(new FieldEditor(field, false));
					} else {
						throw new IllegalArgumentException("Unsupported EditableField type " + field.getType().getName());
					}
				}
			}
		}
	}
	
	private void createDrawingEntities() {
		drawingEntities = new ArrayList<>();
		
		for(int i = 0; i < fieldEditors.size(); i++) {
			try {
				Entity drawingEntity = entity.clone();
				fieldEditors.get(i).edit(drawingEntity);
				
				if (shouldResizeEntityClone(drawingEntity))
					drawingEntity.setDimensions(30, 30);

				Rectangle rect = buttons.get(i);
				drawingEntity.setPos(rect.x + rect.width / 2 - drawingEntity.getWidth() / 2, rect.y + rect.height / 2 - drawingEntity.getHeight() / 2);
				
				drawingEntities.add(drawingEntity);
			} catch (Exception e) {
				System.err.println("Could not create drawingEntity for type " + fieldEditors.get(i).getFieldType().getName());
				e.printStackTrace();
			}
		}
	}
	
	private boolean shouldResizeEntityClone(Entity drawingEntity) {
		boolean isPlatformEditType = this.hasAnnotation(EditableEntityType.PLATFORMS);
		if (isPlatformEditType)
			return true;
		
		boolean isTooBig = drawingEntity.getWidth() > 30 || drawingEntity.getHeight() > 30;
		return isTooBig;
	}
	
	public T getEntity() {
		return entity;
	}
	
	public boolean getReady() {
		return ready;
	}
	
	public boolean getInLevel() {
		return inLevel;
	}
	
	public void addedToLevel() {
		inLevel = true;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, Editor.WIDTH, 600);
		
		// Draw the entity if it is ready to be drawn
		if (ready) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect((int) entity.getX() - 5, (int) entity.getY() - 5, entity.getWidth() + 10, entity.getHeight() + 10);
			entity.draw(g);
			
			if (hasAnnotation(EditableEntityType.PLATFORMS)) {
				g.setColor(Color.RED);
				g.fillOval((int) entity.getX() + entity.getWidth() - 5, (int) entity.getY() + entity.getHeight() - 5, 10, 10);
			}
			
			Field[] pointFields = getPointFields();
			for (int i = 0; i < pointFields.length; i += 2) {
				try {
					EditableField annotation = pointFields[i].getAnnotation(EditableField.class);
					g.setColor(Color.WHITE);
					int centerX = (int) pointFields[i].getFloat(entity);
					int centerY = (int) pointFields[i + 1].getFloat(entity);
					if (!annotation.visible()) {
						g.drawOval(centerX - annotation.radius() / 2, centerY - annotation.radius() / 2, annotation.radius(), annotation.radius());
						g.drawLine(centerX, centerY, (int) entity.getX(), (int) entity.getY());	
					} else {
						g.drawLine(centerX + annotation.radius() / 2, centerY + annotation.radius() / 2, (int) entity.getX(), (int) entity.getY());	
					}
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (dragging) {
				Color c = entity.getColor().getGraphicsColor();
				g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
				g.fill((rectangle.width <= 0 || rectangle.height <= 0) ? getNormalRect(rectangle) : rectangle);
			}
		}

		// Draw editable property buttons
		for (int i = 0; i < fieldEditors.size(); i++) { // FIXME There can be more buttons than editors!
			boolean shouldHighlight = false;
			try {
				shouldHighlight = fieldEditors.get(i).getFieldValue(entity).equals(fieldEditors.get(i).getSetterObject());
			} catch (Exception e) {
				System.err.println("Could not determine shouldHighlight for " + fieldEditors.get(i).getFieldType().getName());
				e.printStackTrace();
			}
			
			DrawUtil.drawButton(g, buttons.get(i), shouldHighlight);
			drawingEntities.get(i).draw(g);
		}
	}
	
	public Rectangle getNormalRect(Rectangle rectangle) {
		int rx = rectangle.x + (rectangle.width < 0 ? rectangle.width : 0);
		int ry = rectangle.y + (rectangle.height < 0 ? rectangle.height : 0);
		int rw = (rectangle.width <= 0 ? -rectangle.width + 10 : rectangle.width);
		int rh = (rectangle.height <= 0 ? -rectangle.height + 10 : rectangle.height);
		return new Rectangle(rx, ry, rw, rh);
	}
	
	public boolean hasAnnotation(EditableEntityType wantedType) {
		for (EditableEntityType type : entityAnnotation.value()) {
			if (type == wantedType) {
				return true;
			}
		}
		
		return false;
	}
	
	public Field[] getPointFields() {
		ArrayList<Field> points = new ArrayList<>();
		if (hasAnnotation(EditableEntityType.FIELDS)) {
			for (Field field : editableFields) {
				EditableField annotation = field.getAnnotation(EditableField.class);
				for (EditableFieldType type : annotation.value()) {
					if (type == EditableFieldType.POINT) {
						if (points.size() <= 0) {
							points.add(field);
						} else {
							for (int i = 0; i < points.size(); i++) { // TODO When this becomes a point refactor this
								int mismatchIndex = Util.sharesAllButOne(field.getName(), points.get(i).getName());
								if (mismatchIndex != -1) {
									if (field.getName().substring(mismatchIndex, mismatchIndex + 1).equalsIgnoreCase("x")) {
										if (i + 1 < points.size())
											points.set(i + 1, points.set(i, field));
										else
											points.add(points.set(i, field));
									} else {
										if (i + 1 < points.size())
											points.set(i + 1, field);
										else
											points.add(field);
									}
								}
							}
						}
					}
				}
			}
		}
		if (points.size() % 2 != 0) {
			throw new IllegalStateException("There are not enough annotated point varaibles in " + entity.getClass());
		}
		
		return points.toArray(new Field[points.size()]);
	}
	
	public boolean isDraggingVariablePoint(Point p) {
		Field[] points = getPointFields();
		for (int i = 0; i < points.length; i += 2) {
			try {
				EditableField annotation = points[i].getAnnotation(EditableField.class);
				if (p.distance((int) points[i].getFloat(entity), (int) points[i + 1].getFloat(entity)) < annotation.radius()) {
					movingPointFields = new Field[] { points[i], points[i + 1] };
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getPoint().y < 600) {
			if (ready) {
				if (e.getPoint().distance((int) entity.getX() + entity.getWidth(), (int) entity.getY() + entity.getHeight()) < 10) {
					if (hasAnnotation(EditableEntityType.PLATFORMS)) {
						pmp = e.getPoint();
						resizing = true;
					}
				} else if (isDraggingVariablePoint(e.getPoint())){
					pmp = e.getPoint();
					movingPoint = true;
				} else if (entity.getRect().contains(e.getPoint())) {
					pmp = e.getPoint();
					dragging = true;
				} else {
					ready = false;
					entity = (T) Util.createEntityInstance(entity.getClass());
					ignoreInput = true;
					inLevel = false;
				}
			} else {
				if (hasAnnotation(EditableEntityType.PLATFORMS)) {
					rectangle.x = Math.round((e.getPoint().x) / 10) * 10;
					rectangle.y = Math.round((e.getPoint().y) / 10) * 10;
					dragging = true;
				} else if (hasAnnotation(EditableEntityType.POINTS)) {
					ready = true;
					entity.setPos(Math.round(e.getPoint().x / 10) * 10, Math.round(e.getPoint().y / 10) * 10);
				} else {
					throw new IllegalStateException("Entity class " + entity.getClass().getName() + " doesn't specify EditableEntityType!");
				}
			}
		} else {
			for (int i = 0; i < buttons.size(); i++) {
				if (buttons.get(i).contains(e.getPoint())) {
					try {
						fieldEditors.get(i).edit(entity);
						createDrawingEntities();
					} catch (Exception ex) {
						System.err.println("Unable to edit " + fieldEditors.get(i).getFieldType() + " of " + entity.getClass().getName());
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (ready) {
			Point cmp = e.getPoint();
			if (dragging) {
				entity.translate(cmp.x - pmp.x, cmp.y - pmp.y);
			} else if (resizing) {
				entity.setDimensions(entity.getWidth() + cmp.x - pmp.x, entity.getHeight() + cmp.y - pmp.y);
			} else if (movingPoint) {
				try {
					movingPointFields[0].set(entity, (int) movingPointFields[0].getFloat(entity) + cmp.x - pmp.x);
					movingPointFields[1].set(entity, (int) movingPointFields[1].getFloat(entity) + cmp.y - pmp.y);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			pmp = cmp;
		} else {
			if (dragging) {
				if (hasAnnotation(EditableEntityType.PLATFORMS)) {
					rectangle.width = Math.round((e.getPoint().x - rectangle.x) / 10) * 10;
					rectangle.height = Math.round((e.getPoint().y - rectangle.y) / 10) * 10;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (ignoreInput) {
			ignoreInput = false;
			return;
		}
			
		if (ready) {
			if (dragging) {
				entity.setPos(Math.round(entity.getX() / 10) * 10, Math.round(entity.getY() / 10) * 10);
			} else if (resizing) {
				entity.setDimensions(Math.round(entity.getWidth() / 10) * 10, Math.round(entity.getHeight() / 10) * 10);
				
				if (entity.getWidth() <= 0) {
					entity.setDimensions(10, entity.getHeight());
				}
				
				if (entity.getHeight() <= 0) {
					entity.setDimensions(entity.getWidth(), 10);
				}
			} else if (movingPoint) {
				try {
					movingPointFields[0].set(entity, Math.round((int) movingPointFields[0].getFloat(entity) / 10) * 10);
					movingPointFields[1].set(entity, Math.round((int) movingPointFields[1].getFloat(entity) / 10) * 10);
					movingPointFields[0] = null;
					movingPointFields[1] = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else {
			if (dragging) {
				rectangle = getNormalRect(rectangle);
				entity.setPos(rectangle.x, rectangle.y);
				entity.setDimensions(rectangle.width, rectangle.height);
				rectangle.x = 0;
				rectangle.y = 0;
				rectangle.width = 0;
				rectangle.height = 0;
				ready = true;
			}
		}
		
		movingPoint = false;
		resizing = false;
		dragging = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
