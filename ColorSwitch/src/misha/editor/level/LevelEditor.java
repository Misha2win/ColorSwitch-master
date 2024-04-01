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
import java.lang.reflect.Array;

import javax.swing.JOptionPane;

import misha.editor.Editor;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.item.ColorChangerEditor;
import misha.editor.level.entity.item.ColorMixerEditor;
import misha.editor.level.entity.item.DamagePackEditor;
import misha.editor.level.entity.item.HealthPackEditor;
import misha.editor.level.entity.item.MirrorEditor;
import misha.editor.level.entity.item.SuperJumpEditor;
import misha.editor.level.entity.item.TeleporterEditor;
import misha.editor.level.entity.obstacle.HealthPoolEditor;
import misha.editor.level.entity.obstacle.LavaEditor;
import misha.editor.level.entity.obstacle.PrismEditor;
import misha.editor.level.entity.obstacle.WaterEditor;
import misha.editor.level.entity.platform.HealthGateEditor;
import misha.editor.level.entity.platform.MovingPlatformEditor;
import misha.editor.level.entity.platform.PlatformEditor;
import misha.editor.level.entity.point.GoalPointEditor;
import misha.editor.level.entity.point.PortalPointEditor;
import misha.editor.level.entity.point.SpawnPointEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.item.Mirror;
import misha.game.level.entity.item.SuperJump;
import misha.game.level.entity.item.Teleporter;
import misha.game.level.entity.obstacle.Acid;
import misha.game.level.entity.obstacle.Lava;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.entity.obstacle.Water;
import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.PortalPoint;
import misha.game.level.entity.point.SpawnPoint;
import misha.game.level.entity.item.ColorChanger;
import misha.game.level.entity.item.ColorMixer;
import misha.game.level.entity.item.DamagePack;
import misha.game.level.entity.item.HealthPack;
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
	
	// Buttons for selecting platforms editors
	private static final Rectangle PLATFORM_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle MOVING_PLATFORM_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle HEALTH_GATE_BUTTON = new Rectangle(110, 660, 40, 40);
	
	// Buttons for selecting item editors
	private static final Rectangle COLOR_CHANGER_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle COLOR_MIXER_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle DAMAGE_PACK_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle HEALTH_PACK_BUTTON = new Rectangle(160, 660, 40, 40);
	private static final Rectangle MIRROR_BUTTON = new Rectangle(210, 660, 40, 40);
	private static final Rectangle SUPER_JUMP_BUTTON = new Rectangle(260, 660, 40, 40);
	private static final Rectangle TELEPORTER_BUTTON = new Rectangle(310, 660, 40, 40);
	
	// Buttons for selecting obstacle editors
	private static final Rectangle HEALTH_POOL_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle LAVA_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle WATER_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle PRISM_BUTTON = new Rectangle(160, 660, 40, 40);
	
	// Buttons for selecting point editors
	private static final Rectangle GOAL_POINT_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle PORTAL_POINT_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle SPAWN_POINT_BUTTON = new Rectangle(110, 660, 40, 40);
	
	// Buttons for selecting level color
	private static final Rectangle LEVEL_BLACK_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle LEVEL_RED_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle LEVEL_GREEN_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle LEVEL_BLUE_BUTTON = new Rectangle(160, 660, 40, 40);
	private static final Rectangle LEVEL_YELLOW_BUTTON = new Rectangle(210, 660, 40, 40);
	private static final Rectangle LEVEL_MAGENTA_BUTTON = new Rectangle(260, 660, 40, 40);
	private static final Rectangle LEVEL_CYAN_BUTTON = new Rectangle(310, 660, 40, 40);
	private static final Rectangle LEVEL_WHITE_BUTTON = new Rectangle(360, 660, 40, 40);
	
	// Misc. buttons
	private static final Rectangle CLEAR_BUTTON = new Rectangle(700, 610, 40, 40);
	private static final Rectangle PRINT_BUTTON = new Rectangle(700, 660, 40, 40);
	private static final Rectangle PLAY_LEVEL_BUTTON = new Rectangle(700, 710, 40, 40);
	private static final Rectangle NEXT_LEVEL_BUTTON = new Rectangle(700, 760, 40, 40);
	private static final Rectangle PREVIOUS_LEVEL_BUTTON = new Rectangle(650, 760, 40, 40);
	
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
	
	@SuppressWarnings("deprecation")
	public LevelEditor(Level level, int num) {
		if (level == null) {
			level = new Level("EditingLevel", CSColor.RED);
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
	
	public void draw(Graphics2D g) {
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
		
		level.draw(g);
		
		if (entityEditor != null)
			entityEditor.draw(g);
		
		drawButton(g, CLEAR_BUTTON);
		g.setColor(Color.RED.darker());
		g.fillRect(CLEAR_BUTTON.x + 5, CLEAR_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(CLEAR_BUTTON.x + 10, CLEAR_BUTTON.y + 10, 20, 20);
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.WHITE);
		g.drawLine(CLEAR_BUTTON.x + 15, CLEAR_BUTTON.y + 15, CLEAR_BUTTON.x + 25, CLEAR_BUTTON.y + 25);
		g.drawLine(CLEAR_BUTTON.x + 25, CLEAR_BUTTON.y + 15, CLEAR_BUTTON.x + 15, CLEAR_BUTTON.y + 25);
		
		g.setStroke(new BasicStroke(0)); // SUPER_PRINT_BUTTON
		drawButton(g, PRINT_BUTTON);
		g.setColor(Color.GREEN.darker());
		g.fillRect(PRINT_BUTTON.x + 5, PRINT_BUTTON.y + 5, 30, 30);
		g.setColor(Color.GREEN);
		g.fillRect(PRINT_BUTTON.x + 10, PRINT_BUTTON.y + 10, 20, 20);
		g.setColor(Color.GREEN.darker());
		g.fillOval(PRINT_BUTTON.x + 15, PRINT_BUTTON.y + 15, 10, 10);
		
		drawButton(g, PLATFORM_SELECT_BUTTON);
		if (selection == PLATFORMS) {
			drawHighlight(g, PLATFORM_SELECT_BUTTON);
		}
		g.setColor(Color.BLACK);
		g.fillRect(PLATFORM_SELECT_BUTTON.x + 5, PLATFORM_SELECT_BUTTON.y + 5, 30, 30);
		
		drawButton(g, ITEM_SELECT_BUTTON);
		if (selection == ITEMS) {
			drawHighlight(g, ITEM_SELECT_BUTTON);
		}
		g.setColor(Color.RED.darker());
		g.fillRect(ITEM_SELECT_BUTTON.x + 5, ITEM_SELECT_BUTTON.y + 5, 30, 30);
		g.setColor(Color.RED);
		g.fillRect(ITEM_SELECT_BUTTON.x + 10, ITEM_SELECT_BUTTON.y + 10, 20, 20);
		
		drawButton(g, OBSTACLE_SELECT_BUTTON);
		if (selection == OBSTACLES) {
			drawHighlight(g, OBSTACLE_SELECT_BUTTON);
		}
		g.setColor(Color.BLUE.darker());
		g.fillRect(OBSTACLE_SELECT_BUTTON.x + 5, OBSTACLE_SELECT_BUTTON.y + 5, 30, 30);
		
		drawButton(g, POINT_SELECT_BUTTON);
		if (selection == POINTS) {
			drawHighlight(g, POINT_SELECT_BUTTON);
		}
		g.setColor(Color.GREEN);
		g.fillOval(POINT_SELECT_BUTTON.x + 10, POINT_SELECT_BUTTON.y + 10, 20, 20);
		
		drawButton(g, COLOR_SELECT_BUTTON);
		if (selection == COLORS) {
			drawHighlight(g, COLOR_SELECT_BUTTON);
		}
		g.setColor(level.getLevelColor().getGraphicsColor());
		g.fillRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		if (level.getLevelColor().equals(CSColor.WHITE)) {
			g.setColor(Color.BLACK);
			g.drawRect(COLOR_SELECT_BUTTON.x + 10, COLOR_SELECT_BUTTON.y + 10, 20, 20);
		}
		
		drawButton(g, NEXT_LEVEL_BUTTON);
		g.setColor(Color.BLACK);
		Polygon rightArrow = new Polygon();
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 5, NEXT_LEVEL_BUTTON.y + 5);
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 5, NEXT_LEVEL_BUTTON.y + 35);
		rightArrow.addPoint(NEXT_LEVEL_BUTTON.x + 35, NEXT_LEVEL_BUTTON.y + 20);
		g.fill(rightArrow);
		
		
		drawButton(g, PREVIOUS_LEVEL_BUTTON);
		g.setColor(Color.BLACK);
		Polygon leftArrow = new Polygon();
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 35, PREVIOUS_LEVEL_BUTTON.y + 5);
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 35, PREVIOUS_LEVEL_BUTTON.y + 35);
		leftArrow.addPoint(PREVIOUS_LEVEL_BUTTON.x + 5, PREVIOUS_LEVEL_BUTTON.y + 20);
		g.fill(leftArrow);
		
		drawButton(g, PLAY_LEVEL_BUTTON);
		g.setColor(Color.BLACK);
		Polygon playArrow = new Polygon();
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 5);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 5, PLAY_LEVEL_BUTTON.y + 35);
		playArrow.addPoint(PLAY_LEVEL_BUTTON.x + 35, PLAY_LEVEL_BUTTON.y + 20);
		g.draw(playArrow);
		
		if (selection == PLATFORMS) {
			drawButton(g, PLATFORM_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(PlatformEditor.class)) {
				drawHighlight(g, PLATFORM_BUTTON);
			}
			g.setColor(Color.BLACK);
			g.fillRect(PLATFORM_BUTTON.x + 5, PLATFORM_BUTTON.y + 5, 30, 30);
			
			drawButton(g, MOVING_PLATFORM_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(MovingPlatformEditor.class)) {
				drawHighlight(g, MOVING_PLATFORM_BUTTON);
			}
			g.setColor(Color.BLACK);
			g.fillRect(MOVING_PLATFORM_BUTTON.x + 5, MOVING_PLATFORM_BUTTON.y + 5, 30, 30);
			g.setColor(Color.WHITE);
			g.drawOval(MOVING_PLATFORM_BUTTON.x + 15, MOVING_PLATFORM_BUTTON.y + 15, 6, 6);
			g.drawLine(MOVING_PLATFORM_BUTTON.x + 5, MOVING_PLATFORM_BUTTON.y + 5, MOVING_PLATFORM_BUTTON.x + 18, MOVING_PLATFORM_BUTTON.y + 18);
			
			drawButton(g, HEALTH_GATE_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(HealthGateEditor.class)) {
				drawHighlight(g, HEALTH_GATE_BUTTON);
			}
			g.setColor(Color.GRAY);
			g.fillRect(HEALTH_GATE_BUTTON.x + 5, HEALTH_GATE_BUTTON.y + 5, 30, 30);
			
		} else if (selection == OBSTACLES) {
			drawButton(g, HEALTH_POOL_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(HealthPoolEditor.class)) {
				drawHighlight(g, HEALTH_POOL_BUTTON);
			}
			g.setColor(Color.GREEN.darker());
			g.fillRect(HEALTH_POOL_BUTTON.x + 5, HEALTH_POOL_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LAVA_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(LavaEditor.class)) {
				drawHighlight(g, LAVA_BUTTON);
			}
			g.setColor(Color.RED.darker());
			g.fillRect(LAVA_BUTTON.x + 5, LAVA_BUTTON.y + 5, 30, 30);
			
			drawButton(g, WATER_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(WaterEditor.class)) {
				drawHighlight(g, WATER_BUTTON);
			}
			g.setColor(Color.BLUE.darker());
			g.fillRect(WATER_BUTTON.x + 5, WATER_BUTTON.y + 5, 30, 30);
			
			drawButton(g, PRISM_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(PrismEditor.class)) {
				drawHighlight(g, PRISM_BUTTON);
			}
			g.setColor(Color.GRAY);
			Polygon prism = new Polygon();
			prism.addPoint((int) (PRISM_BUTTON.x + 20), (int) (PRISM_BUTTON.y + 10));
			prism.addPoint((int) (PRISM_BUTTON.x + 30), (int) (PRISM_BUTTON.y + 30));
			prism.addPoint((int) (PRISM_BUTTON.x + 10), (int) (PRISM_BUTTON.y + 30));
			g.fill(prism);
		} else if (selection == POINTS) {
			drawButton(g, GOAL_POINT_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(GoalPointEditor.class)) {
				drawHighlight(g, GOAL_POINT_BUTTON);
			}
			g.setColor(Color.GREEN);
			g.fillOval(GOAL_POINT_BUTTON.x + 10, GOAL_POINT_BUTTON.y + 10, 20, 20);
			
			drawButton(g, PORTAL_POINT_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(PortalPointEditor.class)) {
				drawHighlight(g, PORTAL_POINT_BUTTON);
			}
			g.setColor(Color.BLUE);
			g.fillOval(PORTAL_POINT_BUTTON.x + 10, PORTAL_POINT_BUTTON.y + 10, 20, 20);
			
			drawButton(g, SPAWN_POINT_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(SpawnPointEditor.class)) {
				drawHighlight(g, SPAWN_POINT_BUTTON);
			}
			g.setColor(Color.RED);
			g.fillOval(SPAWN_POINT_BUTTON.x + 10, SPAWN_POINT_BUTTON.y + 10, 20, 20);
		} else if (selection == ITEMS) {
			drawButton(g, COLOR_CHANGER_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(ColorChangerEditor.class)) {
				drawHighlight(g, COLOR_CHANGER_BUTTON);
			}
			new ColorChanger(CSColor.RED, COLOR_CHANGER_BUTTON.x + 5, COLOR_CHANGER_BUTTON.y + 5).draw(g);
			
			drawButton(g, COLOR_MIXER_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(ColorMixerEditor.class)) {
				drawHighlight(g, COLOR_MIXER_BUTTON);
			}
			new ColorMixer(CSColor.RED, COLOR_MIXER_BUTTON.x + 5, COLOR_MIXER_BUTTON.y + 5, true).draw(g);
			
			drawButton(g, DAMAGE_PACK_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(DamagePackEditor.class)) {
				drawHighlight(g, DAMAGE_PACK_BUTTON);
			}
			new DamagePack(DAMAGE_PACK_BUTTON.x + 5, DAMAGE_PACK_BUTTON.y + 5).draw(g);
			
			drawButton(g, HEALTH_PACK_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(HealthPackEditor.class)) {
				drawHighlight(g, HEALTH_PACK_BUTTON);
			}
			new HealthPack(HEALTH_PACK_BUTTON.x + 5, HEALTH_PACK_BUTTON.y + 5).draw(g);
			
			drawButton(g, MIRROR_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(MirrorEditor.class)) {
				drawHighlight(g, MIRROR_BUTTON);
			}
			new Mirror(MIRROR_BUTTON.x + 5, MIRROR_BUTTON.y + 5, CSColor.GRAY, false).draw(g);
			
			drawButton(g, SUPER_JUMP_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(SuperJumpEditor.class)) {
				drawHighlight(g, SUPER_JUMP_BUTTON);
			}
			new SuperJump(SUPER_JUMP_BUTTON.x + 5, SUPER_JUMP_BUTTON.y + 5).draw(g);
			
			drawButton(g, TELEPORTER_BUTTON);
			if (entityEditor != null && entityEditor.getClass().equals(TeleporterEditor.class)) {
				drawHighlight(g, TELEPORTER_BUTTON);
			}
			new Teleporter(TELEPORTER_BUTTON.x + 5, TELEPORTER_BUTTON.y + 5, -1, -1).draw(g);
		} else if (selection == COLORS) {
			drawButton(g, LEVEL_BLACK_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.BLACK)) {
				drawHighlight(g, LEVEL_BLACK_BUTTON);
			}
			g.setColor(Color.BLACK);
			g.fillRect(LEVEL_BLACK_BUTTON.x + 5, LEVEL_BLACK_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_RED_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.RED)) {
				drawHighlight(g, LEVEL_RED_BUTTON);
			}
			g.setColor(Color.RED);
			g.fillRect(LEVEL_RED_BUTTON.x + 5, LEVEL_RED_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_GREEN_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.GREEN)) {
				drawHighlight(g, LEVEL_GREEN_BUTTON);
			}
			g.setColor(Color.GREEN);
			g.fillRect(LEVEL_GREEN_BUTTON.x + 5, LEVEL_GREEN_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_BLUE_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.BLUE)) {
				drawHighlight(g, LEVEL_BLUE_BUTTON);
			}
			g.setColor(Color.BLUE);
			g.fillRect(LEVEL_BLUE_BUTTON.x + 5, LEVEL_BLUE_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_YELLOW_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.YELLOW)) {
				drawHighlight(g, LEVEL_YELLOW_BUTTON);
			}
			g.setColor(Color.YELLOW);
			g.fillRect(LEVEL_YELLOW_BUTTON.x + 5, LEVEL_YELLOW_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_MAGENTA_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.MAGENTA)) {
				drawHighlight(g, LEVEL_MAGENTA_BUTTON);
			}
			g.setColor(Color.MAGENTA);
			g.fillRect(LEVEL_MAGENTA_BUTTON.x + 5, LEVEL_MAGENTA_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_CYAN_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.CYAN)) {
				drawHighlight(g, LEVEL_CYAN_BUTTON);
			}
			g.setColor(Color.CYAN);
			g.fillRect(LEVEL_CYAN_BUTTON.x + 5, LEVEL_CYAN_BUTTON.y + 5, 30, 30);
			
			drawButton(g, LEVEL_WHITE_BUTTON);
			if (this.level.getLevelColor().equals(CSColor.WHITE)) {
				drawHighlight(g, LEVEL_WHITE_BUTTON);
			}
			g.setColor(Color.WHITE);
			g.fillRect(LEVEL_WHITE_BUTTON.x + 5, LEVEL_WHITE_BUTTON.y + 5, 30, 30);
			g.setColor(Color.BLACK);
			g.drawRect(LEVEL_WHITE_BUTTON.x + 5, LEVEL_WHITE_BUTTON.y + 5, 30, 30);
		}
	}
	
	private void drawHighlight(Graphics2D g, Rectangle rect) {
		g.setColor(new Color(150, 150, 150));
		g.fillRect(rect.x + 1, rect.y + 1, rect.width - 1, rect.height - 1);
	}
	
	private void drawButton(Graphics2D g, Rectangle rect) {
		g.setColor(Color.WHITE);
		g.fill(rect);
		g.setColor(Color.BLACK);
		g.draw(rect);
	}
	
	@SuppressWarnings("deprecation")
	public void setLevelColor(CSColor color) {
		level.setLevelColor(color);
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
			
			entities = (add ? addToArray(entities, entity) : removeFromArray(entities, entity));
			
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
			level.setText((String[]) (add ? addToArray(level.getText(), (String) obj) : removeFromArray(level.getText(), (String) obj)));
		} else {
			throw new IllegalArgumentException("This Object is not an Entity or String!");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] addToArray(T[] arr, T obj) {
		Class<?> clazz = (obj.getClass().equals(Platform.class) || obj instanceof String) ? obj.getClass() : obj.getClass().getSuperclass();
		T[] newArray = (T[]) Array.newInstance(clazz, arr.length + 1);
		for (int i = 0; i < newArray.length - 1; i++) {
			newArray[i] = arr[i];
		}
		newArray[newArray.length - 1] = obj;
		return newArray;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] removeFromArray(T[] arr, T obj) {
		Class<?> clazz = (obj.getClass().equals(Platform.class) || obj instanceof String) ? obj.getClass() : obj.getClass().getSuperclass();
		T[] newArray = (T[]) Array.newInstance(clazz, arr.length - 1);
		for (int i = 0, j = 0; i < arr.length; i++) {
			if (arr[i] == obj) {
				j++;
			} else {
				if (i == arr.length - 1 && j == 0)
					return arr; // obj is not in arr!
					
				newArray[i - j] = arr[i];
			}
		}
		return newArray;
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

	@SuppressWarnings("deprecation")
	@Override
	public void mousePressed(MouseEvent e) {
		if (entityEditor == null) {
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
						// Platforms
						if (entity.getClass().equals(MovingPlatform.class))
							entityEditor = new MovingPlatformEditor(this, (MovingPlatform) entity);
						else if (entity.getClass().equals(HealthGate.class))
							entityEditor = new HealthGateEditor(this, (HealthGate) entity);
						else if (entity.getClass().equals(Platform.class))
							entityEditor = new PlatformEditor(this, (Platform) entity);
						// Obstacles
						else if (entity.getClass().equals(Acid.class))
							entityEditor = new HealthPoolEditor(this, (Acid) entity);
						else if (entity.getClass().equals(Lava.class))
							entityEditor = new LavaEditor(this, (Lava) entity);
						else if (entity.getClass().equals(Water.class))
							entityEditor = new WaterEditor(this, (Water) entity);
						else if (entity.getClass().equals(Prism.class))
							entityEditor = new PrismEditor(this, (Prism) entity);
						// Points
						else if (entity.getClass().equals(SpawnPoint.class))
							entityEditor = new SpawnPointEditor(this, (SpawnPoint) entity);
						else if (entity.getClass().equals(GoalPoint.class))
							entityEditor = new GoalPointEditor(this, (GoalPoint) entity);
						else if (entity.getClass().equals(PortalPoint.class))
							entityEditor = new PortalPointEditor(this, (PortalPoint) entity);
						// Items
						else if (entity.getClass().equals(ColorChanger.class))
							entityEditor = new ColorChangerEditor(this, (ColorChanger) entity);
						else if (entity.getClass().equals(ColorMixer.class))
							entityEditor = new ColorMixerEditor(this, (ColorMixer) entity);
						else if (entity.getClass().equals(DamagePack.class))
							entityEditor = new DamagePackEditor(this, (DamagePack) entity);
						else if (entity.getClass().equals(HealthPack.class))
							entityEditor = new HealthPackEditor(this, (HealthPack) entity);
						else if (entity.getClass().equals(Mirror.class))
							entityEditor = new MirrorEditor(this, (Mirror) entity);
						else if (entity.getClass().equals(SuperJump.class))
							entityEditor = new SuperJumpEditor(this, (SuperJump) entity);
						else if (entity.getClass().equals(Teleporter.class))
							entityEditor = new TeleporterEditor(this, (Teleporter) entity);
				
						
						if (entityEditor != null) {
							entityEditor.mousePressed(e);
						}
						
						break;
					}
				}
			}
		} else {
			entityEditor.mousePressed(e);
		}
		
		if (PRINT_BUTTON.contains(e.getPoint())) {
			String name = level.getLevelName();
			if (name.startsWith("EditingLevel")) {
				name = JOptionPane.showInputDialog("What should the name of this level be?");
			}
				
			System.out.println("Saving level as: " + name + ".level");
			LevelSaver.saveLevel(level, name);
		} else if (CLEAR_BUTTON.contains(e.getPoint())) {
			level = new Level(level.getLevelName(), level.getLevelColor());
		} else if (NEXT_LEVEL_BUTTON.contains(e.getPoint())) {
			if (levelCounter + 1 < levels.length) {
				levelCounter++;
				level = levels[levelCounter];
				if (level == null) {
					level = new Level("EditingLevel" + levelCounter, CSColor.GREEN);
					level.setPlatforms(new Platform[] {
							new Platform(CSColor.BLACK, 10, 10, 10, 580),
							new Platform(CSColor.BLACK, 20, 580, 720, 10),
							new Platform(CSColor.BLACK, 20, 10, 720, 10),
							new Platform(CSColor.BLACK, 730, 20, 10, 560)	
					});
					levels[levelCounter] = level;
				}
				System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
			}
		} else if (PREVIOUS_LEVEL_BUTTON.contains(e.getPoint())) {
			if (levelCounter - 1 >= 0) {
				levelCounter--;
				level = levels[levelCounter];
				if (level == null) {
					level = new Level("EditingLevel" + levelCounter, CSColor.GREEN);
					level.setPlatforms(new Platform[] {
							new Platform(CSColor.BLACK, 10, 10, 10, 580),
							new Platform(CSColor.BLACK, 20, 580, 720, 10),
							new Platform(CSColor.BLACK, 20, 10, 720, 10),
							new Platform(CSColor.BLACK, 730, 20, 10, 560)	
					});
					levels[levelCounter] = level;
				}
				System.out.println("Currently editing level: " + levelCounter + " (" + level.getLevelName() + ")");
			}
		} else if (PLAY_LEVEL_BUTTON.contains(e.getPoint())) {
			LevelSaver.saveLevel(level, "EditingLevel");
			
			LevelManager levelManager = new LevelManager(LevelLoader.loadLevel(null, "EditingLevel"));
			levelManager.getCurrentLevel().setLevelManager(levelManager);
			
			new Thread(() -> {
				new ColorSwitch(levelManager);
			}).start();
		}
		
		if (PLATFORM_SELECT_BUTTON.contains(e.getPoint())) {
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
			if (PLATFORM_BUTTON.contains(e.getPoint())) {
				entityEditor = new	PlatformEditor(this, null);
			} else if (MOVING_PLATFORM_BUTTON.contains(e.getPoint())) {
				entityEditor = new MovingPlatformEditor(this, null);
			} else if (HEALTH_GATE_BUTTON.contains(e.getPoint())) {
				entityEditor = new HealthGateEditor(this, null);
			}
		} else if (selection == OBSTACLES) {
			if (HEALTH_POOL_BUTTON.contains(e.getPoint())) {
				entityEditor = new HealthPoolEditor(this, null);
			} else if (LAVA_BUTTON.contains(e.getPoint())) {
				entityEditor = new LavaEditor(this, null);
			} else if (WATER_BUTTON.contains(e.getPoint())) {
				entityEditor = new WaterEditor(this, null);
			} else if (PRISM_BUTTON.contains(e.getPoint())) {
				entityEditor = new PrismEditor(this, null);
			}
		} else if (selection == POINTS) {
			if (GOAL_POINT_BUTTON.contains(e.getPoint())) {
				entityEditor = new GoalPointEditor(this, null);
			} else if (PORTAL_POINT_BUTTON.contains(e.getPoint())) {
				entityEditor = new PortalPointEditor(this, null);
			} else if (SPAWN_POINT_BUTTON.contains(e.getPoint())) {
				entityEditor = new SpawnPointEditor(this, null);
			}
		} else if (selection == ITEMS) {
			if (COLOR_CHANGER_BUTTON.contains(e.getPoint())) {
				entityEditor = new ColorChangerEditor(this, null);
			} else if (COLOR_MIXER_BUTTON.contains(e.getPoint())) {
				entityEditor = new ColorMixerEditor(this, null);
			} else if (DAMAGE_PACK_BUTTON.contains(e.getPoint())) {
				entityEditor = new DamagePackEditor(this, null);
			} else if (HEALTH_PACK_BUTTON.contains(e.getPoint())) {
				entityEditor = new HealthPackEditor(this, null);
			} else if (MIRROR_BUTTON.contains(e.getPoint())) {
				entityEditor = new MirrorEditor(this, null);
			} else if (SUPER_JUMP_BUTTON.contains(e.getPoint())) {
				entityEditor = new SuperJumpEditor(this, null);
			} else if (TELEPORTER_BUTTON.contains(e.getPoint())) {
				entityEditor = new TeleporterEditor(this, null);
			}
		} else if (selection == COLORS) {
			if (LEVEL_BLACK_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.BLACK);
			} else if (LEVEL_RED_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.RED);
			} else if (LEVEL_GREEN_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.GREEN);
			} else if (LEVEL_BLUE_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.BLUE);
			} else if (LEVEL_YELLOW_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.YELLOW);
			} else if (LEVEL_MAGENTA_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.MAGENTA);
			} else if (LEVEL_CYAN_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.CYAN);
			} else if (LEVEL_WHITE_BUTTON.contains(e.getPoint())) {
				level.setLevelColor(CSColor.WHITE);
			}
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
