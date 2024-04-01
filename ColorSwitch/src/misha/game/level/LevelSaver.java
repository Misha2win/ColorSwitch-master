/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;

public final class LevelSaver {

	private static String getLevelString(Level level) {
		StringBuilder string = new StringBuilder();
		
		string.append("// Level color\n");
		string.append("level " + CSColor.getStringFromColor(level.getLevelColor()) + "\n");
		
		string.append("// Platforms\n");
		for (Platform platform : level.getPlatforms()) {
			string.append("platform " + platform.toString() + "\n");
		}
		
		string.append("// Points\n");
		for (Point point : level.getPoints()) {
			string.append("point " + point.toString() + "\n");
		}
		
		string.append("// Obstacles\n");
		for (Obstacle obstacle : level.getObstacles()) {
			string.append("obstacle " + obstacle.toString() + "\n");
		}
		
		string.append("// Items\n");
		for (Item item : level.getItems()) {
			string.append("item " + item.toString() + "\n");
		}
		
		string.append("// Texts\n");
		for (String text : level.getText()) {
			string.append("text " + text + "\n");
		}

		return string.toString();
	}

	public static boolean saveLevel(Level level, String fileName) {
		if (!fileName.endsWith(LevelLoader.LEVEL_EXTENSION))
			fileName += LevelLoader.LEVEL_EXTENSION;
		
		String directoryPath = LevelLoader.LEVELS_DIRECTORY;

		String content = getLevelString(level);

		File directory = new File(directoryPath);
		File file = new File(directory, fileName);

		try {
			if (!directory.exists()) {
				throw new IllegalStateException("Directory " + directoryPath + " does not exists!");
			}

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static void createAllLevelFiles() {
		LinkedList<Method> levelMethods = new LinkedList<>();
		
		for (Method method : LevelCreator.class.getMethods()) {
			if (method.getReturnType().equals(Level.class) && !method.isVarArgs()) {
				levelMethods.add(method);
			}
		}
		
		for (Method method : levelMethods) {
			try {
				boolean success = saveLevel((Level) method.invoke(LevelSaver.class, new LevelManager()), method.getName().substring(6));
				System.out.println((success ? "Success for " : "Failed for ") +  method.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
	}

}
