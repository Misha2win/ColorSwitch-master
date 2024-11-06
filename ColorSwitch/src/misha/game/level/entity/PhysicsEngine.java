/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.player.Player;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.PortalPoint;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.Level;
import misha.game.level.LevelManager;

public class PhysicsEngine {

	public static final float GRAVITY = 0.5f;

	public static void calcPhysics(LevelManager levelManager) {
		Player player = levelManager.getPlayer();
		Level level = levelManager.getCurrentLevel();

		// Get keyboard input and add velocity to player
		float xInput = 0;
		if (player.getHoldingLeft())
			xInput -= Player.SPEED;
		if (player.getHoldingRight())
			xInput += Player.SPEED;
		player.setXVelocity(player.getXVelocity() + xInput);

		// Keep track if player is currently on the floor
		boolean onFloor = false;

		// Check player-platform collisions with all platforms in this level
		for (int check = 0; check < 2; check++) {
			Platform[] platforms = (check == 0 ? level.getPlatforms() : level.getGhostPlatforms());

			for (Platform platform : platforms) {
				if (platform instanceof MovingPlatform) {
					platform.translate(platform.getXVelocity(), platform.getYVelocity());
					
//					if (player.getY() + player.getHeight() > platform.getY() + platform.getHeight())
//						continue;
				}
				
				if (platform instanceof HealthGate) { // Specific check for HealthGates
					if (((HealthGate) platform).getIsOpen()) {
						continue; // This gate cannot collide with the player due to being open
					}
				} else { // Check for any other Platform types
					if (!player.color.collidesWith(platform.color) && !platform.color.equals(CSColor.BLACK)) {
						continue; // This platform cannot collide with the player due to colors
					}
				}

				// If the player and platform either are colliding or will, resolve the collision
				platform.onCollision(player);
				
				boolean onPlatform = willCollide(player, 0, GRAVITY, platform, 0, 0);
				
				if (onPlatform) {
					if (platform instanceof MovingPlatform) {
						player.accelerate(platform.xVelocity, 0);
						player.setYVelocity(platform.yVelocity);
					}
				}
				
				if (!onFloor) { // If the player is not already on the floor, check if it is
					onFloor = onPlatform;
				}
			}

			if (!player.getMirrored())
				break; // Skip ghost platforms check since player is not mirrored
		}

		for (Point point : level.getPoints()) {
			if (player.color.collidesWith(point.color)) {
				if (isColliding(player, point)) {
					point.onCollision(player);
				} else {
					if (point instanceof PortalPoint) {
						((PortalPoint) point).setCooldown(false);
					}
				}
			}
		}

		for (Obstacle obstacle : level.getObstacles()) {
			// Player only collides with Obstacles if they are NOT the same color
			boolean playerBeamCollide = (obstacle instanceof Prism && player.getRect().intersects(((Prism) obstacle).getBeam()));
			if ((!player.color.collidesWith(obstacle.color) && isColliding(player, obstacle)) || playerBeamCollide) {
				obstacle.onCollision(player);
			} else if (player.getMirrored()) {
				// Check mirror player collision since player is mirrored
				Player mirrorPlayer = player.getMirrorPlayer();
				boolean mirrorBeamCollide = (obstacle instanceof Prism && mirrorPlayer.getRect().intersects(((Prism) obstacle).getBeam()));
				if (!mirrorPlayer.color.collidesWith(obstacle.color) && isColliding(mirrorPlayer, obstacle)) {
					obstacle.onCollision(player);
				} else if (mirrorBeamCollide) {
					obstacle.onCollision(mirrorPlayer);
				}
			}
		}

		for (Item item : level.getItems()) {
			// Check if player is colliding with the item or player is mirrored and the mirrored player is colliding
			if (isColliding(player, item) || (player.getMirrored() && isColliding(player.getMirrorPlayer(), item))) {
				item.onCollision(player);
			}
		}
		
		// Allow player to jump 5 frames after falling off a platform
		if (onFloor) {
			player.resetCoyoteTime();
			player.setJumping(false);
		} else {
			player.incrementCoyoteTime();
		}
		
		if (player.canJump() && player.getHoldingUp()) { // If the player can jump (is on the floor + within coyote time frame) and wants to jump
			float multiplier = 1;
			if (Player.PLAYER_SIZE != player.getHeight()) {
				if (player.getHeight() > Player.PLAYER_SIZE) {
					multiplier = ((float) player.getHeight() / Player.PLAYER_SIZE) * 0.8f;
				} else {
					multiplier = 0.7f;
				}
			}
			player.setYVelocity(-Player.JUMP_STRENGTH * multiplier);
			player.setJumping(true);
		} else if (!onFloor) { // If the player is not on the floor and should be accelerated by gravity
			player.accelerate(0, PhysicsEngine.GRAVITY);
		}
		
		// Apply velocities onto player
		player.translate(player.getXVelocity(), player.getYVelocity());

		// Player X velocity does not carry over
		player.setXVelocity(0);
	}

	public static boolean willVCollide(Entity e1, Entity e2) {
		return willCollide(e1, e1.getXVelocity(), e1.getYVelocity(), e2, e2.getXVelocity(), e2.getYVelocity());
	}

	public static boolean willCollide(Entity e1, float x1, float y1, Entity e2, float x2, float y2) {
		boolean result = false;

		e1.translate(x1, y1);
		e2.translate(x2, y2);

		result = isColliding(e1, e2);

		e1.translate(-x1, -y1);
		e2.translate(-x2, -y2);

		return result;
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		return e1.getRect().intersects(e2.getRect());
	}

}
