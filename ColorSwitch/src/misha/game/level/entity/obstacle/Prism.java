/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableField;
import misha.editor.level.entity.EditableField.EditableFieldType;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.ColorChanger;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.platform.PhotonicPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.player.Player;

@EditableEntity({ EditableEntityType.POINTS, EditableEntityType.COLORS, EditableEntityType.FIELDS })
public class Prism extends Obstacle {
	
	static { Entity.addSubclass(Prism.class); }
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	private boolean active;
	
	@EditableField(value = { EditableFieldType.LIMITED }, range = { UP, RIGHT, DOWN, LEFT })
	private int direction;
	
	private Beam rootBeam;

	public Prism(CSColor color, int x, int y, int direction) {
		super(color, x, y, 20, 20);
		
		this.direction = direction;
		this.active = true;
		this.rootBeam = new Beam(this);
		
		if (direction != UP && direction != RIGHT && direction != DOWN && direction != LEFT)
			throw new IllegalArgumentException("Invalid direction provided!");
	}
	
	public Beam isCollidingBeams(Entity entity) {
		return rootBeam.getCollidingBeam(entity);
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public void setColor(CSColor color) {
		super.setColor(color);
		rootBeam.setColor(color);
	}

	@Override
	public void update() {
		if (level != null) {
			if (active) {
				rootBeam.updateBeam(level);
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		rootBeam.draw(g);

		Color c = color.getGraphicsColor();
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 100));
		Polygon prism = new Polygon();
		if (direction == UP) {
			prism.addPoint((int) (x + width / 2), (int) (y));
			prism.addPoint((int) (x + width), (int) (y + height));
			prism.addPoint((int) (x), (int) (y + height));
		} else if (direction == RIGHT) {
			prism.addPoint((int) (x), (int) (y));
			prism.addPoint((int) (x + width), (int) (y + height / 2));
			prism.addPoint((int) (x), (int) (y + height));
		} else if (direction == DOWN) {
			prism.addPoint((int) (x), (int) (y));
			prism.addPoint((int) (x + width), (int) (y));
			prism.addPoint((int) (x + width / 2), (int) (y + height));
		} else if (direction == LEFT) {
			prism.addPoint((int) (x + width), (int) (y));
			prism.addPoint((int) (x + width), (int) (y + height));
			prism.addPoint((int) (x), (int) (y + height / 2));
		}
		g.fill(prism);
		
		if (color.equals(CSColor.WHITE)) {
			g.setColor(Color.BLACK);
			g.draw(prism);
		}
	}

	@Override
	public void onCollision(Entity entity) {
		if (entity instanceof Player) {
			Beam collidingBeam = rootBeam.getCollidingBeam(entity);
			if (collidingBeam != null) {
				if (level.getLevelManager().getPlayer().getMirrored())
					level.createGhostPlatforms(level.getLevelManager().getPlayer().getMirrorPlayer().getColor());
				entity.setColor(collidingBeam.getColor());
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", CSColor.getStringFromColor(color), (int) x, (int) y, direction);
		
	}
	
	@Override
	public Entity clone() {
		return new Prism(color, (int) x, (int) y, direction);
	}
	
	public class Beam extends Entity {
		private static final int BEAM_WIDTH = 5;
		
		private final Prism prism;
		private final int direction;
		
		private Beam previousBeam;
		private Beam nextBeam;
		
		private Beam(Prism prism) {
			super(prism.x, prism.y, 0, 0);
			this.prism = prism;
			this.color = prism.color;
			this.direction = prism.direction;
		}
		
		private void resetBeam() {
			if (previousBeam == null) {
				if (direction == UP) {
					this.x = prism.x + prism.width / 2 - BEAM_WIDTH / 2; 
					this.y = prism.y + 10 - ColorSwitch.NATIVE_HEIGHT;	
				} else if (direction == RIGHT) {
					this.x = prism.x + 10; 							
					this.y = prism.y + prism.height / 2 - BEAM_WIDTH / 2; 		
				} else if (direction == DOWN) {
					this.x = prism.x + prism.width / 2 - BEAM_WIDTH / 2; 	
					this.y = prism.y + 10; 							
				} else if (direction == LEFT) {
					this.x = prism.x + 10 - ColorSwitch.NATIVE_WIDTH; 	
					this.y = prism.y + prism.height / 2 - BEAM_WIDTH / 2; 	
				}
				this.width = (direction % 2 == 0) ? BEAM_WIDTH : ColorSwitch.NATIVE_WIDTH;
				this.height = (direction % 2 == 1) ? BEAM_WIDTH : ColorSwitch.NATIVE_HEIGHT;
			} else {
				if (direction == UP) {
					this.x = previousBeam.x;
					this.y = previousBeam.y - ColorSwitch.NATIVE_HEIGHT;
					this.width = previousBeam.width;
					this.height = ColorSwitch.NATIVE_HEIGHT;
				} else if (direction == RIGHT) {
					this.x = previousBeam.x + previousBeam.width;
					this.y = previousBeam.y;
					this.width = ColorSwitch.NATIVE_WIDTH;
					this.height = previousBeam.height;
				} else if (direction == DOWN) {
					this.x = previousBeam.x;
					this.y = previousBeam.y + previousBeam.height;
					this.width = previousBeam.width;
					this.height = ColorSwitch.NATIVE_HEIGHT;
				} else if (direction == LEFT) {
					this.x = previousBeam.x - ColorSwitch.NATIVE_WIDTH;
					this.y = previousBeam.y;
					this.width = ColorSwitch.NATIVE_WIDTH;
					this.height = previousBeam.height;
				}
			}
		}

		/**
		 * Note: This will throw a stack overflow error if a beam is colliding with another beam
		 * while one of those beams is half inside of a platform in a way that is parallel to the direction of the beam.
		 * This happens because beams have a width and are not infinitely thin lines
		 */
		public void updateBeam(Level level) {
			resetBeam();
			
			for (Platform platform : level.getPlatforms()) {
				if (platform instanceof PhotonicPlatform)
					continue;
				
				if (platform.getColor().collidesWith(color) && getRect().intersects(platform.getRect())) {
					CSColor subtraction = color.subtract(platform.getColor());
					
					shortenBeam(platform);
					nextBeam = null;
					
					if (!subtraction.equals(CSColor.BLACK) && !platform.getColor().equals(CSColor.BLACK)) { // XXX GC issues???
						createPartition(level, subtraction);
					}
				}
			}
			
			// Second check specifically for PhotonicPlatforms
			for (Platform platform : level.getPlatforms()) {
				if (platform instanceof PhotonicPlatform && getRect().intersects(platform.getRect())) {
					if (platform.getColor().equals(CSColor.GRAY)) {
						platform.setColor(color);
					} else {
						platform.setColor(platform.getColor().add(color));
					}
				}
			}
			
			for (Obstacle obstacle : level.getObstacles()) {
				if (obstacle instanceof Prism prism) {
					if (prism == this.prism)
						continue;
					
					Beam collidingBeam = prism.isCollidingBeams(this);
					if (collidingBeam != null) {
						CSColor addition = collidingBeam.color.add(color);
						if (!addition.equals(color)) {
							shortenBeam(collidingBeam);
							
							createPartition(level, addition);
						}
					}
				}
			}
			
			for (Item item : level.getItems()) {
				if (item instanceof ColorChanger colorChanger) {
					if (!colorChanger.getColor().equals(color) && getRect().intersects(colorChanger.getRect())) {
						shortenBeam(colorChanger);
						
						createPartition(level, colorChanger.getColor());
					}
				}
			}
		}
		
		private void shortenBeam(Entity entity) {
			boolean isRoot = previousBeam == null;
			
			if (direction == UP) {
				height = (int) ((isRoot ? prism.height / 2 : height) - (entity.getY() + entity.getHeight()) + (isRoot ? prism.y : y));
				y = (int) (entity.getY() + entity.getHeight());
			} else if (direction == RIGHT) {
				width = (int) (entity.getX() - (isRoot ? prism.x : x)) - (isRoot ? prism.width / 2 : 0);
			} else if (direction == DOWN) {
				height = (int) (entity.getY() - (isRoot ? prism.y : y)) - (isRoot ? prism.height / 2 : 0);
			} else if (direction == LEFT) {
				width = (int) ((isRoot ? prism.width / 2 : width) - (entity.getX() + entity.getWidth()) + (isRoot ? prism.x : x));
				x = (int) (entity.getX() + entity.getWidth());
			}
		}
		
		public Beam getCollidingBeam(Entity entity) {
			if (getRect().intersects(entity.getRect())) {
				return this;
			}

			if (nextBeam != null)
				return nextBeam.getCollidingBeam(entity);
			else
				return null;
		}
		
		private void createPartition(Level level, CSColor color) {
			Beam newBeam = new Beam(prism);
			newBeam.previousBeam = this;
			this.nextBeam = newBeam;
			
			newBeam.setColor(color);
			newBeam.updateBeam(level);
		}

		@Override
		public void draw(Graphics2D g) { // Draw all beams in reverse order
			if (nextBeam != null) {
				nextBeam.draw(g);
			}
			
			g.setColor(color.getGraphicsColor());
			g.fillRect((int) x, (int) y, width, height);
			
			if (color.equals(CSColor.WHITE)) {
				g.setColor(Color.BLACK);
				g.drawRect((int) x, (int) y, width, height);
			}
		}

		@Override
		public void onCollision(Entity entity) {
			throw new IllegalStateException("onCollision(Entity) in Prism.Beam class should never be called!");
		}

		@Override
		public String toString() {
			return "[" + x + " " + y + " " + width + " " + height + "]";
		}

		@Override
		public Entity clone() {
			throw new IllegalStateException("clone() in Prism.Beam class should never be called!");
		}
		
		
	}
	
}
