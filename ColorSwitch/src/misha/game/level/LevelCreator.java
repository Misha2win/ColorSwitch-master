/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.File;
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
	
	public static String LEVEL_ORDER_STRING;
	
	static {
		try {
			LEVEL_ORDER_STRING = new String(Files.readAllBytes(Paths.get(LevelLoader.LEVEL_DIRECTORY + "LevelsOrder.levels")));
		} catch (IOException e) {
			System.err.println("Could not load LevelOrder.levels!");
			e.printStackTrace();
		}
		
		checkUnusedLevels();
	}
	
	private static void checkUnusedLevels() {
		System.out.println("Checking unused levels...");
		
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            String[] referencedLevels = LEVEL_ORDER_STRING.split("\n");
            for (File file : files) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(".level")) {
                	boolean referenced = false;
                	
                	for (String levelName : referencedLevels) {
                		if (levelName.equals(file.getName().replace(".level", ""))) {
                			referenced = true;
                			break;
                		}
                	}
                	
                	if (!referenced && !file.getName().equals("TestLevel.level"))
                		System.err.println("Not using level \"" + file.getName() + "\"");
                }
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
	}

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
			
//			levels.add(LevelLoader.getLevel(levelManager, "TestLevel"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return levels.toArray(new Level[levels.size()]);
	}
	
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