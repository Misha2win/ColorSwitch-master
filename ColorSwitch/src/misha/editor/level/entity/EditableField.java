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
	
	enum EditableFieldType {
		POINT,
		ATTRIBUTE
	}
	
}
