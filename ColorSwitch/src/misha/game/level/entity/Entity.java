/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import misha.annotation.EnforceAnnotationInImplementedSubclasses;
import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.game.level.Level;
import misha.game.level.entity.player.Player;

@EnforceAnnotationInImplementedSubclasses(EditableEntity.class)
public abstract class Entity {
	
	private static LinkedList<Class<? extends Entity>> SUBCLASSES; // Keep track of Entity subclasses
	
	static {
		SUBCLASSES = new LinkedList<>();
	}
	
	protected Level level;

	@EditableField
	protected CSColor color;
	protected float x, y;
	protected int width, height;
	protected float xVelocity, yVelocity;
	
	public Entity(int x, int y, int w, int h) {
		if (!SUBCLASSES.contains(getClass()) && !getClass().equals(Player.class)) {
			throw new IllegalStateException(getClass().getCanonicalName() + " is not in the SUBCLASSES array!\n"
					+ "Did you forget to make a static block that calls Entity.addSubclass(Class<? extends Entity>)?");
		}
		
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void onCollision(Entity entity);
	
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
	
	public static Class<? extends Entity>[] getAllSubclasses() {
		@SuppressWarnings("unchecked")
		Class<? extends Entity>[] classArray = SUBCLASSES.toArray(new Class[SUBCLASSES.size()]);
		return classArray;
	}
	
	protected static void addSubclass(Class<? extends Entity> clazz) {
		if (!SUBCLASSES.contains(clazz)) {
			SUBCLASSES.add(clazz);
		} else {
			throw new IllegalArgumentException("Class " + clazz.getCanonicalName() + " is added to subclasses more than once!");
		}
	}
	
}
