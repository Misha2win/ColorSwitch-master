/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.swing.JOptionPane;
import misha.editor.DrawUtil;
import misha.editor.Editor;
import misha.editor.Util;
import misha.editor.level.entity.EntityEditor;
import misha.editor.selector.ItemSelector;
import misha.editor.selector.LevelColorSelector;
import misha.editor.selector.ObstacleSelector;
import misha.editor.selector.PlatformSelector;
import misha.editor.selector.PointSelector;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;
import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelCreator;
import misha.game.level.LevelLoader;
import misha.game.level.LevelManager;
import misha.game.level.LevelSaver;

public class LevelEditor implements KeyListener, MouseListener, MouseMotionListener {
	
	// Buttons for selecting the button selectors
	private static final Rectangle PLATFORM_SELECT_BUTTON = new Rectangle(10, 610, 40, 40);
	private static final Rectangle ITEM_SELECT_BUTTON = new Rectangle(60, 610, 40, 40);
	private static final Rectangle OBSTACLE_SELECT_BUTTON = new Rectangle(110, 610, 40, 40);
	private static final Rectangle POINT_SELECT_BUTTON = new Rectangle(160, 610, 40, 40);
	private static final Rectangle COLOR_SELECT_BUTTON = new Rectangle(210, 610, 40, 40);
	
	// Misc. buttons
	private static final Rectangle CLEAR_BUTTON = new Rectangle(700, 610, 40, 40);
	private static final Rectangle PRINT_BUTTON = new Rectangle(700, 660, 40, 40);
	private static final Rectangle PLAY_LEVEL_BUTTON = new Rectangle(700, 710, 40, 40);
	private static final Rectangle NEXT_LEVEL_BUTTON = new Rectangle(700, 760, 40, 40);
	private static final Rectangle PREVIOUS_LEVEL_BUTTON = new Rectangle(650, 760, 40, 40);
	
	// Selectors for level entities and level color
	private final PlatformSelector platformSelector = new PlatformSelector();
	private final ObstacleSelector obstacleSelector = new ObstacleSelector();
	private final ItemSelector itemSelector = new ItemSelector();
	private final PointSelector pointSelector = new PointSelector();
	private final LevelColorSelector levelColorSelector = new LevelColorSelector();
	
	private static final int NOTHING = 0;
	private static final int PLATFORMS = 1;
	private static final int OBSTACLES = 2;
	private static final int POINTS = 3;
	private static final int ITEMS = 4;
	private static final int COLORS = 5;
	private int selection;
	
	private EntityEditor<?> entityEditor;
	
	private int levelCounter;
	private Level[] levels;
	private Level level;
	
	public LevelEditor(Level level) {
		this(level, -1);
	}
	
	public LevelEditor(Level level, int num) {
		if (level == null) {
			level = LevelCreator.createEmptyLevel("EditingLevel", null);
		} else {
			this.level = level;
		}
		
		this.levels = LevelCreator.createLevels(null);
		this.levelCounter = num;
		this.selection = NOTHING;
		
		System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
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
	}
	
	public void draw(Graphics2D g) {
		level.draw(g);
		
		drawGuideLines(g);
		
		if (entityEditor != null)
			entityEditor.draw(g);
		
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
		DrawUtil.drawButton(g, PRINT_BUTTON, false);
		g.setColor(Color.GREEN.darker());
		g.fillRect(PRINT_BUTTON.x + 5, PRINT_BUTTON.y + 5, 30, 30);
		g.setColor(Color.GREEN);
		g.fillRect(PRINT_BUTTON.x + 10, PRINT_BUTTON.y + 10, 20, 20);
		g.setColor(Color.GREEN.darker());
		g.fillOval(PRINT_BUTTON.x + 15, PRINT_BUTTON.y + 15, 10, 10);
		
		DrawUtil.drawButton(g, PLATFORM_SELECT_BUTTON, selection == PLATFORMS);
		g.setColor(Color.BLACK);
		g.fillRect(PLATFORM_SELECT_BUTTON.x + 5, PLATFORM_SELECT_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, ITEM_SELECT_BUTTON, selection == ITEMS);
		g.setColor(Color.RED.darker());
		g.fillRect(ITEM_SELECT_BUTTON.x + 5, ITEM_SELECT_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(ITEM_SELECT_BUTTON.x + 10, ITEM_SELECT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, OBSTACLE_SELECT_BUTTON, selection == OBSTACLES);
		g.setColor(Color.BLUE.darker());
		g.fillRect(OBSTACLE_SELECT_BUTTON.x + 5, OBSTACLE_SELECT_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, POINT_SELECT_BUTTON, selection == POINTS);
		g.setColor(Color.GREEN);
		g.fillOval(POINT_SELECT_BUTTON.x + 10, POINT_SELECT_BUTTON.y + 10, 20, 20);
		
		DrawUtil.drawButton(g, COLOR_SELECT_BUTTON, selection == COLORS);
		g.setColor(level.getLevelColor().getGraphicsColor());
		g.fillRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		if (level.getLevelColor().equals(CSColor.WHITE)) {
			g.setColor(Color.BLACK);
			g.drawRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		}
		
		
		
		
		
		
		
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
		
		DrawUtil.drawButton(g, PLAY_LEVEL_BUTTON, false);
		g.setColor(Color.BLACK);
		Polygon playArrow = new Polygon();
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 5);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 35);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 35, PLAY_LEVEL_BUTTON.y + 20);
		g.draw(playArrow);
		
		if (selection == PLATFORMS) {
			platformSelector.draw(g);
		} else if (selection == OBSTACLES) {
			obstacleSelector.draw(g);
		} else if (selection == POINTS) {
			pointSelector.draw(g);
		} else if (selection == ITEMS) {
			itemSelector.draw(g);
		} else if (selection == COLORS) {
			levelColorSelector.draw(g);
		}
	}
	
	public void addObjectToLevel(Object obj) {
		modifyLevelContent(obj, true);
	}
	
	public void removeObjectFromLevel(Object obj) {
		modifyLevelContent(obj, false);
	}
	
	@SuppressWarnings("deprecation")
	private void modifyLevelContent(Object obj, boolean add) {
		if (obj == null)
			return;
		
		if (obj instanceof Entity) {
			Entity entity = (Entity) obj;
			
			Entity[] entities = null;
			if (entity instanceof Platform) {
				entities = level.getPlatforms();
			} else if (entity instanceof Point) {
				entities = level.getPoints();
			} else if (entity instanceof Obstacle) {
				entities = level.getObstacles();
			} else if (entity instanceof Item) {
				entities = level.getItems();
			}
			
			entities = (add ? Util.addToArray(entities, entity) : Util.removeFromArray(entities, entity));
			
			if (entity instanceof Platform) {
				level.setPlatforms((Platform[]) entities);
			} else if (entity instanceof Point) {
				level.setPoints((Point[]) entities);
			} else if (entity instanceof Obstacle) {
				level.setObstacles((Obstacle[]) entities);
			} else if (entity instanceof Item) {
				level.setItems((Item[]) entities);
			}
		} else if (obj instanceof String) {
			level.setText((String[]) (add ? Util.addToArray(level.getText(), (String) obj) : Util.removeFromArray(level.getText(), (String) obj)));
		} else {
			throw new IllegalArgumentException("This Object is not an Entity or String!");
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
			
			if (entities == null)
				continue;
			
			for (Entity entity : entities) {
				if (entity.getRect().contains(e.getPoint())) {
					return entity;
				}
			}
		}
		
		return null;
	}
	
	public void saveLevel() {
		String name = level.getLevelName();
		if (name.startsWith("EditingLevel")) {
			name = JOptionPane.showInputDialog("What should the name of this level be?");
		}
			
		System.out.println("Saving level as: " + name + ".level");
		LevelSaver.saveLevel(level, name);
	}
	
	public void setToNextLevel() {
		if (levelCounter + 1 < levels.length) {
			levelCounter++;
			level = levels[levelCounter];
			if (level == null) {
				levels[levelCounter] = LevelCreator.createEmptyLevel("EditingLevel" + levelCounter, null);
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
			}
			System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void playCurrentLevel() {
		LevelSaver.saveLevel(level, LevelLoader.LEVEL_DIRECTORY, "EditingLevel");
		
		LevelManager levelManager;
		try {
			levelManager = new LevelManager(LevelLoader.loadLevel(null, "../EditingLevel"));
			levelManager.getCurrentLevel().setLevelManager(levelManager);
			
			new Thread(() -> {
				new ColorSwitch(levelManager);
			}).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void findSelectorToDraw() {
		Type superclass = entityEditor.getClass().getGenericSuperclass();
		if (superclass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superclass;
			
			Type[] typeArguments = parameterizedType.getActualTypeArguments();

			if (typeArguments.length == 1) {
				Type type = typeArguments[0];
				
				if (type instanceof Class<?>) {
					if (Platform.class.isAssignableFrom((Class<?>) type)) {
						selection = PLATFORMS;
	                } else if (Obstacle.class.isAssignableFrom((Class<?>) type)) {
						selection = OBSTACLES;
	                } else if (Point.class.isAssignableFrom((Class<?>) type)) {
						selection = POINTS;
	                } else if (Item.class.isAssignableFrom((Class<?>) type)) {
						selection = ITEMS;
	                }
				}
			} else {
				throw new IllegalStateException("entityEditor either has no parameterized types or has multiple!");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (entityEditor == null) {
			Entity clickedEntity = getClickedEntity(e);
			
			if (clickedEntity != null) {
				entityEditor = clickedEntity.getEntityEditor(this);
				entityEditor.mousePressed(e);
				
				findSelectorToDraw();
			}
		} else {
			entityEditor.mousePressed(e);
		}
		
		if (PRINT_BUTTON.contains(e.getPoint())) {
			saveLevel();
		} else if (CLEAR_BUTTON.contains(e.getPoint())) {
			level = LevelCreator.createTrulyEmptyLevel(level.getLevelName(), null);
		} else if (NEXT_LEVEL_BUTTON.contains(e.getPoint())) {
			setToNextLevel();
		} else if (PREVIOUS_LEVEL_BUTTON.contains(e.getPoint())) {
			setToPreviousLevel();
		} else if (PLAY_LEVEL_BUTTON.contains(e.getPoint())) {
			playCurrentLevel();
		} else if (PLATFORM_SELECT_BUTTON.contains(e.getPoint())) {
			selection = PLATFORMS;
			entityEditor = null;
		} else if (ITEM_SELECT_BUTTON.contains(e.getPoint())) {
			selection = ITEMS;
			entityEditor = null;
		} else if (OBSTACLE_SELECT_BUTTON.contains(e.getPoint())) {
			selection = OBSTACLES;
			entityEditor = null;
		} else if (POINT_SELECT_BUTTON.contains(e.getPoint())) {
			selection = POINTS;
			entityEditor = null;
		} else if (COLOR_SELECT_BUTTON.contains(e.getPoint())) {
			selection = COLORS;
			entityEditor = null;
		}
		
		if (selection == PLATFORMS) {
			EntityEditor<?> editor = platformSelector.getEditor(this, level.getPlatforms(), e.getPoint());
			if (editor != null && (entityEditor == null || entityEditor.getStoredEntity() == null))
				entityEditor = editor;
 		} else if (selection == OBSTACLES) {
 			EntityEditor<?> editor = obstacleSelector.getEditor(this, level.getObstacles(), e.getPoint());
			if (editor != null && (entityEditor == null || entityEditor.getStoredEntity() == null)) {
				entityEditor = editor;
			}
		} else if (selection == POINTS) {
			EntityEditor<?> editor = pointSelector.getEditor(this, level.getPoints(), e.getPoint());
			if (editor != null && (entityEditor == null || entityEditor.getStoredEntity() == null)) {
				entityEditor = editor;
			}
		} else if (selection == ITEMS) {
			EntityEditor<?> editor = itemSelector.getEditor(this, level.getItems(), e.getPoint());
			if (editor != null && (entityEditor == null || entityEditor.getStoredEntity() == null))
				entityEditor = editor;
		} else if (selection == COLORS) {
			levelColorSelector.setColor(this, e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (entityEditor != null)
			entityEditor.mouseReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (entityEditor != null)
			entityEditor.keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			entityEditor = null;
			selection = NOTHING;
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
	
}
