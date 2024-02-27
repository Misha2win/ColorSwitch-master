/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import misha.game.level.entity.player.Player;

public class Teleporter extends Item {
	
	public static final Color PURPLE = new Color(150, 0, 200);
	
	private int endX, endY;
	
	public Teleporter(int x, int y, int x2, int y2) {
		super("Teleporter", x, y, 30, 30);
		endX = x2;
		endY = y2;
	}
	
	public Teleporter(int x, int y) {
		this(x, y, -1, -1);
	}
	
	@Override
	public void onUse() {	
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		if (endX != -1 && endY != -1) {
			if (!persist) {
				removeItem();
				used = true;
			}
			
			player.setPos(endX, endY);
		} else {
			endX = (int)(player.getX() + .5f);
			endY = (int)(player.getY() + .5f);
		}
	}
	
	public int getX2() {
		return endX;
	}
	
	public int getY2() {
		return endY;
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			// Draw the item itself
			g.setColor(Teleporter.PURPLE);
			g.fillRect((int)x, (int)y, width, height);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.PLAIN, 14));
			g.drawString("TP", x + 7, y + 20);
			
			// Draw the TP end point
			if (endX != -1 && endY != -1) {
				g.setColor(Teleporter.PURPLE);
				g.fillOval((int)endX, (int)endY, 20, 20);
			}
		}
	}
	
}
