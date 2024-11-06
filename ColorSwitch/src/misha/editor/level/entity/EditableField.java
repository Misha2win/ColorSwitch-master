package misha.editor.level.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares that a varaible should be editable by an EntityEditor of that type
 * 
 * @author Misha Malinouski
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EditableField {

	EditableFieldType[] value() default { EditableFieldType.ATTRIBUTE };
	int[] range() default { 0, 0, 0 };
	int radius() default 10;
	boolean visible() default false;
	
	enum EditableFieldType {
		/**
		 * Used if the field should be treated as a coordinate point
		 */
		POINT,
		/**
		 * Used if the field can have any value
		 */
		ATTRIBUTE,
		/**
		 * Used if the field can have a range of values
		 */
		RANGE,
		/**
		 * Used if the field can only have a limited set of values
		 */
		LIMITED
	}
	
}
