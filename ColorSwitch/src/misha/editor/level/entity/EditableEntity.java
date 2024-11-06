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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EditableEntity {

	EditableEntityType[] value();
	
	enum EditableEntityType {
		/**
		 * Used if an entity can have variable dimensions
		 */
		PLATFORMS,
		/**
		 * Used if an entity does not have variable dimensions
		 */
		POINTS,
		/**
		 * Used if an entity can have variable colors
		 */
		COLORS,
		/**
		 * Used if an entity has editable fields
		 */
		FIELDS
	}
	
}
