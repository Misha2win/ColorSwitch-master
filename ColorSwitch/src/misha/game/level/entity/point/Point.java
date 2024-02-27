/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.point;

import java.awt.Graphics2D;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.Updatable;

public abstract class Point extends Entity implements Updatable {
	
	public Point(CSColor color, int x, int y, int w, int h) {
		super(x, y, w, h);
		super.color = color;
	}
	
	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color.getGraphicsColor());
		g.fillOval((int)x, (int)y, width, height);
	}
	
}
