/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.obstacle;

import java.awt.Graphics2D;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.obstacle.Obstacle;

public abstract class AbstractObstacleEditor<T extends Obstacle> extends EntityEditor<T> {
	
	public AbstractObstacleEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public AbstractObstacleEditor(LevelEditor levelEditor, T platform) {
		super(levelEditor, platform);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
