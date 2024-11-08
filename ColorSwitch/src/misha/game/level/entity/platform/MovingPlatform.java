/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.platform;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EditableField.EditableFieldType;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.Updatable;

@EditableEntity({ EditableEntityType.PLATFORMS, EditableEntityType.COLORS, EditableEntityType.FIELDS })
public class MovingPlatform extends Platform implements Updatable {
	
	static { Entity.addSubclass(MovingPlatform.class); }
	
	private boolean movingToPoint2;
	
	private final float speed;
	
	private boolean isActive;
	
	private final float x1, y1;
	
	@EditableField({ EditableFieldType.POINT })
	private final float x2, y2;
	
	public MovingPlatform(CSColor c, int x, int y, int x2, int y2, int w, int h) {
		super(c, x, y, w, h);
		
		movingToPoint2 = true;
		isActive = true;
		
		this.x1 = x;
		this.y1 = y;
		
		this.x2 = x2;
		this.y2 = y2;
		
		this.speed = .5f;
	}
	
	public void setMovingToPoint2(boolean newbool) {
		movingToPoint2 = newbool;
	}
	
	public boolean getMovingToPoint2() {
		return movingToPoint2;
	}
	
	public float getX2() {
		return x2;
	}
	
	public float getY2() {
		return y2;
	}
	
	public float getX1() {
		return x1;
	}
	
	public float getY1() {
		return y1;
	}
	
	public void setActive(boolean state) {
		isActive = state;
	}
	
	private float getTargetX() {
		return (movingToPoint2 ? x2 : x1);
	}
	
	private float getTargetY() {
		return (movingToPoint2 ? y2 : y1);
	}
	
	@Override
	public void update() {
		if (level == null) {
			//throw new IllegalStateException("update() was envoked but this Updatable is not in a level!");
		}
		
		if (isActive) {
			double angle = Math.atan2(getTargetY() - y, getTargetX() - x);
			xVelocity = (float) (speed * Math.cos(angle));
			yVelocity = (float) (speed * Math.sin(angle));
			
			if (Math.abs(getTargetX() - x) < 0.1 && Math.abs(getTargetY() - y) < 0.1)
				movingToPoint2 = !movingToPoint2;
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s %s %s %s", CSColor.getStringFromColor(color), (int) x, (int) y, (int) x2, (int) y2, width, height); 
	}
	
	@Override
	public Entity clone() {
		return new MovingPlatform(color, (int) x, (int) y, (int) x2, (int) y2, width, height);
	}
	
}
