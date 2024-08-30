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
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import misha.editor.level.LevelImageLoader;
import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelLoader;
import misha.screen.Screen;

public class LevelScreen extends Screen {
	
	private final static RoundRectangle2D BACK_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125, ColorSwitch.NATIVE_HEIGHT - 90, 250, 75, 20, 20);
	
	private final Rectangle2D.Float[] LEVEL_BUTTONS;
	
	private final int LEVEL_IMAGE_SCALE = 7;
	private final int LEVEL_IMAGE_WIDTH = ColorSwitch.NATIVE_WIDTH / LEVEL_IMAGE_SCALE;
	private final int LEVEL_IMAGE_HEIGHT = ColorSwitch.NATIVE_HEIGHT / LEVEL_IMAGE_SCALE;
	private final int GRID_X_DIMENSION = 6;
	private final int padding = 1;
	
	private boolean[] unlockedLevels;
	
	private Level backgroundLevel;
	
	public LevelScreen(ScreenManager manager) {
		super(manager);
		
		try {
			backgroundLevel = LevelLoader.getLevel(null, "ColorChanger3");
		} catch (IOException e) {
			System.err.println("There was an issue when loading the background level!");
			e.printStackTrace();
		}
		
		int levelCount = (int) Arrays.stream(((ScreenManager) screenManager).getGameScreen().getLevelManager().getLevelNames()).filter(Objects::nonNull).count();

		unlockedLevels = new boolean[levelCount];
		unlockedLevels[0] = true;
		
		LEVEL_BUTTONS = new Rectangle2D.Float[levelCount];
		
		int xOffset = (ColorSwitch.NATIVE_WIDTH - (GRID_X_DIMENSION * (LEVEL_IMAGE_WIDTH + padding))) / 2;
		int gridHeight = unlockedLevels.length / GRID_X_DIMENSION;
		int yOffset = (ColorSwitch.NATIVE_HEIGHT - (padding * (gridHeight - 1))) / 2 - (5 * gridHeight * LEVEL_IMAGE_HEIGHT / 9);
		
		for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
			LEVEL_BUTTONS[i] = new Rectangle2D.Float(
					xOffset + (i % GRID_X_DIMENSION) * (LEVEL_IMAGE_WIDTH + padding), 
					yOffset + (i / GRID_X_DIMENSION) * (LEVEL_IMAGE_HEIGHT + padding), 
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
	public void focused() {
		
		
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
		
		int count = 0;
		for (String level : ((ScreenManager) screenManager).getGameScreen().getLevelManager().getLevelNames()) {
			if (level == null)
				continue;
			
			try {
				BufferedImage img = LevelImageLoader.getLevelImage(level);
				g.drawImage(img, (int) LEVEL_BUTTONS[count].getX(), (int) LEVEL_BUTTONS[count].getY(), (int) LEVEL_BUTTONS[count].getWidth(), (int) LEVEL_BUTTONS[count].getHeight(), null);
				
				if (!unlockedLevels[count]) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fill(LEVEL_BUTTONS[count]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			count++;
		}
		
		String backString = "Back";
		g.setColor(new Color(200, 200, 200));
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
		g.fill(BACK_BUTTON);
		g.setColor(Color.BLACK);
		g.drawString(backString, ColorSwitch.NATIVE_WIDTH / 2 - g.getFontMetrics().stringWidth(backString) / 2, (int) BACK_BUTTON.getY() + 55);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (BACK_BUTTON.contains(e.getPoint())) {
			screenManager.setScreen(ScreenManager.MENU_SCREEN);
		} else {
			for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
				if (LEVEL_BUTTONS[i].contains(e.getPoint())) {
					if (unlockedLevels[i]) {
						((ScreenManager) screenManager).getGameScreen().getLevelManager().setLevel(i);
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
