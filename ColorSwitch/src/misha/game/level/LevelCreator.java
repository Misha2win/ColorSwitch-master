/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.SpawnPoint;

public class LevelCreator {
	
	public final static String LEVEL_ORDER_STRING;
	
	static {
		String levelOrderString = null;
		
		try {
			levelOrderString = new String(Files.readAllBytes(Paths.get(LevelLoader.LEVEL_DIRECTORY + "LevelsOrder.levels")));
		} catch (IOException e) {
			System.err.println("Could not load LevelOrder.levels!");
			e.printStackTrace();
		}
		
		LEVEL_ORDER_STRING = levelOrderString;
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