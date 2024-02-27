/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.obstacle;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.Updatable;

public abstract class Obstacle extends Entity implements Updatable {

	public Obstacle(CSColor color, int x, int y, int w, int h) {
		super(x, y, w, h);
		super.color = color;
	}
	
}
