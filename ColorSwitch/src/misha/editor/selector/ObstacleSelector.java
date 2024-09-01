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
import java.awt.Point;
import java.awt.Polygon;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.obstacle.HealthPoolEditor;
import misha.editor.level.entity.obstacle.LavaEditor;
import misha.editor.level.entity.obstacle.PrismEditor;
import misha.editor.level.entity.obstacle.WaterEditor;
import misha.game.level.entity.obstacle.Acid;
import misha.game.level.entity.obstacle.Lava;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.entity.obstacle.Water;

public class ObstacleSelector extends AbstractEntitySelector<Obstacle> {
	
	private static final int HEALTH_POOL = 1;
	private static final int LAVA = 2;
	private static final int WATER = 3;
	private static final int PRISM = 4;
	
	private static final Rectangle HEALTH_POOL_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle LAVA_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle WATER_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle PRISM_BUTTON = new Rectangle(160, 660, 40, 40);
	
	@Override
	public EntityEditor<? extends Obstacle> getEditor(LevelEditor levelEditor, Obstacle[] obstacles, Point point) {
		Obstacle clickedObstacle = null;
		for (Obstacle obstacle : obstacles) {
			if (obstacle.getRect().contains(point)) {
				clickedObstacle = obstacle;
			}
		}
		
		if (clickedObstacle == null) {
			if (HEALTH_POOL_BUTTON.contains(point)) {
				highlightOption = HEALTH_POOL;
				return new HealthPoolEditor(levelEditor);
			} else if (LAVA_BUTTON.contains(point)) {
				highlightOption = LAVA;
				return new LavaEditor(levelEditor);
			} else if (WATER_BUTTON.contains(point)) {
				highlightOption = WATER;
				return new WaterEditor(levelEditor);
			} else if (PRISM_BUTTON.contains(point)) {
				highlightOption = PRISM;
				return new PrismEditor(levelEditor);
			}
		} else {
			if (clickedObstacle.getClass().equals(Acid.class)) {
				highlightOption = HEALTH_POOL;
				return new HealthPoolEditor(levelEditor, (Acid) clickedObstacle);
			} else if (clickedObstacle.getClass().equals(Lava.class)) {
				highlightOption = LAVA;
				return new LavaEditor(levelEditor, (Lava) clickedObstacle);
			} else if (clickedObstacle.getClass().equals(Water.class)) {
				highlightOption = WATER;
				return new WaterEditor(levelEditor, (Water) clickedObstacle);
			} else if (clickedObstacle.getClass().equals(Prism.class)) {
				highlightOption = PRISM;
				return new PrismEditor(levelEditor, (Prism) clickedObstacle);
			}
		}
		
		return null;
	}
	
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, HEALTH_POOL_BUTTON, highlightOption == HEALTH_POOL);
		g.setColor(Color.GREEN.darker());
		g.fillRect(HEALTH_POOL_BUTTON.x + 5, HEALTH_POOL_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LAVA_BUTTON, highlightOption == LAVA);
		g.setColor(Color.RED.darker());
		g.fillRect(LAVA_BUTTON.x + 5, LAVA_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, WATER_BUTTON, highlightOption == WATER);
		g.setColor(Color.BLUE.darker());
		g.fillRect(WATER_BUTTON.x + 5, WATER_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, PRISM_BUTTON, highlightOption == PRISM);
		g.setColor(Color.GRAY);
		Polygon prism = new Polygon();
		prism.addPoint((int) (PRISM_BUTTON.x + 20), (int) (PRISM_BUTTON.y + 10));
		prism.addPoint((int) (PRISM_BUTTON.x + 30), (int) (PRISM_BUTTON.y + 30));
		prism.addPoint((int) (PRISM_BUTTON.x + 10), (int) (PRISM_BUTTON.y + 30));
		g.fill(prism);
	}
	
}
