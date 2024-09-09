/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity;

import java.lang.reflect.Field;

public class FieldEditor {
	
	private enum FieldEditorType {
		SETTER,
		INCREMENTER,
		DECREMENTER
	}
	
	private final Field field;
	private final Object object;
	
	private final FieldEditorType editorType;
	private final int changeAmount;
	
	public FieldEditor(Field field, Object object) {
		this(field, object, FieldEditorType.SETTER, 0);
	}
	
	private FieldEditor(Field field, Object object, FieldEditorType editorType, int changeAmount) {
		this.field = field;
		this.object = object;
		this.editorType = editorType;
		this.changeAmount = changeAmount;
	}
	
	public static FieldEditor getIncrementer(Field field, int changeAmount) {
		return new FieldEditor(field, null, FieldEditorType.INCREMENTER, changeAmount);
	}
	
	public static FieldEditor getDecrementer(Field field, int changeAmount) {
		return new FieldEditor(field, null, FieldEditorType.DECREMENTER, changeAmount);
	}
	
	public Class<?> getFieldType() {
		return field.getType();
	}
	
	public Object getSetterObject() {
		return object;
	}
	
	public <T> Object getFieldValue(T entity) throws IllegalArgumentException, IllegalAccessException {
		return field.get(entity);
	}
	
	public <T> void edit(T entity) throws IllegalArgumentException, IllegalAccessException {
		if (editorType == FieldEditorType.SETTER)
			field.set(entity, object);
		else if (editorType == FieldEditorType.INCREMENTER)
			field.set(entity, (int) field.get(entity) + changeAmount);
		else if (editorType == FieldEditorType.DECREMENTER)
			field.set(entity, (int) field.get(entity) - changeAmount);
	}
	
}
