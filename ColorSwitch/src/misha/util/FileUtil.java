/*
 * Author: Misha Malinouski
 * Date:   3/23/2025
 * Rev:    01
 * Notes:  
 */

package misha.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.LinkedList;

import misha.game.ColorSwitch;
import misha.game.level.LevelLoader;

public class FileUtil {
	
	private static File JAR_PARENT_DIR;
	
	public static File getJarParentDirectory() {
		if (JAR_PARENT_DIR != null) {
			return JAR_PARENT_DIR;
		}
		
		try {
        	CodeSource codeSource = ColorSwitch.class.getProtectionDomain().getCodeSource();
			File location = new File(codeSource.getLocation().toURI());
			if (location.isFile() && location.getName().endsWith(".jar")) {
				return (JAR_PARENT_DIR = location.getParentFile());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static File getFile(String directory, String fileName) {
		if (getJarParentDirectory() != null) {
			return new File(JAR_PARENT_DIR, directory + fileName);
		}
		
		return new File(directory + fileName);
	}
	
	public static byte[] readFileBytes(String directory, String fileName) {
		try {
			return Files.readAllBytes(getFile(directory, fileName).toPath());
		} catch (IOException e) {
			System.err.println("There was an issue loading '" + directory + fileName + "'!");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static File[] getLevelFiles() {
		LinkedList<File> levelFiles = new LinkedList<>();
		
		File directory = getFile(LevelLoader.LEVELS_DIRECTORY, ".");
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(".level")) {
                	levelFiles.add(file);
                }
            }
        } else {
            System.err.println("Directory '" + directory.getAbsolutePath() + "' does not exist or is not a directory.");
        }
        
        return levelFiles.toArray(new File[levelFiles.size()]);
	}
	
}
