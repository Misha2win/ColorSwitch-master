/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.entity.player.Player;

public class ColorMixer extends Item {
	
	private boolean add;
	
	public ColorMixer(CSColor color, int x, int y, boolean add) {
		super(x, y, 30, 30);
		this.color = color;
		this.add = add;
	}
	
	public boolean getAdd() {
		return add;
	}
	
	@Override
	public void onUse() {
		if (level == null)
			return;
		
		Player player = level.getLevelManager().getPlayer();
		
		if (add)
			player.setColor(player.getColor().add(color));
		else
			player.setColor(player.getColor().subtract(color));
		
		for (Obstacle obstacle : level.getObstacles()) {
			if (obstacle instanceof Prism) {
				Prism prism = (Prism) obstacle;
				if (level.getLevelManager().getPlayer().getRect().intersects(prism.getBeam()))
					if (add)
						obstacle.setColor(obstacle.getColor().add(color));
					else
						obstacle.setColor(obstacle.getColor().subtract(color));
			}
		}
		
		if (!persist) {
			removeItem();
			used = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (!used) {
			g.setColor(color.getGraphicsColor().darker());
			g.fillRect((int)x, (int)y, width, height);
			
			g.setColor(color.getGraphicsColor());
			g.fillRect((int)x + 5, (int)y + 5, width - 10, height - 10);
			
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(1));
			g.fillRoundRect((int)x + 6, (int)(y + height / 2) - 3, width - 12, 6, 3, 3);
			if (add)
				g.fillRoundRect((int)(x + width / 2) - 3, (int)y + 6, 6, height - 12, 3, 3);
			
			g.setColor(Color.WHITE);
			g.fillRoundRect((int)x + 7, (int)(y + height / 2) - 2, width - 14, 4, 3, 3);
			if (add)
				g.fillRoundRect((int)(x + width / 2) - 2, (int)y + 7, 4, height - 14, 3, 3);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", CSColor.getStringFromColor(color), (int) x, (int) y, add);
	}
	
	public static void main(String[] args) {
		System.out.println(new ColorMixer(CSColor.RED, 100, 100, true));
	}
	
}
