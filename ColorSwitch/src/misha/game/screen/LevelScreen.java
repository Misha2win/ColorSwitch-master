/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelLoader;
import misha.screen.Screen;
import misha.util.FileUtil;
import misha.util.LevelImageLoader;
import misha.util.LevelImageSaver;

public class LevelScreen extends Screen {
	
	private final static RoundRectangle2D MENU_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125 + 20, ColorSwitch.NATIVE_HEIGHT - 90, 210, 75, 20, 20);
	private final static RoundRectangle2D BACK_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125 - 210 - 20, ColorSwitch.NATIVE_HEIGHT - 90, 210, 75, 20, 20);
	private final static RoundRectangle2D NEXT_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125 + 210 + 60, ColorSwitch.NATIVE_HEIGHT - 90, 210, 75, 20, 20);
	
	private static final int LEVEL_IMAGE_SCALE = 7;
	private static final int LEVEL_IMAGE_WIDTH = ColorSwitch.NATIVE_WIDTH / LEVEL_IMAGE_SCALE;
	private static final int LEVEL_IMAGE_HEIGHT = ColorSwitch.NATIVE_HEIGHT / LEVEL_IMAGE_SCALE;
	private static final int GRID_X_DIMENSION = 6;
	private static final int PADDING = 1;
	
	private final Rectangle2D.Float[] LEVEL_BUTTONS;
	
	private boolean[] unlockedLevels;
	private int page;
	
	private Level backgroundLevel;
	
	public LevelScreen(ScreenManager manager) {
		super(manager);
		
		for (File image : FileUtil.getLevelFiles()) { // XXX FIXME
			try {
				LevelImageLoader.loadLevelImage(image.getName().replace(".level", ""));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			backgroundLevel = LevelLoader.getLevel(null, "ColorChanger3");
		} catch (IOException e) {
			System.err.println("There was an issue when loading the background level!");
			e.printStackTrace();
		}
		
		int levelCount = ((ScreenManager) screenManager).getGameScreen().getLevelManager().getNonNullLevelNames().length;

		unlockedLevels = new boolean[levelCount];
		unlockedLevels[0] = true;
		page = 0;
		
		LEVEL_BUTTONS = new Rectangle2D.Float[levelCount];
		
		int xOffset = (ColorSwitch.NATIVE_WIDTH - (GRID_X_DIMENSION * (LEVEL_IMAGE_WIDTH + PADDING))) / 2;
		int gridHeight = (unlockedLevels.length > 30 ? 30 : unlockedLevels.length) / GRID_X_DIMENSION;
		int yOffset = (ColorSwitch.NATIVE_HEIGHT - (PADDING * (gridHeight - 1))) / 2 - (5 * gridHeight * LEVEL_IMAGE_HEIGHT / 9);
		
		for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
			LEVEL_BUTTONS[i] = new Rectangle2D.Float(
					xOffset + ((i % 30) % GRID_X_DIMENSION) * (LEVEL_IMAGE_WIDTH + PADDING), 
					yOffset + ((i % 30) / GRID_X_DIMENSION) * (LEVEL_IMAGE_HEIGHT + PADDING), 
					LEVEL_IMAGE_WIDTH, 
					LEVEL_IMAGE_HEIGHT
			);
		}
		
		
	}
	
	public void unlockLevel(int levelNum) {
		if (levelNum >= 0 && levelNum < unlockedLevels.length) {
			unlockedLevels[levelNum] = true;
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		backgroundLevel.draw(g);
		g.setColor(new Color(0, 0, 0, 120));
		g.fillRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		String title = "Level Selector:";
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - g.getFontMetrics().stringWidth(title) / 2, 45);
		
		String[] levelNames = ((ScreenManager) screenManager).getGameScreen().getLevelManager().getNonNullLevelNames();
		for (int i = page * 30; i < LEVEL_BUTTONS.length && i - (30 * page) < 30; i++) {
			try {
				BufferedImage img = LevelImageLoader.getLevelImage(levelNames[i]);
				Rectangle2D button = LEVEL_BUTTONS[i];
				g.drawImage(img, (int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight(), null);
				
				if (!unlockedLevels[i]) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fill(LEVEL_BUTTONS[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		drawButton(g, MENU_BUTTON, "Menu");
		drawButton(g, BACK_BUTTON, "Back");
		drawButton(g, NEXT_BUTTON, "Next");
	}
	
	public void drawButton(Graphics2D g, RoundRectangle2D button, String text) {
		g.setColor(new Color(200, 200, 200));
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
		g.fill(button);
		g.setColor(Color.BLACK);
		g.drawString(text, (int) button.getX() + (int) button.getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, (int) button.getY() + 60);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (ScreenUtil.scaledContains(BACK_BUTTON, e.getPoint())) {
			if (page > 0)
				page--;
		} else if (ScreenUtil.scaledContains(MENU_BUTTON, e.getPoint())) {
			screenManager.setScreen(ScreenManager.MENU_SCREEN);
		} else if (ScreenUtil.scaledContains(NEXT_BUTTON, e.getPoint())) {
			if (page < unlockedLevels.length / 30) {
				page++;
			}
		}
		else {
			for (int i = page * 30; i < LEVEL_BUTTONS.length && i - (30 * page) < 30; i++) {
				if (ScreenUtil.scaledContains(LEVEL_BUTTONS[i], e.getPoint())) {
					if (unlockedLevels[i]) {
						((ScreenManager) screenManager).getGameScreen().getLevelManager().setLevel(i);
						((ScreenManager) screenManager).getGameScreen().getLevelManager().resetLevel();
						screenManager.setScreen(ScreenManager.GAME_SCREEN);
					}
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			for (int i = 0; i < unlockedLevels.length; i++) {
				unlockedLevels[i] = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}
