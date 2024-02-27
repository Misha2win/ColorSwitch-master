/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.point;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.point.GoalPoint;

public class GoalPointEditor extends AbstractPointEditor<GoalPoint> {
	
	public GoalPointEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public GoalPointEditor(LevelEditor levelEditor, GoalPoint goalPoint) {
		super(levelEditor, goalPoint);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	@Override
	protected GoalPoint getEntity() {
		return new GoalPoint(point.x, point.y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		}
	}
	
}
