/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import misha.editor.Editor;
import misha.editor.level.LevelImageLoader;
import misha.editor.level.LevelImageSaver;
import misha.editor.utility.DrawUtil;
import misha.editor.utility.Util;
import misha.game.ColorSwitch;
import misha.game.level.LevelCreator;
import misha.game.level.LevelLoader;
import misha.screen.Screen;

public class LevelOrderScreen extends Screen {
	
	private static final Rectangle RELOAD_BUTTON = new Rectangle(700, 610, 40, 40);
	private static final Rectangle SAVE_BUTTON = new Rectangle(700, 660, 40, 40);
	@SuppressWarnings("unused")
	private static final Rectangle REMOVE_BUTTON = new Rectangle(700, 710, 40, 40);
	
	private Point previousMousePos = new Point();
	
	private int focused; // TODO is focused always the same as draggingRect???
	
	private Rectangle[] LEVEL_BUTTONS;
	private int draggingRect;
	
	private final int GRID_X_DIMENSION = 7;
	private final float LEVEL_IMAGE_SCALE = 1f / ((GRID_X_DIMENSION * ColorSwitch.NATIVE_WIDTH / ColorSwitch.NATIVE_HEIGHT));
	private final int LEVEL_IMAGE_WIDTH = (int) (ColorSwitch.NATIVE_WIDTH * LEVEL_IMAGE_SCALE + 0.5);
	private final int LEVEL_IMAGE_HEIGHT = (int) (ColorSwitch.NATIVE_HEIGHT * LEVEL_IMAGE_SCALE + 0.5);
	private final int padding = 1;
	
	private String buttonHint;
	
	private String[] levelNames;
	
	public LevelOrderScreen(ScreenManager screenManager) {
		super(screenManager);
		
		buttonHint = "";
	}
	
	public void setFocusedLevel(String name) {
		for (int i = 0; i < levelNames.length; i++) {
			if (levelNames[i].equals(name)) {
				focused = i;
				break;
			}
		}
	}
	
	public boolean hasFocusedLevel() {
		return focused != -1;
	}
	
	public String getFocusedLevelName() {
		return levelNames[focused];
	}
	
	@Override
	public void setup() {
		focused = -1;
		draggingRect = -1;
		
		focused();
		
		LevelImageSaver.saveAllLevels();
	}
	
	@Override
	public void focused() {
		levelNames = LevelCreator.LEVEL_ORDER_STRING.split("\n");
		resetLevelButtons();
	}
	
	private void resetLevelButtons() {
		LEVEL_BUTTONS = new Rectangle[levelNames.length];
		
		int xOffset = (ColorSwitch.NATIVE_WIDTH - (GRID_X_DIMENSION * (LEVEL_IMAGE_WIDTH + padding) - padding)) / 2;
		int gridYDimension = (int) Math.ceil((double)levelNames.length / GRID_X_DIMENSION);
		int yOffset = (ColorSwitch.NATIVE_HEIGHT - (gridYDimension * (LEVEL_IMAGE_HEIGHT + padding) - padding)) / 2;
		
		for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
			LEVEL_BUTTONS[i] = new Rectangle(
					xOffset + (i % GRID_X_DIMENSION) * (LEVEL_IMAGE_WIDTH + padding), 
					yOffset + (i / GRID_X_DIMENSION) * (LEVEL_IMAGE_HEIGHT + padding), 
					LEVEL_IMAGE_WIDTH, 
					LEVEL_IMAGE_HEIGHT
			);
		}
	}

	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		g.setStroke(new BasicStroke(0));
		
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		String title = "Level Order Editor:";
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - g.getFontMetrics().stringWidth(title) / 2, 45);
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 600, Editor.WIDTH, 600);
		
		for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
			if (draggingRect == i)
				continue;
			
			Rectangle button = LEVEL_BUTTONS[i];
			
			try {
				BufferedImage img = LevelImageLoader.getLevelImage(levelNames[i]);
				g.drawImage(img, (int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (draggingRect != -1) {
			try {
				BufferedImage img = LevelImageLoader.getLevelImage(levelNames[draggingRect]);
				Rectangle2D button = LEVEL_BUTTONS[draggingRect];
				g.drawImage(img, (int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (focused != -1) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fill(LEVEL_BUTTONS[focused]);
		}
		
		DrawUtil.drawButton(g, RELOAD_BUTTON, false);
		g.setColor(Color.BLACK);
		g.fillOval(RELOAD_BUTTON.x + 5, RELOAD_BUTTON.y + 6, 30, 30);
		g.setColor(Color.WHITE);
		g.fillOval(RELOAD_BUTTON.x + 10, RELOAD_BUTTON.y + 11, 20, 20);
		g.fillRect(RELOAD_BUTTON.x + 20, RELOAD_BUTTON.y + 6, 15, 15);
		g.setColor(Color.BLACK);
		Polygon playArrow = new Polygon();
		playArrow.addPoint(RELOAD_BUTTON.x + 20, RELOAD_BUTTON.y + 1);
		playArrow.addPoint(RELOAD_BUTTON.x + 20, RELOAD_BUTTON.y + 17);
		playArrow.addPoint(RELOAD_BUTTON.x + 30, RELOAD_BUTTON.y + 9);
		g.fill(playArrow);
		
		DrawUtil.drawButton(g, SAVE_BUTTON, false);
		g.setColor(Color.GREEN.darker());
		g.fillRect(SAVE_BUTTON.x + 5, SAVE_BUTTON.y + 5, 30, 30);
		g.setColor(Color.GREEN);
		g.fillRect(SAVE_BUTTON.x + 10, SAVE_BUTTON.y + 10, 20, 20);
		g.setColor(Color.GREEN.darker());
		g.fillOval(SAVE_BUTTON.x + 15, SAVE_BUTTON.y + 15, 10, 10);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("monospaced", Font.PLAIN, 14));
		g.drawString(buttonHint, Editor.WIDTH - 10 - g.getFontMetrics().stringWidth(buttonHint), Editor.HEIGHT - 5);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("monospaced", Font.PLAIN, 14));
		String levelName = "Level: " + this.getFocusedLevelName();
		g.drawString(levelName, 10, Editor.HEIGHT - 5);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (RELOAD_BUTTON.contains(e.getPoint())) {
			for (String level : levelNames) {
				try {
					LevelImageLoader.loadLevelImage(level);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else if (SAVE_BUTTON.contains(e.getPoint())) {
			String levelOrderString = "";
			for (int i = 0; i < levelNames.length; i++) {
				levelOrderString += levelNames[i];
				if (i != levelNames.length - 1) {
					levelOrderString += "\n";
				}
			}
			
			try {
				Files.write(Paths.get(LevelLoader.LEVEL_DIRECTORY + "LevelsOrder.levels"), levelOrderString.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			LevelCreator.loadLevelOrderString();
		}
		else {
			draggingRect = -1;
			
			for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
				if (LEVEL_BUTTONS[i].contains(e.getPoint())) {
					focused = i;
					draggingRect = i;
					previousMousePos = e.getPoint();
					break;
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (draggingRect != -1) {
			for (int i = 0; i < LEVEL_BUTTONS.length; i++) {
				if (draggingRect == i)
					continue;
				
				if (LEVEL_BUTTONS[i].contains(e.getPoint())) {
					Util.insert(levelNames, draggingRect, i);
					
					if (draggingRect == focused) {
						focused = i;
					}
					
					break;
				}
			}
			
			resetLevelButtons();
			
			draggingRect = -1;
		}
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

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draggingRect != -1) {
			LEVEL_BUTTONS[draggingRect].x += e.getPoint().x - previousMousePos.x;
			LEVEL_BUTTONS[draggingRect].y += e.getPoint().y - previousMousePos.y;
		}
		
		previousMousePos = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (RELOAD_BUTTON.contains(e.getPoint())) {
			buttonHint = "Reload level images";
		} else if (SAVE_BUTTON.contains(e.getPoint())) {
			buttonHint = "Save level order";
		} else {
			buttonHint = "";
		}
	}
	
}
