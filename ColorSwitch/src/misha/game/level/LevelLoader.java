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
	
	static {
		checkUnusedLevels();
	}
	
	public static final String LEVEL_DIRECTORY = "./resources/level/";
	public static final String LEVELS_DIRECTORY = "./resources/level/levels/";
	public static final String LEVEL_EXTENSION = ".level";
	
	/**
	 * HashMap of level names and their corresponding level Strings
	 */
	private static final HashMap<String, String> LEVEL_STRINGS = new HashMap<>();
	
	/**
	 * Creates a level given its level string
	 * 
	 * @param levelManager the levelManager to put this level in
	 * @param levelName the name to give the level
	 * @param levelString the String of the level that says where everything in the level is
	 * @return the level made of levelString
	 */
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
	 * Loads the level String of a level provided the level's name
	 * 
	 * @param directory the directory to look for the level in
	 * @param levelName the name of the level to load the level string of
	 * @return the level String of the level
	 * @throws IOException if there is no level with the provided name
	 */
	private static String loadLevelString(String directory, String levelName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(directory + levelName)));
	}
	
	/**
	 * Loads the level String of a level provided the level's name
	 * 
	 * @param levelName the name of the level to load
	 * @return the level String of the level
	 * @throws IOException if there is no level with the provided name
	 */
	private static String loadLevelString(String levelName) throws IOException {
		return loadLevelString(LEVELS_DIRECTORY , levelName);
	}
	
	/**
	 * Loads a level provided its name
	 * 
	 * @param levelManager the LevelManager to assign this level to
	 * @param levelName the name of the level to load
	 * @return the level with the provided name
	 * @throws IOException if no level with the provided name is found
	 */
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
	
	/**
	 * Gets or loads a level provided its name
	 * 
	 * @param levelManager the LevelManager to assign this level to
	 * @param levelName the name of the Level to get or load
	 * @return the level with the provided name
	 * @throws IOException if no level with the provided name is found
	 */
	public static Level getLevel(LevelManager levelManager, String levelName)  throws IOException {
		if (!levelName.endsWith(LEVEL_EXTENSION))
			levelName += LEVEL_EXTENSION;
		
		if (!LEVEL_STRINGS.containsKey(levelName)) {
			return loadLevel(levelManager, levelName);
		}
		
		return createLevel(levelManager, levelName.replace(".level", ""), LEVEL_STRINGS.get(levelName));
	}
	
	/**
	 * Checks and prints all levels that are not referenced in the LevelCreator.LEVEL_ORDER_STRING.
	 * Ignoring all lines that start are commented
	 */
	private static void checkUnusedLevels() {
		System.out.println("Checking unused levels...");
		
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            String[] referencedLevels = LevelCreator.LEVEL_ORDER_STRING.split("\n");
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

}
