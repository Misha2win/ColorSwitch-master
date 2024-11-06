/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Graphics2D;

public abstract class AbstractSelector<T> {
	
	protected static final int NOTHING = -1;
	
	protected int highlightOption;
	
	public abstract void draw(Graphics2D g);
	
	public void unfocus() {
		highlightOption = NOTHING;
	}
	
}
