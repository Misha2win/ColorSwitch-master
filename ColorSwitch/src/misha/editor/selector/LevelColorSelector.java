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
	private static final int GRAY = 9;
	
	private static final Rectangle LEVEL_BLACK_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle LEVEL_RED_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle LEVEL_GREEN_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle LEVEL_BLUE_BUTTON = new Rectangle(160, 660, 40, 40);
	private static final Rectangle LEVEL_YELLOW_BUTTON = new Rectangle(210, 660, 40, 40);
	private static final Rectangle LEVEL_MAGENTA_BUTTON = new Rectangle(260, 660, 40, 40);
	private static final Rectangle LEVEL_CYAN_BUTTON = new Rectangle(310, 660, 40, 40);
	private static final Rectangle LEVEL_WHITE_BUTTON = new Rectangle(360, 660, 40, 40);
	private static final Rectangle LEVEL_GRAY_BUTTON = new Rectangle(410, 660, 40, 40);
	
	public void setHighlightFromColor(CSColor color) {
		if (color.equals(CSColor.BLACK)) {
			highlightOption = 1;
		} else if (color.equals(CSColor.RED)) {
			highlightOption = 2;
		} else if (color.equals(CSColor.GREEN)) {
			highlightOption = 3;
		} else if (color.equals(CSColor.BLUE)) {
			highlightOption = 4;
		} else if (color.equals(CSColor.YELLOW)) {
			highlightOption = 5;
		} else if (color.equals(CSColor.MAGENTA)) {
			highlightOption = 6;
		} else if (color.equals(CSColor.CYAN)) {
			highlightOption = 7;
		} else if (color.equals(CSColor.WHITE)) {
			highlightOption = 8;
		} else if (color.equals(CSColor.GRAY)) {
			highlightOption = 9;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setColor(LevelEditor levelEditor) {
		Level level = levelEditor.getLevel();
		
		if (highlightOption == BLACK) {
			level.setLevelColor(CSColor.BLACK);
		} else if (highlightOption == RED) {
			level.setLevelColor(CSColor.RED);
		} else if (highlightOption == GREEN) {
			level.setLevelColor(CSColor.GREEN);
		} else if (highlightOption == BLUE) {
			level.setLevelColor(CSColor.BLUE);
		} else if (highlightOption == YELLOW) {
			level.setLevelColor(CSColor.YELLOW);
		} else if (highlightOption == MAGENTA) {
			level.setLevelColor(CSColor.MAGENTA);
		} else if (highlightOption == CYAN) {
			level.setLevelColor(CSColor.CYAN);
		} else if (highlightOption == WHITE) {
			level.setLevelColor(CSColor.WHITE);
		} else if (highlightOption == GRAY) {
			level.setLevelColor(CSColor.GRAY);
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
		
		DrawUtil.drawButton(g, LEVEL_GRAY_BUTTON, highlightOption == GRAY);
		g.setColor(Color.GRAY);
		g.fillRect(LEVEL_GRAY_BUTTON.x + 5, LEVEL_GRAY_BUTTON.y + 5, 30, 30);
	}
	
	public boolean mousePressed(MouseEvent e) {
		if (LEVEL_BLACK_BUTTON.contains(e.getPoint())) {
			highlightOption = BLACK;
			return true;
		} else if (LEVEL_RED_BUTTON.contains(e.getPoint())) {
			highlightOption = RED;
			return true;
		} else if (LEVEL_GREEN_BUTTON.contains(e.getPoint())) {
			highlightOption = GREEN;
			return true;
		} else if (LEVEL_BLUE_BUTTON.contains(e.getPoint())) {
			highlightOption = BLUE;
			return true;
		} else if (LEVEL_YELLOW_BUTTON.contains(e.getPoint())) {
			highlightOption = YELLOW;
			return true;
		} else if (LEVEL_MAGENTA_BUTTON.contains(e.getPoint())) {
			highlightOption = MAGENTA;
			return true;
		} else if (LEVEL_CYAN_BUTTON.contains(e.getPoint())) {
			highlightOption = CYAN;
			return true;
		} else if (LEVEL_WHITE_BUTTON.contains(e.getPoint())) {
			highlightOption = WHITE;
			return true;
		} else if (LEVEL_GRAY_BUTTON.contains(e.getPoint())) {
			highlightOption = GRAY;
			return true;
		} 
		
		return false;
	}
	
}
