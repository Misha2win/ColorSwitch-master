/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.player;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;

import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.game.ColorSwitch;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.Updatable;
import misha.game.level.entity.item.Item;

public class Player extends Entity implements Updatable {
	
	public static final int MAX_COYOTE_TIME = 5;
	public static final float JUMP_STRENGTH = 12f;
	public static final float SPEED = 4.5f;
	public static final int PLAYER_SIZE = 20;
	
	private boolean holdingLeft, holdingRight, holdingUp;
	
	private Item[] inventory;
	private int selectedItem;

	private boolean mirrored;
	private Player mirrorPlayer;
	
	private int coyoteTime;
	private boolean jumping;
	
	private boolean drawDamageVignette;
	private boolean drawHealVignette;
	private float health;
	
	public Player(int x, int y) {
		super(x, y, PLAYER_SIZE, PLAYER_SIZE);
		
		inventory = new Item[1];
		
		reset();
	}
	
	public void reset() {
		width = PLAYER_SIZE;
		height = PLAYER_SIZE;
		
		holdingLeft = false;
		holdingRight = false;
		holdingUp = false;
		health = 1;
		selectedItem = 0;
		color = (level != null ? level.getLevelColor() : CSColor.GREEN);
		mirrored = false;
		xVelocity = 0;
		yVelocity = 0;
		
		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = null;
		}
		selectedItem = 0;
	}
	
	public boolean canJump() {
		return coyoteTime <= MAX_COYOTE_TIME && !jumping;
	}
	
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	public void incrementCoyoteTime() {
		coyoteTime++;
	}
	
	public void resetCoyoteTime() {
		coyoteTime = 0;
	}
	
	@Override
	public void draw(Graphics2D g) {
		// Draw the player itself
		g.setColor(color.equals(CSColor.WHITE) ? Color.BLACK : color.getGraphicsColor());
		g.fillRect((int)(x + 0.5f), (int)(y + 0.5f), width, height);
		
		if (color.equals(CSColor.WHITE)) {
			g.setColor(color.getGraphicsColor());
			g.fillRect((int)(x + 0.5f + 1), (int)(y + 0.5f + 1), width - 2, height - 2);
		}
		
		// Draw the mirror player
		if (mirrored) {
			// Draw arrow over real player
			g.setColor(Color.BLACK);
			Point p = new Point(5, 5);
			Point cp = new Point((int) (x + width / 2), (int) (y - 5));
			g.setStroke(new BasicStroke(4));
			g.drawLine((int) (x + p.x), (int) (y - p.y - 5), cp.x, cp.y);
			g.drawLine(cp.x, cp.y, (int) (x + width - p.x), (int) (y - p.y -  5));
			
			// Draw the mirror player
			g.setColor(mirrorPlayer.getColor().getGraphicsColor());
			g.fillRect((int)(-x + ColorSwitch.NATIVE_WIDTH - width + .5), (int)(y + .5), width, height);
		}
		
		int yOffset = 0;

		if (getRect().intersects(new Rectangle2D.Float(20, yOffset + 20, 150, 80))) {
			yOffset = 480;
		}
		
		// Draw the background of the inventory slots
		g.setColor(new Color(238, 238, 238, 100));
		g.fillRect(20, yOffset + 20, 150, 80);
		
		// Draw the player's health
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 25));
		String healthStr = (int)(health + 0.5) + "/100";
		g.setColor(Color.BLACK);
		g.drawString(healthStr, 22, 45 + yOffset);
		
		// Draw the player's inventory
		for (int i = 0; i < inventory.length; i++) {
			if (selectedItem == i)
				g.setColor(Color.GRAY.brighter());
			else
				g.setColor(Color.GRAY);
			
			g.fillRect(25 + (i * 50), 55 + yOffset, 40, 40);
			
			if (selectedItem == i) {
				// The selected item slot has a black border
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
				g.drawRect(25 + (i * 50), 55 + yOffset, 40, 40);
			}
			
			Item item = inventory[i];
			if (item != null) {
				item.setPos(45 + (i * 50) - item.getWidth() / 2, 75 - item.getHeight() / 2  + yOffset);
				item.draw(g);
			}
		}
		
		if (drawDamageVignette || drawHealVignette) {
			if (drawDamageVignette) {
				g.setColor(Color.RED);
				drawDamageVignette = false;
			} else {
				g.setColor(Color.GREEN);
				drawHealVignette = false;
			}
			
			g.setStroke(new BasicStroke(10));
			g.drawRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
			
			Color color = g.getColor();
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
			g.setStroke(new BasicStroke(30));
			g.drawRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		}
	}
	
	public boolean hasInventorySpace() {
		boolean hasEmptySlot = false;
		
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				hasEmptySlot = true;
				break;
			}
		}
		
		return hasEmptySlot;
	}
	
	@Override
	public void update() {
		if (mirrored) {
			mirrorPlayer.setPos((int)(-x + ColorSwitch.NATIVE_WIDTH - width + .5), (int)(y + .5));
		}
	}
	
	public Player getMirrorPlayer() {
		return mirrorPlayer;
	}
	
	public float getHealth() {
		return health;
	}

	public boolean getIsAlive() {
		return health > 0;
	}
	
	public void addHealth(float healthToAdd) {
		if (healthToAdd > 0) {
			drawHealVignette = true;
		} else if (healthToAdd < 0) {
			drawDamageVignette = true;
		}
		
		health += healthToAdd;
		if (health > 100) {
			health = 100;
		} else if (health < 0) {
			health = 0;
		}
		
		if (health == 0) {
			level.getLevelManager().resetLevel();
		}
	}
	
	public void setHealth(float newHealth) {
		health = newHealth;
	}
	
	public void removeInventory(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == item) {
				inventory[i] = null;
				break;
			}
		}
	}
	
	public void addInventory(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				break;
			}
		}
	}
	
	public void setMirrored(boolean newbool) {
		mirrored = newbool;
		
		if (newbool) {
			mirrorPlayer = new Player((int)(-x + ColorSwitch.NATIVE_WIDTH - width + .5), (int)(y + .5));
		}
	}
	
	public boolean getMirrored() {
		return mirrored;
	}
	
	public boolean getHoldingUp() {
		return holdingUp;
	}
	
	public boolean getHoldingRight() {
		return holdingRight;
	}
	
	public boolean getHoldingLeft() {
		return holdingLeft;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			holdingLeft = true;
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			holdingRight = true;
		} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
			holdingUp = true;
		} else if (key == KeyEvent.VK_E) {
			if (selectedItem != -1 && getIsAlive()) {
				if (inventory[selectedItem] != null) {
					inventory[selectedItem].onUse();
				}
			}
		} else if (key == KeyEvent.VK_R) {
			level.getLevelManager().resetLevel();
		} else if (key == KeyEvent.VK_1) {
			selectedItem = 0;
		} else if (key == KeyEvent.VK_2) {
			if (inventory.length >= 2)
				selectedItem = 1;
		} else if (key == KeyEvent.VK_3) {
			if (inventory.length >= 3)
				selectedItem = 2;
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			level.getLevelManager().previousLevel();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			holdingLeft = false;
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			holdingRight = false;
		} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
			holdingUp = false;
		}
	}

	@Override
	public void onCollision(Entity entity) {
		// Do nothing.
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s", (int) x, (int) y);
	}

	@Override
	public EntityEditor<?> getEntityEditor(LevelEditor levelEditor) {
		throw new IllegalStateException("getEntityEditor() cannot be called for instances of Player");
	}
	
}