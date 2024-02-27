/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.entity.player.Player;

public class Platform extends Entity {
	
	public Platform(CSColor c, int x, int y, int w, int h) {
		super(x, y, w, h);
		color = c;
	}
	
	public Platform(int x, int y, int w, int h) {
		this(CSColor.BLACK, x, y, w, h);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color.getGraphicsColor());
		
		if (level != null && level.getLevelManager() != null) {
			Player player = level.getLevelManager().getPlayer();
			Player mirrorPlayer = player.getMirrorPlayer();
			if (!level.getLevelManager().getDebugMode() && !color.equals(CSColor.BLACK)) {
				if (!player.getColor().collidesWith(color) && (!player.getMirrored() || !mirrorPlayer.getColor().collidesWith(color))) {
					Color c = color.getGraphicsColor();
					g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
				}
			}
		}
		
		g.fillRect((int)x, (int)y, width, height);
	}

	@Override
	public void onCollision(Entity e) {
		if (PhysicsEngine.isColliding(e, this)) {
			// Find how far in the entity is from each side of the platform
			float left = (e.getX() + e.getWidth()) - this.getX();
			float right = (this.getX() + this.getWidth()) - e.getX();
			float top = (e.getY() + e.getHeight()) - this.getY();
			float bot = (this.getY() + this.getHeight()) - e.getY();
			
			// Treat negative numbers as really high numbers
			left = (left < 0 ? Float.POSITIVE_INFINITY : left);
			right = (right < 0 ? Float.POSITIVE_INFINITY : right);
			top = (top < 0 ? Float.POSITIVE_INFINITY : top);
			bot = (bot < 0 ? Float.POSITIVE_INFINITY : bot);

			// Find the smallest distance needed to travel to push out the entity
			float smallest = Math.min(Math.min(left, right), Math.min(top, bot));

			if (smallest == left) { // Push entity out of the left side
				e.setPos(e.getX() - left, e.getY());
				if (e.getXVelocity() > 0)
					e.setXVelocity(0);
			} else if (smallest == right) { // Push entity out of the right side
				e.setPos(e.getX() + right, e.getY());
				if (e.getXVelocity() < 0)
					e.setXVelocity(0);
			} else if (smallest == top) { // Push entity out of the top side
				e.setPos(e.getX(), e.getY() - top);
				e.setYVelocity(0);
			} else { // Push entity out of the bottom side
				e.setPos(e.getX(), e.getY() + bot);
			}
		} else if (PhysicsEngine.willVCollide(e, this)) {
			// Will collide must be true

			// Check for collisions considering one component of velocity at a time
			if (PhysicsEngine.willCollide(e, e.getXVelocity(), 0, this, this.getXVelocity(), 0)) {
				// Adjust x velocity
				int sign = (int) Math.signum(e.getXVelocity() == 0 ? -this.getXVelocity() : e.getXVelocity());
				
				// Decelerate if the collision is going to happen
				while (PhysicsEngine.willCollide(e, e.getXVelocity(), 0, this, this.getXVelocity(), 0)) {
					e.accelerateX(-sign * 0.1f);
				}

				// Make sure that velocity has not flipped over
				if (((int) Math.signum(e.getXVelocity())) != sign) {
					e.setXVelocity(0);
				}
			} else {
				// Adjust y velocity
				int sign = (int) Math.signum(e.getYVelocity() == 0 ? -this.getYVelocity() : e.getYVelocity());
				
				// Decelerate if the collision is going to happen
				while (PhysicsEngine.willCollide(e, e.getXVelocity(), e.getYVelocity(), this, this.getXVelocity(), this.getYVelocity())) {
					e.accelerateY(-sign * 0.1f);
				}

				// Make sure that velocity has not flipped over
				if (((int) Math.signum(e.getYVelocity())) != sign) {
					e.setYVelocity(0);
				}
			}
		}
	}
	
}
