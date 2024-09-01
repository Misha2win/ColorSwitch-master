/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.Level;
import misha.game.level.entity.CSColor;

public class LevelColorSelector extends AbstractSelector<CSColor> {
	
	private static final int BLACK = 1;
	private static final int RED = 2;
	private static final int GREEN = 3;
	private static final int BLUE = 4;
	private static final int YELLOW = 5;
	private static final int MAGENTA = 6;
	private static final int CYAN = 7;
	private static final int WHITE = 8;
	
	private static final Rectangle LEVEL_BLACK_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle LEVEL_RED_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle LEVEL_GREEN_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle LEVEL_BLUE_BUTTON = new Rectangle(160, 660, 40, 40);
	private static final Rectangle LEVEL_YELLOW_BUTTON = new Rectangle(210, 660, 40, 40);
	private static final Rectangle LEVEL_MAGENTA_BUTTON = new Rectangle(260, 660, 40, 40);
	private static final Rectangle LEVEL_CYAN_BUTTON = new Rectangle(310, 660, 40, 40);
	private static final Rectangle LEVEL_WHITE_BUTTON = new Rectangle(360, 660, 40, 40);
	
	@SuppressWarnings("deprecation")
	public void setColor(LevelEditor levelEditor, Point point) {
		Level level = levelEditor.getLevel();
		
		if (LEVEL_BLACK_BUTTON.contains(point)) {
			highlightOption = BLACK;
			level.setLevelColor(CSColor.BLACK);
		} else if (LEVEL_RED_BUTTON.contains(point)) {
			highlightOption = RED;
			level.setLevelColor(CSColor.RED);
		} else if (LEVEL_GREEN_BUTTON.contains(point)) {
			highlightOption = GREEN;
			level.setLevelColor(CSColor.GREEN);
		} else if (LEVEL_BLUE_BUTTON.contains(point)) {
			highlightOption = BLUE;
			level.setLevelColor(CSColor.BLUE);
		} else if (LEVEL_YELLOW_BUTTON.contains(point)) {
			highlightOption = YELLOW;
			level.setLevelColor(CSColor.YELLOW);
		} else if (LEVEL_MAGENTA_BUTTON.contains(point)) {
			highlightOption = MAGENTA;
			level.setLevelColor(CSColor.MAGENTA);
		} else if (LEVEL_CYAN_BUTTON.contains(point)) {
			highlightOption = CYAN;
			level.setLevelColor(CSColor.CYAN);
		} else if (LEVEL_WHITE_BUTTON.contains(point)) {
			highlightOption = WHITE;
			level.setLevelColor(CSColor.WHITE);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, LEVEL_BLACK_BUTTON, highlightOption == BLACK);
		g.setColor(Color.BLACK);
		g.fillRect(LEVEL_BLACK_BUTTON.x + 5, LEVEL_BLACK_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_RED_BUTTON, highlightOption == RED);
		g.setColor(Color.RED);
		g.fillRect(LEVEL_RED_BUTTON.x + 5, LEVEL_RED_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_GREEN_BUTTON, highlightOption == GREEN);
		g.setColor(Color.GREEN);
		g.fillRect(LEVEL_GREEN_BUTTON.x + 5, LEVEL_GREEN_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_BLUE_BUTTON, highlightOption == BLUE);
		g.setColor(Color.BLUE);
		g.fillRect(LEVEL_BLUE_BUTTON.x + 5, LEVEL_BLUE_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_YELLOW_BUTTON, highlightOption == YELLOW);
		g.setColor(Color.YELLOW);
		g.fillRect(LEVEL_YELLOW_BUTTON.x + 5, LEVEL_YELLOW_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_MAGENTA_BUTTON, highlightOption == MAGENTA);
		g.setColor(Color.MAGENTA);
		g.fillRect(LEVEL_MAGENTA_BUTTON.x + 5, LEVEL_MAGENTA_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_CYAN_BUTTON, highlightOption == CYAN);
		g.setColor(Color.CYAN);
		g.fillRect(LEVEL_CYAN_BUTTON.x + 5, LEVEL_CYAN_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, LEVEL_WHITE_BUTTON, highlightOption == WHITE);
		g.setColor(Color.WHITE);
		g.fillRect(LEVEL_WHITE_BUTTON.x + 5, LEVEL_WHITE_BUTTON.y + 5, 30, 30);
		g.setColor(Color.BLACK);
		g.drawRect(LEVEL_WHITE_BUTTON.x + 5, LEVEL_WHITE_BUTTON.y + 5, 30, 30);
	}
	
}
