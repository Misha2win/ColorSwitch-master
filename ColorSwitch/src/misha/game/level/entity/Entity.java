/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import misha.annotation.EnforceAnnotationInImplementedSubclasses;
import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EntityEditor;
import misha.game.level.Level;

@EnforceAnnotationInImplementedSubclasses(EditableEntity.class)
public abstract class Entity {
	
	protected Level level;

	@EditableField
	protected CSColor color;
	protected float x, y;
	protected int width, height;
	protected float xVelocity, yVelocity;
	
	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void onCollision(Entity entity);
	
	public abstract EntityEditor<?> getEntityEditor(LevelEditor levelEditor);
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract Entity clone();
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public float getXVelocity() {
		return xVelocity;
	}
	
	public float getYVelocity() {
		return yVelocity;
	}
	
	public void setXVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void accelerate(float x, float y) {
		xVelocity += x;
		yVelocity += y;
	}
	
	public void accelerateX(float x) {
		xVelocity += x;
	}
	
	public void accelerateY(float y) {
		yVelocity += y;
	}
	
	public void setVelocity(int x, int y) {
		xVelocity = x;
		yVelocity = y;
	}
	
	public void setColor(CSColor color) {
		this.color = color;
	}
	
	public CSColor getColor() {
		return color;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Rectangle2D getRect() {
		return new Rectangle2D.Float(x, y, width, height);
	}
	
}
