package misha.editor.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import misha.screen.Screen;
import misha.util.DrawUtil;
import misha.game.level.Level;
import misha.screen.AbstractScreenManager;

public class ScreenManager extends AbstractScreenManager {
	
	private static final Rectangle SWITCH_BUTTON = new Rectangle(700, 760, 40, 40);

	public static final int LEVEL_SCREEN = 0;
	public static final int ORDER_SCREEN = 1;
	public static final int UNUSED_SCREEN = 2;
	
	/**
	 * Constructs a new ScreenManager
	 * 
	 * @param surface the surface to give all of the Screens
	 */
	public ScreenManager(Level level, int num) {
		currentScreen = ScreenManager.LEVEL_SCREEN;
		
		this.screens = new ArrayList<Screen>();
		screens.add(new LevelEditorScreen(this, level, num));
		screens.add(new LevelOrderScreen(this));
		screens.add(new UnusedLevelScreen(this));
		
		screens.get(currentScreen).focused();
		
		setup();
	}
	
	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		super.draw(g);
		
		DrawUtil.drawButton(g, SWITCH_BUTTON, false);
		g.setColor(Color.BLACK);
		Polygon handle = new Polygon();
		handle.addPoint((int)(SWITCH_BUTTON.x + 20), (int)(SWITCH_BUTTON.y + 5));
		handle.addPoint((int)(SWITCH_BUTTON.x + 35), (int)(SWITCH_BUTTON.y + 20));
		handle.addPoint((int)(SWITCH_BUTTON.x + 20), (int)(SWITCH_BUTTON.y + 35));
		handle.addPoint((int)(SWITCH_BUTTON.x + 5), (int)(SWITCH_BUTTON.y + 20));
		g.fill(handle);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (SWITCH_BUTTON.contains(e.getPoint())) {
			if (currentScreen == LEVEL_SCREEN) {
				LevelEditorScreen editorScreen = (LevelEditorScreen) getCurrentScreen();
				if (editorScreen.getLevel() != null) {
					LevelOrderScreen orderScreen = (LevelOrderScreen) getScreen(ORDER_SCREEN);
					orderScreen.setFocusedLevel(editorScreen.getLevel().getLevelName());
				}
				
				super.setScreen(ORDER_SCREEN);
			} else if (currentScreen == ORDER_SCREEN) {
				LevelOrderScreen orderScreen = (LevelOrderScreen) getCurrentScreen();
				if (orderScreen.hasFocusedLevel()) {
					LevelEditorScreen editorScreen = (LevelEditorScreen) getScreen(LEVEL_SCREEN);
					editorScreen.setLevel(orderScreen.getFocusedLevelName());
				}
				
				super.setScreen(LEVEL_SCREEN);
			}
		} else {
			super.mousePressed(e);
		}
	}
	
	
	
}
