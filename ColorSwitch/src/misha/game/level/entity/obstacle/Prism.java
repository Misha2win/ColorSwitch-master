/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.Beans;
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
	public void update() {
		if (level != null) {
			if (active) {
				rootBeam.updateBeam(level);
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = color.getGraphicsColor();
		
		rootBeam.draw(g);

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
		private static final int BEAM_WIDTH = 6;
		
		private Beam previousBeam;
		private Beam nextBeam;
		
		private final int direction;
		
		private Beam(CSColor color, int x, int y, int width, int height, int direction) {
			super(x, y, 0, 0);
			this.color = color;
			this.direction = direction;
			
			if (direction == UP) {
				this.x = x + width / 2 - BEAM_WIDTH / 2; 		// 1*
				this.y = y + 10 - ColorSwitch.NATIVE_HEIGHT;	// 6
			} else if (direction == RIGHT) {
				this.x = x + 10; 								// 5
				this.y = y + height / 2 - BEAM_WIDTH / 2; 		// 2*
			} else if (direction == DOWN) {
				this.x = x + width / 2 - BEAM_WIDTH / 2; 		// 1*
				this.y = y + 10; 								// 4
			} else if (direction == LEFT) {
				this.x = x + 10 - ColorSwitch.NATIVE_WIDTH; 	// 3
				this.y = y + height / 2 - BEAM_WIDTH / 2; 		// 2*
			}
			this.width = (direction % 2 == 0) ? BEAM_WIDTH : ColorSwitch.NATIVE_WIDTH;
			this.height = (direction % 2 == 1) ? BEAM_WIDTH : ColorSwitch.NATIVE_HEIGHT;
		}
		
		public void updateBeam(Level level) {
			for (Platform platform : level.getPlatforms()) {
				if (platform.getColor().collidesWith(color)) {
					if (direction == UP) {
					} else if (direction == RIGHT) {
					} else if (direction == DOWN) {
					} else if (direction == LEFT) {
					}
				}
			}
//			if (direction == UP) {
//				if (platform != null && platform.getColor().collidesWith(color)) {
//					beam.height = (int) (beam.height - ((platform.getY() + platform.getHeight()) - beam.y));
//					beam.y = (int) (platform.getY() + platform.getHeight());
//				}
//			} else if (direction == RIGHT) {
//				if (platform != null && platform.getColor().collidesWith(color)) {
//					beam.width = (int) (platform.getX() - beam.x);
//				}
//			} else if (direction == DOWN) {
//				if (platform != null && platform.getColor().collidesWith(color)) {
//					beam.height = (int) (platform.getY() - beam.y);
//				}
//			} else if (direction == LEFT) {
//				if (platform != null && platform.getColor().collidesWith(color)) {
//					beam.width = (int) (beam.width - ((platform.getX() + platform.getWidth()) - beam.x));
//					beam.x = (int) (platform.getX() + platform.getWidth());
//				}
//			} else {
//				throw new IllegalStateException("Unrecognized direction: " + direction);
//			}
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

		@Override
		public void draw(Graphics2D g) { // Draw all beams in reverse order
			if (nextBeam != null) {
				nextBeam.draw(g);
			}
			
			g.setColor(color.getGraphicsColor());
			g.fillRect((int) x, (int) y, width, height);
		}

		@Override
		public void onCollision(Entity entity) {
			throw new IllegalStateException("onCollision(Entity) in Prism.Beam class should never be called!");
		}

		@Override
		public String toString() {
			throw new IllegalStateException("toString() in Prism.Beam class should never be called!");
		}

		@Override
		public Entity clone() {
			throw new IllegalStateException("clone() in Prism.Beam class should never be called!");
		}
		
		
	}
	
}
