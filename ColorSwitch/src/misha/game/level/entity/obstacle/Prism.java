/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Polygon;
import misha.game.ColorSwitch;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.player.Player;

public class Prism extends Obstacle {
	
	private static final int BEAM_WIDTH = 5;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
//	private boolean cooldown;
	private boolean active;
	private int direction;
	private Rectangle beam;

	public Prism(CSColor color, int x, int y, int direction) {
		super(color, x, y, 20, 20);
		
		this.direction = direction;
		this.active = true;
		
		if (direction != UP && direction != RIGHT && direction != DOWN && direction != LEFT)
			throw new IllegalArgumentException("Invalid direction provided!");
	}
	
	public Rectangle getBeam() {
		return new Rectangle(beam.x, beam.y, beam.width, beam.height);
	}
	
	public int getDirection() {
		return direction;
	}

	@Override
	public void update() {
		if (level != null) {
//			if (getRect().intersects(level.getLevelManager().getPlayer().getRect())) { // TODO refactor this
//				if (!cooldown) {
//					active = !active;
//					cooldown = true;
//				}
//			} else {
//				cooldown = false;
//			}
			
			if (active) {
				if (direction == UP)
					beam = new Rectangle((int) x + width / 2 - BEAM_WIDTH / 2, (int) y + 10 - ColorSwitch.HEIGHT, BEAM_WIDTH, ColorSwitch.HEIGHT);
				else if (direction == RIGHT)
					beam = new Rectangle((int) x + 10, (int) y + height / 2 - BEAM_WIDTH / 2, ColorSwitch.WIDTH, BEAM_WIDTH);
				else if (direction == DOWN)
					beam = new Rectangle((int) x + width / 2 - BEAM_WIDTH / 2, (int) y + 10, BEAM_WIDTH, ColorSwitch.HEIGHT);
				else if (direction == LEFT)
					beam = new Rectangle((int) x + 10 - ColorSwitch.WIDTH, (int) y + height / 2 - BEAM_WIDTH / 2, ColorSwitch.WIDTH, BEAM_WIDTH);
				
				for (Entity platform : level.getPlatforms()) {
					if ((platform.getColor().collidesWith(color) || platform.getColor().equals(CSColor.BLACK) || platform.getColor().equals(CSColor.WHITE)) && beam.intersects(platform.getRect())) {
						if (direction == UP) {
							beam.height = (int) (beam.height - ((platform.getY() + platform.getHeight()) - beam.y));
							beam.y = (int) (platform.getY() + platform.getHeight());
						} else if (direction == RIGHT) {
							beam.width = (int) (platform.getX() - beam.x);
						} else if (direction == DOWN) {
							beam.height = (int) (platform.getY() - beam.y);
						} else if (direction == LEFT) {
							beam.width = (int) (beam.width - ((platform.getX() + platform.getWidth()) - beam.x));
							beam.x = (int) (platform.getX() + platform.getWidth());
						}
					}
				}
			} else {
				beam = new Rectangle();
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = color.getGraphicsColor();
		
		if (beam != null) {
			g.setColor(c);
			g.fill(beam);
		}

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
			if (beam.intersects(entity.getRect())) {
				if (level.getLevelManager().getPlayer().getMirrored())
					level.createGhostPlatforms(level.getLevelManager().getPlayer().getMirrorPlayer().getColor());
				entity.setColor(color);
			}
		}
	}
	
}
