/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

import misha.game.level.LevelLoader;

public class LevelImageLoader {
	
	public static final String IMAGE_DIRECTORY = "./resources/level/images/";
	
	private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();
	
	private static BufferedImage loadImageFromFile(String imageName) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(FileUtil.readFileBytes(IMAGE_DIRECTORY, imageName + ".png")));
	}
	
	public static BufferedImage loadLevelImageFromFile(String levelName) throws IOException {
		IMAGES.put(levelName, loadImageFromFile(levelName));
		return IMAGES.get(levelName);
	}
	
	public static BufferedImage getLevelImageFromFile(String levelName) throws IOException {
		if (!IMAGES.containsKey(levelName)) {
			return loadLevelImageFromFile(levelName);
		}
		
		return IMAGES.get(levelName);
	}
	
	public static BufferedImage loadLevelImage(String levelName) throws IOException {
		IMAGES.put(levelName, LevelUtil.createLevelImage(LevelLoader.getLevel(null, levelName)));
		return IMAGES.get(levelName);
	}
	
	public static BufferedImage getLevelImage(String levelName) throws IOException {
		if (!IMAGES.containsKey(levelName)) {
			return loadLevelImage(levelName);
		}
		
		return IMAGES.get(levelName);
	}
	
	public static void reloadAllLevelImages() {
		for (File image : FileUtil.getLevelFiles()) {
			try {
				LevelImageLoader.loadLevelImage(image.getName().replace(".level", ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
