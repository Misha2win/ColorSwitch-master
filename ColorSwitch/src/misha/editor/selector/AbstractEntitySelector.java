/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Point;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.game.level.entity.Entity;

public abstract class AbstractEntitySelector<T extends Entity> extends AbstractSelector<T> {
	
	public abstract EntityEditor<? extends T> getEditor(LevelEditor levelEditor, T[] entities, Point point);
	
}
