/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.point.GoalPointEditor;
import misha.editor.level.entity.point.PortalPointEditor;
import misha.editor.level.entity.point.SpawnPointEditor;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.PortalPoint;
import misha.game.level.entity.point.SpawnPoint;

public class PointSelector extends AbstractEntitySelector<Point> {
	
	private static final int GOAL = 1;
	private static final int PORTAL = 2;
	private static final int SPAWN = 3;
	
	private static final Rectangle GOAL_POINT_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle PORTAL_POINT_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle SPAWN_POINT_BUTTON = new Rectangle(110, 660, 40, 40);
	
	@Override
	public EntityEditor<? extends Point> getEditor(LevelEditor levelEditor, Point[] points, java.awt.Point point) {
		Point clickedPoint = null;
		for (Point p : points) {
			if (p.getRect().contains(point)) {
				clickedPoint = p;
			}
		}
		
		if (clickedPoint == null) {
			if (GOAL_POINT_BUTTON.contains(point)) {
				highlightOption = GOAL;
				return new GoalPointEditor(levelEditor);
			} else if (PORTAL_POINT_BUTTON.contains(point)) {
				highlightOption = PORTAL;
				return new PortalPointEditor(levelEditor);
			} else if (SPAWN_POINT_BUTTON.contains(point)) {
				highlightOption = SPAWN;
				return new SpawnPointEditor(levelEditor);
			}
		} else {
			if (clickedPoint.getClass().equals(GoalPoint.class)) {
				highlightOption = GOAL;
				return new GoalPointEditor(levelEditor, (GoalPoint) clickedPoint);
			} else if (clickedPoint.getClass().equals(PortalPoint.class)) {
				highlightOption = PORTAL;
				return new PortalPointEditor(levelEditor, (PortalPoint) clickedPoint);
			} else if (clickedPoint.getClass().equals(SpawnPoint.class)) {
				highlightOption = SPAWN;
				return new SpawnPointEditor(levelEditor, (SpawnPoint) clickedPoint);
			}
		}
		
		return null;
	}
	
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, GOAL_POINT_BUTTON, highlightOption == GOAL);
		g.setColor(Color.GREEN);
		g.fillOval(GOAL_POINT_BUTTON.x + 10, GOAL_POINT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, PORTAL_POINT_BUTTON, highlightOption == PORTAL);
		g.setColor(Color.BLUE);
		g.fillOval(PORTAL_POINT_BUTTON.x + 10, PORTAL_POINT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, SPAWN_POINT_BUTTON, highlightOption == SPAWN);
		g.setColor(Color.RED);
		g.fillOval(SPAWN_POINT_BUTTON.x + 10, SPAWN_POINT_BUTTON.y + 10, 20, 20);
	}
	
}
