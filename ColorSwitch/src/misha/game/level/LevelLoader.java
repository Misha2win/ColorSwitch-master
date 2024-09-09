/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;

public final class LevelLoader {
	
	public static final String LEVEL_DIRECTORY = "./resources/level/";
	public static final String LEVELS_DIRECTORY = "./resources/level/levels/";
	public static final String LEVEL_EXTENSION = ".level";
	
	private static final HashMap<String, String> LEVEL_STRINGS = new HashMap<>();
	
	private static Level createLevel(LevelManager levelManager, String levelName, String levelString) {
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
					e.printStackTrace();
				}
			}
		}
		
//		MasterEntityEditor<ColorChanger> mee = new MasterEntityEditor<>(ColorChanger.class);
//		items.add(mee.getEntity());
		
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
	
	private static String loadLevelString(String directory, String levelName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(directory + levelName)));
	}
	
	private static String loadLevelString(String levelName) throws IOException {
		return loadLevelString(LEVELS_DIRECTORY , levelName);
	}
	
	public static boolean loadAllLevels() {
		File directory = new File(LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden()) {
                	try {
                		getLevel(null, file.getName());
                	} catch (IOException e) {
                		System.err.println(file.getName() + " is not a level!");
                		e.printStackTrace();
                	}
                }
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
		
		return true;
	}
	
	public static boolean isLoaded(String levelName) {
		if (!levelName.endsWith(LEVEL_EXTENSION))
			levelName += LEVEL_EXTENSION;
		
		return LEVEL_STRINGS.containsKey(levelName);
	}
	
	public static Level loadLevel(LevelManager levelManager, String levelName) throws IOException {
		if (!levelName.endsWith(LEVEL_EXTENSION))
			levelName += LEVEL_EXTENSION;
		
		String levelString = loadLevelString(levelName);
		
		if (levelString != null) {
			LEVEL_STRINGS.put(levelName, levelString);
		} else {
			throw new NoSuchFileException("There was an issue reading level " + levelName);
		}
		
		return createLevel(levelManager, levelName.replace(".level", ""), LEVEL_STRINGS.get(levelName));
	}
	
	public static Level getLevel(LevelManager levelManager, String levelName)  throws IOException {
		if (!levelName.endsWith(LEVEL_EXTENSION))
			levelName += LEVEL_EXTENSION;
		
		if (!LEVEL_STRINGS.containsKey(levelName)) {
			return loadLevel(levelManager, levelName);
		}
		
		return createLevel(levelManager, levelName.replace(".level", ""), LEVEL_STRINGS.get(levelName));
	}

}
