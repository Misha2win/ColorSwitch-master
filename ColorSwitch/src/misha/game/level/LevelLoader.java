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
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

import misha.editor.utility.Util;

public final class LevelLoader {
	
	public static final String LEVEL_DIRECTORY = "./resources/level/";
	public static final String LEVELS_DIRECTORY = "./resources/level/levels/";
	public static final String LEVEL_EXTENSION = ".level";
	
	/**
	 * HashMap of level names and their corresponding level Strings
	 */
	private static final HashMap<String, String> LEVEL_STRINGS = new HashMap<>();
	
	static {
		loadAllLevelStrings();
		checkUnusedLevels();
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
		
		return LevelCreator.createLevel(levelManager, levelName.replace(".level", ""), LEVEL_STRINGS.get(levelName));
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
		
		return LevelCreator.createLevel(levelManager, levelName.replace(".level", ""), LEVEL_STRINGS.get(levelName));
	}
	
	public static String[] getUnreferencedLevelNames() {
		LinkedList<String> names = new LinkedList<>();
		
		String[] referencedLevels = LevelCreator.LEVEL_ORDER_STRING.split("\n");
		for (String loadedLevel : LEVEL_STRINGS.keySet()) {
			loadedLevel = loadedLevel.replace(LEVEL_EXTENSION, "");
			
			if (loadedLevel.equals("TestLevel"))
				continue;
			
			if (!Util.contains(referencedLevels, loadedLevel)) {
				names.add(loadedLevel);
			}
		}
		
		return names.toArray(new String[names.size()]);
	}
	
	/**
	 * Checks and prints all levels that are not referenced in the LevelCreator.LEVEL_ORDER_STRING
	 */
	private static void checkUnusedLevels() {
		System.out.println("Checking unused levels...");
		
		for (String unreferencedLevel : getUnreferencedLevelNames()) {
			System.err.println("Not using \"" + unreferencedLevel + "\"");
		}
	}
	
	private static void loadAllLevelStrings() {
		System.out.println("Loading all levels in directory '" + LevelLoader.LEVEL_DIRECTORY + "'");
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(LEVEL_EXTENSION)) {
                	try {
                		LEVEL_STRINGS.put(file.getName(), LevelLoader.loadLevelString(file.getName()));
					} catch (IOException e) {
						System.err.println("There was an issue loading the level String of " + file.getName());
						e.printStackTrace();
					}
                }
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
        System.out.println("Done! Loaded " + LEVEL_STRINGS.size() + " levels!");
	}

}
