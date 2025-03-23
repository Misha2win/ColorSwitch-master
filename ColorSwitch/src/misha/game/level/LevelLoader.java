/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import misha.editor.utility.Util;
import misha.game.ColorSwitch;

public final class LevelLoader {
	
	public static final String LEVEL_DIRECTORY = "./resources/level/";
	public static final String LEVELS_DIRECTORY = "./resources/level/levels/";
	public static final String LEVEL_EXTENSION = ".level";
	
	/**
	 * HashMap of level names and their corresponding level Strings
	 */
	private static final HashMap<String, String> LEVEL_STRINGS = new HashMap<>();
	
	static {
		File parentFile = LevelLoader.getJarParentDirectory();
		if (parentFile != null) {
			try {
				File outputFile = new File(parentFile, "output.log");
				PrintStream newPrintStream = new PrintStream(new FileOutputStream(outputFile, true));
				System.out.println("This is being executed as a jar file! Setting the printstream to a custom stream!");
				System.setOut(newPrintStream);
				System.setErr(newPrintStream);
				System.out.println("This program is executing as a .jar, so this is no where the outputs will be written to!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
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
		File parentFile = getJarParentDirectory();
        
        if (parentFile != null) {
        	return new String(Files.readAllBytes(new File(parentFile, directory + levelName).toPath()));
        }
        
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
		
		String[] referencedLevels = LevelCreator.LEVEL_ORDER_STRING.split("\\r?\\n");
		System.out.println(Arrays.toString(referencedLevels));
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
		
		File parentDirectory = getJarParentDirectory();
		if (parentDirectory != null) {
			directory = new File(parentDirectory, LevelLoader.LEVELS_DIRECTORY);
		}

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(LEVEL_EXTENSION)) {
                	System.out.println("Loading file \'" + file.getName() + "\'");
                	try {
                		LEVEL_STRINGS.put(file.getName(), LevelLoader.loadLevelString(file.getName()));
					} catch (IOException e) {
						System.err.println("There was an issue loading the level String of " + file.getName());
						e.printStackTrace();
					}
                }
            }
        } else {
            System.err.println("Directory '" + directory.getAbsolutePath() + "' does not exist or is not a directory.");
        }
        System.out.println("Done! Loaded " + LEVEL_STRINGS.size() + " levels!");
	}
	
	public static File getJarParentDirectory() {
		try {
        	CodeSource codeSource = ColorSwitch.class.getProtectionDomain().getCodeSource();
			File location = new File(codeSource.getLocation().toURI());
			if (location.isFile() && location.getName().endsWith(".jar")) {
				return location.getParentFile();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}

}
