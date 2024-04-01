/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.File;
import java.util.ArrayList;

import misha.game.ColorSwitch;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.item.SizeChanger;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.SpawnPoint;

public class LevelCreator {
	
	static {
		checkUnusedLevels();
	}
	
	private static void checkUnusedLevels() {
		createLevels(null);
		
		System.out.println("Checking unused levels...");
		
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(".level")) {
                	if (!LevelLoader.isLoaded(file.getName())) {
                		System.err.println("Not using level \"" + file.getName() + "\"");
                	}
                }
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
	}

	public static Level[] createLevels(LevelManager levelManager) {
		ArrayList<Level> levels = new ArrayList<>(50);
		
		levels.add(LevelLoader.getLevel(levelManager, "Level0"));
		levels.add(LevelLoader.getLevel(levelManager, "ColorChanger1"));
		levels.add(LevelLoader.getLevel(levelManager, "ColorChanger2"));
		levels.add(LevelLoader.getLevel(levelManager, "ColorChanger3"));
		levels.add(LevelLoader.getLevel(levelManager, "Element1"));
		levels.add(LevelLoader.getLevel(levelManager, "Element2"));
		levels.add(LevelLoader.getLevel(levelManager, "Element3"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism6"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism1"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism2"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism4"));
		levels.add(LevelLoader.getLevel(levelManager, "Teleporter1"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror1"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror6"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror2"));
		levels.add(LevelLoader.getLevel(levelManager, "Teleporter2"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror3"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror4"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism5"));
		levels.add(LevelLoader.getLevel(levelManager, "Mirror7"));
		
		levels.add(createEmptyLevel(
				"ColorMixingText",
				levelManager,
				"Levels from this point on have a lot",
				"to do with additive color mixing.",
				"You can see how colors mix in the",
				"pause menu by pressing ESC."
		));
		
		levels.add(LevelLoader.getLevel(levelManager, "ColorMixer1"));
		levels.add(LevelLoader.getLevel(levelManager, "ColorMixer2"));
		levels.add(LevelLoader.getLevel(levelManager, "ColorChanger4"));
		levels.add(LevelLoader.getLevel(levelManager, "Prism7"));
		
		levels.add(createEmptyLevel(
				"IdeasText",
				levelManager, 
				"The levels beyond this level are ideas", 
				"that I am testing out. I made basic",
				"levels but I cannot come up with actual",
				"level ideas."
		));
		
		levels.add(LevelLoader.getLevel(levelManager, "PortalPoint1"));
		levels.add(LevelLoader.getLevel(levelManager, "SuperJump1"));
		
		levels.add(createItemTestLevel(
				"SizeChangerTest",
				levelManager, 
				new SizeChanger(ColorSwitch.WIDTH / 2 + 50, ColorSwitch.HEIGHT - 100, true),
				new SizeChanger(ColorSwitch.WIDTH / 2 - 50, ColorSwitch.HEIGHT - 100, false)
		));
		
		while (levels.size() < 50) {
			levels.add(null);
		}
		
		levels.set(levels.size() - 1, LevelLoader.getLevel(levelManager, "TestLevel"));

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
}