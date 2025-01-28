/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.SpawnPoint;

public class LevelCreator {
	
	public static String LEVEL_ORDER_STRING;
	
	static {
		loadLevelOrderString();
	}
	
	public static void loadLevelOrderString() {
		String levelOrderString = null;
		
		try {
			levelOrderString = new String(Files.readAllBytes(Paths.get(LevelLoader.LEVEL_DIRECTORY + "LevelsOrder.levels")));
		} catch (IOException e) {
			System.err.println("Could not load LevelOrder.levels!");
			e.printStackTrace();
		}
		
		LEVEL_ORDER_STRING = levelOrderString.replaceAll("(\n|^)//.*?(?=\n|$)", "");
	}

	/**
	 * Create all of the levels in LevelCreator.LEVEL_ORDER_STRING
	 * 
	 * @param levelManager the LevelManager to assign all of the levels to
	 * @return an array of all the Levels in LevelCreator.LEVEL_ORDER_STRING
	 */
	public static Level[] createLevels(LevelManager levelManager) {
		ArrayList<Level> levels = new ArrayList<>();
		
		String[] lines = LEVEL_ORDER_STRING.split("\n");
		for (String line : lines) {
			if (line.startsWith("//")) {
				continue;
			}
			
			try {
				levels.add(LevelLoader.getLevel(levelManager, line));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		while (levels.size() < 50) {
			levels.add(null);
		}
		
		try {
			levels.set(levels.size() - 1, LevelLoader.getLevel(levelManager, "TestLevel"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return levels.toArray(new Level[levels.size()]);
	}
	
	/**
	 * Creates a level given its level string
	 * 
	 * @param levelManager the levelManager to put this level in
	 * @param levelName the name to give the level
	 * @param levelString the String of the level that says where everything in the level is
	 * @return the level made of levelString
	 */
	public static Level createLevel(LevelManager levelManager, String levelName, String levelString) {
		CSColor levelColor = null;
		LinkedList<Platform> platforms = new LinkedList<>();
		LinkedList<Obstacle> obstacles = new LinkedList<>();
		LinkedList<Point> points = new LinkedList<>();
		LinkedList<Item> items = new LinkedList<>();
		LinkedList<String> texts = new LinkedList<>();
		
		String[] lines = levelString.split("\n");
		for (String line : lines) {
			String[] parts = line.split(" ");
			
			if (line.startsWith("//") || line.length() == 0) {
				continue;
			} else if (parts[0].equals("text")) {
				texts.add(line.substring(5));
			} else if (parts[0].equals("level")) {
				levelColor = CSColor.getColorFromString(parts[1]);
			} else {
				try {
					Class<?> clazz = Class.forName("misha.game.level.entity." + parts[0] + "." + parts[1]);
					
					Constructor<?> constructor = clazz.getConstructors()[0];
					Class<?>[] parameters = constructor.getParameterTypes();
					
					Object[] objs = new Object[parameters.length];
					
					for (int i = 0; i < parameters.length; i++) {
						int partsIndex = 2 + i;
						
						if (parameters[i].equals(CSColor.class)) {
							objs[i] = CSColor.getColorFromString(parts[partsIndex]);
							
						} else if (parameters[i].equals(int.class)) {
							objs[i] = Integer.valueOf(parts[partsIndex]);
							
						} else if (parameters[i].equals(boolean.class)) {
							objs[i] = Boolean.valueOf(parts[partsIndex]);
							
						}
					}
					
					if (Platform.class.isAssignableFrom(clazz)) {
						platforms.add((Platform) constructor.newInstance(objs));
						
					} else if (Point.class.isAssignableFrom(clazz)) {
						points.add((Point) constructor.newInstance(objs));
						
					} else if (Obstacle.class.isAssignableFrom(clazz)) {
						obstacles.add((Obstacle) constructor.newInstance(objs));
						
					} else if (Item.class.isAssignableFrom(clazz)) {
						items.add((Item) constructor.newInstance(objs));
						
					}
				} catch (Exception e) {
					System.err.println("There was an issue trying to instantiate " + parts[1]);
					System.err.println("Problematic line was '" + line + "'");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
		return new Level(
				levelName,
				levelManager,
				levelColor, 
				platforms.toArray(new Platform[platforms.size()]),
				points.toArray(new Point[points.size()]),
				obstacles.toArray(new Obstacle[obstacles.size()]),
				items.toArray(new Item[items.size()]),
				texts.toArray(new String[texts.size()])
		);
	}
	
	/**
	 * Creates a level with 4 walls and the specified items
	 * 
	 * @param levelName the name of the Level to create
	 * @param levelManager the LevelManager to assign the Level to
	 * @param items the Items to put inside of the level
	 * @return level with 4 walls and the specified items
	 */
	public static Level createItemTestLevel(String levelName, LevelManager levelManager, Item... items) {
		return new Level(
				levelName,
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 730, 10),
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 730, 10, 10, 580),
						new Platform(CSColor.BLACK, 10, 580, 730, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				items,
				new String[] {
				}
		);
	}
	
	/**
	 * Creates a level with 4 walls, a SpawnPoint, and a GoalPoint, and optional text
	 * 
	 * @param levelName the name of the Level to create
	 * @param levelManager the LevelManager to assign the Level to
	 * @param text the text to put in the level
	 * @return a level with 4 walls, a SpawnPoint, and a GoalPoint, and optional text
	 */
	public static Level createEmptyLevel(String levelName, LevelManager levelManager, String... text) {
		return new Level(
				levelName,
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 730, 10),
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 730, 10, 10, 580),
						new Platform(CSColor.BLACK, 10, 580, 730, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
				},
				text
		);
	}
	
	/**
	 * Creates a level with nothing in it
	 * 
	 * @param levelName the name of the Level to create
	 * @param levelManager the LevelManager to assign the Level to
	 * @return a level with nothing in it
	 */
	public static Level createTrulyEmptyLevel(String levelName, LevelManager levelManager) {
		return new Level(
				levelName,
				levelManager,
				CSColor.GREEN,
				new Platform[] {
				},
				new Point[] {
				},
				new Obstacle[] {
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
}