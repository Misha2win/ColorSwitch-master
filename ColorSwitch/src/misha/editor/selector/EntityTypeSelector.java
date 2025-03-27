/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import misha.editor.screen.LevelEditorScreen;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;
import misha.util.DrawUtil;

public class EntityTypeSelector extends AbstractSelector<CSColor> {
	
	private static final Rectangle PLATFORM_SELECT_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle ITEM_SELECT_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle OBSTACLE_SELECT_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle POINT_SELECT_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle COLOR_SELECT_BUTTON = new Rectangle(210, 610, 40, 40);
	
	private static final int PLATFORMS = 1;
	private static final int OBSTACLES = 2;
	private static final int POINTS = 3;
	private static final int ITEMS = 4;
	private static final int COLORS = 5;
	
	private LevelEditorScreen levelEditor;
	
	public EntityTypeSelector(LevelEditorScreen levelEditor) {
		this.levelEditor = levelEditor;
	}

	@Override
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, PLATFORM_SELECT_BUTTON, highlightOption == PLATFORMS);
		g.setColor(Color.BLACK);
		g.fillRect(PLATFORM_SELECT_BUTTON.x + 5, PLATFORM_SELECT_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, ITEM_SELECT_BUTTON, highlightOption == ITEMS);
		g.setColor(Color.RED.darker());
		g.fillRect(ITEM_SELECT_BUTTON.x + 5, ITEM_SELECT_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(ITEM_SELECT_BUTTON.x + 10, ITEM_SELECT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, OBSTACLE_SELECT_BUTTON, highlightOption == OBSTACLES);
		g.setColor(Color.BLUE.darker());
		g.fillRect(OBSTACLE_SELECT_BUTTON.x + 5, OBSTACLE_SELECT_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, POINT_SELECT_BUTTON, highlightOption == POINTS);
		g.setColor(Color.GREEN);
		g.fillOval(POINT_SELECT_BUTTON.x + 10, POINT_SELECT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, COLOR_SELECT_BUTTON, highlightOption == COLORS);
		g.setColor(levelEditor.getLevel().getLevelColor().getGraphicsColor());
		g.fillRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		if (levelEditor.getLevel().getLevelColor().equals(CSColor.WHITE)) {
			g.setColor(Color.BLACK);
			g.drawRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		}
	}
	
	public AbstractSelector<?> getSelector() {
		if (highlightOption == PLATFORMS) {
			return MasterEntitySelector.getSelector(Platform.class);
		} else if (highlightOption == ITEMS) {
			return MasterEntitySelector.getSelector(Item.class);
		} else if (highlightOption == OBSTACLES) {
			return MasterEntitySelector.getSelector(Obstacle.class);
		} else if (highlightOption == POINTS) {
			return MasterEntitySelector.getSelector(Point.class);
		} else if (highlightOption == COLORS) {
			LevelColorSelector selector = new LevelColorSelector();
			selector.setHighlightFromColor(levelEditor.getLevel().getLevelColor());
			return new LevelColorSelector();
		}
		
		return null;
	}
	
	public boolean mousePressed(MouseEvent e) {
		if (PLATFORM_SELECT_BUTTON.contains(e.getPoint())) {
			highlightOption = PLATFORMS;
			return true;
		} else if (ITEM_SELECT_BUTTON.contains(e.getPoint())) {
			highlightOption = ITEMS;
			return true;
		} else if (OBSTACLE_SELECT_BUTTON.contains(e.getPoint())) {
			highlightOption = OBSTACLES;
			return true;
		} else if (POINT_SELECT_BUTTON.contains(e.getPoint())) {
			highlightOption = POINTS;
			return true;
		} else if (COLOR_SELECT_BUTTON.contains(e.getPoint())) {
			highlightOption = COLORS;
			return true;
		}
		
		return false;
	}
	
}
