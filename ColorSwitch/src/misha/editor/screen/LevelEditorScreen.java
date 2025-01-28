/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.screen;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import misha.editor.Editor;
import misha.editor.level.entity.MasterEntityEditor;
import misha.editor.selector.AbstractSelector;
import misha.editor.selector.EntityTypeSelector;
import misha.editor.selector.LevelColorSelector;
import misha.editor.selector.MasterEntitySelector;
import misha.editor.utility.DrawUtil;
import misha.editor.utility.LevelUtil;
import misha.game.level.entity.Entity;
import misha.screen.Screen;
import misha.game.level.Level;
import misha.game.level.LevelCreator;

public class LevelEditorScreen extends Screen {
	
	// Misc. buttons
	private static final Rectangle CLEAR_BUTTON = new Rectangle(650, 610, 40, 40);
	private static final Rectangle DELETE_BUTTON = new Rectangle(700, 610, 40, 40);
	private static final Rectangle SAVE_BUTTON = new Rectangle(700, 660, 40, 40);
	private static final Rectangle SAVE_NEW_BUTTON = new Rectangle(650, 660, 40, 40);
	private static final Rectangle PLAY_LEVEL_BUTTON = new Rectangle(700, 710, 40, 40);
	private static final Rectangle NEXT_LEVEL_BUTTON = new Rectangle(650, 760, 40, 40);
	private static final Rectangle PREVIOUS_LEVEL_BUTTON = new Rectangle(600, 760, 40, 40);
	private static final Rectangle SEEK_LEVEL_BUTTON = new Rectangle(550, 760, 40, 40);
	
	private EntityTypeSelector typeSelector;
	private AbstractSelector<?> entitySelector;
	private MasterEntityEditor<?> entityEditor;
	
	private int levelCounter;
	private Level[] levels;
	private Level level;
	
	public LevelEditorScreen(ScreenManager screenManager, Level level) {
		this(screenManager, level, -1);
	}
	
	public LevelEditorScreen(ScreenManager screenManager, Level level, int num) {
		super(screenManager);
		
		if (level == null) {
			level = LevelCreator.createEmptyLevel("EditingLevel", null);
		} else {
			this.level = level;
		}
		
		this.levels = LevelCreator.createLevels(null);
		this.levelCounter = num;
		this.typeSelector = new EntityTypeSelector(this);
		
		System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
	}
	
	public void setLevel(String levelName) {
		for (int i = 0; i < levels.length; i++) {
			if (levels[i] == null)
				continue;
			
			if (levels[i].getLevelName().equals(levelName)) {
				levelCounter = i;
				level = levels[levelCounter];
				break;
			}
		}
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void drawGuideLines(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(0));
		
		for (int i = 0; i < Editor.WIDTH; i += 10) {
			g.drawLine(i, 0, i, 600);
		}
		
		for (int i = 0; i < Editor.WIDTH; i += 50) {
			g.drawLine(i, 0, i, 600);
		}
		
		for (int i = 0; i < 600; i += 10) {
			g.drawLine(0, i, Editor.WIDTH, i);
		}
		
		for (int i = 0; i < 600; i += 50) {
			g.drawLine(0, i, Editor.WIDTH, i);
		}
		
		g.setColor(Color.RED);
		g.drawLine(Editor.WIDTH / 2, 0, Editor.WIDTH / 2, 600);
		g.drawLine(0, 300, Editor.WIDTH , 300);
		
		g.setColor(Color.BLUE);
		g.drawLine(Editor.WIDTH / 4, 0, Editor.WIDTH / 4, 600);
		g.drawLine(3 * Editor.WIDTH / 4, 0, 3 * Editor.WIDTH / 4, 600);
		g.drawLine(0, 150, Editor.WIDTH , 150);
		g.drawLine(0, 450, Editor.WIDTH , 450);
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 600, Editor.WIDTH, 600);
	}
	
	public void drawButtons(Graphics2D g) {
		DrawUtil.drawButton(g, CLEAR_BUTTON, false);
		g.setColor(Color.RED.darker());
		g.fillRect(CLEAR_BUTTON.x + 5, CLEAR_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(CLEAR_BUTTON.x + 10, CLEAR_BUTTON.y + 10, 20, 20);
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.WHITE);
		g.drawLine(CLEAR_BUTTON.x + 15, CLEAR_BUTTON.y + 15, CLEAR_BUTTON.x + 25, CLEAR_BUTTON.y + 25);
		g.drawLine(CLEAR_BUTTON.x + 25, CLEAR_BUTTON.y + 15, CLEAR_BUTTON.x + 15, CLEAR_BUTTON.y + 25);
		
		g.setStroke(new BasicStroke(0));
		DrawUtil.drawButton(g, DELETE_BUTTON, false);
		g.setColor(Color.RED.darker());
		g.fillRect(DELETE_BUTTON.x + 5, DELETE_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(DELETE_BUTTON.x + 10, DELETE_BUTTON.y + 10, 20, 20);
		g.setStroke(new BasicStroke(5));
		g.setColor(new Color(150, 0, 0));
		g.drawLine(DELETE_BUTTON.x + 15, DELETE_BUTTON.y + 15, DELETE_BUTTON.x + 25, DELETE_BUTTON.y + 25);
		g.drawLine(DELETE_BUTTON.x + 25, DELETE_BUTTON.y + 15, DELETE_BUTTON.x + 15, DELETE_BUTTON.y + 25);
		
		g.setStroke(new BasicStroke(0));
		DrawUtil.drawButton(g, SAVE_BUTTON, false);
		g.setColor(Color.GREEN.darker());
		g.fillRect(SAVE_BUTTON.x + 5, SAVE_BUTTON.y + 5, 30, 30);
		g.setColor(Color.GREEN);
		g.fillRect(SAVE_BUTTON.x + 10, SAVE_BUTTON.y + 10, 20, 20);
		g.setColor(Color.GREEN.darker());
		g.fillOval(SAVE_BUTTON.x + 15, SAVE_BUTTON.y + 15, 10, 10);
		
		g.setStroke(new BasicStroke(0));
		DrawUtil.drawButton(g, SAVE_NEW_BUTTON, false);
		g.setColor(Color.GREEN.darker());
		g.fillRect(SAVE_NEW_BUTTON.x + 5, SAVE_NEW_BUTTON.y + 5, 30, 30);
		g.setColor(Color.GREEN);
		g.fillRect(SAVE_NEW_BUTTON.x + 10, SAVE_NEW_BUTTON.y + 10, 20, 20);
		g.setColor(Color.GREEN.darker().darker());
		g.fillOval(SAVE_NEW_BUTTON.x + 12, SAVE_NEW_BUTTON.y + 12, 16, 16);
		g.setColor(Color.GREEN);
		g.fillOval(SAVE_NEW_BUTTON.x + 15, SAVE_NEW_BUTTON.y + 15, 10, 10);
		
		DrawUtil.drawButton(g, NEXT_LEVEL_BUTTON, false);
		g.setColor(Color.BLACK);
		Polygon rightArrow = new Polygon();
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 5, NEXT_LEVEL_BUTTON.y + 5);
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 5, NEXT_LEVEL_BUTTON.y + 35);
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 35, NEXT_LEVEL_BUTTON.y + 20);
		g.fill(rightArrow);
		
		
		DrawUtil.drawButton(g, PREVIOUS_LEVEL_BUTTON, false);
		g.setColor(Color.BLACK);
		Polygon leftArrow = new Polygon();
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 35, PREVIOUS_LEVEL_BUTTON.y + 5);
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 35, PREVIOUS_LEVEL_BUTTON.y + 35);
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 5, PREVIOUS_LEVEL_BUTTON.y + 20);
		g.fill(leftArrow);
		
		DrawUtil.drawButton(g, SEEK_LEVEL_BUTTON, false);
		g.setColor(Color.BLACK);
		g.fillOval(SEEK_LEVEL_BUTTON.x + 5, SEEK_LEVEL_BUTTON.y + 5, 22, 22);
		Polygon handle = new Polygon();
		handle.addPoint((int)(SEEK_LEVEL_BUTTON.x + 25 + 1.5 - 7.5), (int)(SEEK_LEVEL_BUTTON.y + 25 - 1.5 - 7.5));
		handle.addPoint((int)(SEEK_LEVEL_BUTTON.x + 25 - 1.5 - 7.5), (int)(SEEK_LEVEL_BUTTON.y + 25 + 1.5 - 7.5));
		handle.addPoint((int)(SEEK_LEVEL_BUTTON.x + 25 - 1.5 + 7.5), (int)(SEEK_LEVEL_BUTTON.y + 25 + 1.5 + 7.5));
		handle.addPoint((int)(SEEK_LEVEL_BUTTON.x + 25 + 1.5 + 7.5), (int)(SEEK_LEVEL_BUTTON.y + 25 - 1.5 + 7.5));
		g.fill(handle);
		g.setColor(new Color(0, 150, 255));
		g.fillOval(SEEK_LEVEL_BUTTON.x + 5 + 2, SEEK_LEVEL_BUTTON.y + 5 + 2, 18, 18);
	
		DrawUtil.drawButton(g, PLAY_LEVEL_BUTTON, false);
		g.setColor(Color.BLACK);
		Polygon playArrow = new Polygon();
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 5);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 35);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 35, PLAY_LEVEL_BUTTON.y + 20);
		g.draw(playArrow);
	}
	
	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		level.draw(g);
		
		drawGuideLines(g);
		
		if (entityEditor != null)
			entityEditor.draw(g);
		
		drawButtons(g);
		
		if (entitySelector != null)
			entitySelector.draw(g);
		
		typeSelector.draw(g);
	}
	
	private Entity getClickedEntity(MouseEvent e) {
		for (int check = 0; check < 4; check++) {
			Entity[] entities = null;
			if (check == 0)
				entities = level.getPlatforms();
			else if (check == 1)
				entities = level.getPoints();
			else if (check == 2)
				entities = level.getObstacles();
			else if (check == 3)
				entities = level.getItems();
			
			for (Entity entity : entities) {
				if (entity.getRect().contains(e.getPoint())) {
					return entity;
				}
			}
		}
		
		return null;
	}
	
	public void setToNextLevel() {
		if (levelCounter + 1 < levels.length) {
			levelCounter++;
			level = levels[levelCounter];
			if (level == null) {
				levels[levelCounter] = LevelCreator.createEmptyLevel("EditingLevel" + levelCounter, null);
				level = levels[levelCounter];
			}
			System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
		}
	}
	
	public void setToPreviousLevel() {
		if (levelCounter - 1 >= 0) {
			levelCounter--;
			level = levels[levelCounter];
			if (level == null) {
				levels[levelCounter] = LevelCreator.createEmptyLevel("EditingLevel" + levelCounter, null);
				level = levels[levelCounter];
			}
			System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (SAVE_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			LevelUtil.saveLevel(level);
		} else if (SAVE_NEW_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			LevelUtil.saveLevelAsNew(level);
		} else if (DELETE_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			boolean result = LevelUtil.removeLevel(level);
			if (result) {
				levels[levelCounter] = null;
				if (levelCounter - 1 > 0) {
					levelCounter--;
					level = levels[levelCounter];
				} else if (levelCounter + 1 < levels.length) {
					levelCounter++;
					level = levels[levelCounter];
				}
			}
		} else if (CLEAR_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			level = LevelCreator.createTrulyEmptyLevel(level.getLevelName(), null);
		} else if (NEXT_LEVEL_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			setToNextLevel();
		} else if (PREVIOUS_LEVEL_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			setToPreviousLevel();
		} else if (SEEK_LEVEL_BUTTON.contains(e.getPoint())) { // SEEK_LEVEL_BUTTON
			entitySelector = null;
			entityEditor = null;
			String levelToSeek = LevelUtil.promptUserForLevelOrderLevel();
			for (int i = 0; i < levels.length; i++) {
				if (levels[i] != null && levels[i].getLevelName().equals(levelToSeek)) {
					levelCounter = i;
					level = levels[i];
					System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
					break;
				}
			}
		} else if (PLAY_LEVEL_BUTTON.contains(e.getPoint())) {
			entitySelector = null;
			entityEditor = null;
			LevelUtil.playLevel(level);
		} 
		else if (typeSelector.mousePressed(e)) {
			entitySelector = typeSelector.getSelector();
			entityEditor = null;
		} else if (entitySelector instanceof MasterEntitySelector<?> entitySelector && entitySelector.mousePressed(e)) {
			entityEditor = entitySelector.getEditor();
		} else if (entitySelector instanceof LevelColorSelector entitySelector && entitySelector.mousePressed(e)) {
			entitySelector.setColor(this);
		}
		else if (entityEditor != null) {
			entityEditor.mousePressed(e);
		} else {
			Entity clickedEntity = getClickedEntity(e);
			
			if (clickedEntity != null)
				entityEditor = new MasterEntityEditor<>(clickedEntity, true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (entityEditor != null) {
			entityEditor.mouseReleased(e);
			
			if (entityEditor.getReady() && !entityEditor.getInLevel()) {
				LevelUtil.addObjectToLevel(level, entityEditor.getEntity());
				entityEditor.addedToLevel();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (entityEditor != null)
			entityEditor.keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && entityEditor != null) {
				LevelUtil.removeObjectFromLevel(level, entityEditor.getEntity());
			}
			entityEditor = null;
			if (entitySelector != null) {
				entitySelector.unfocus();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (entityEditor != null)
			entityEditor.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (entityEditor != null)
			entityEditor.keyTyped(e);
	}

	@Override
	public void setup() {
	}
	
}
