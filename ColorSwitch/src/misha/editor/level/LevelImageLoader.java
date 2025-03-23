/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.imageio.ImageIO;

import misha.game.level.LevelLoader;

public class LevelImageLoader {
	
	public static final String IMAGE_DIRECTORY = "./resources/level/images/";
	
	private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();
	
	private static BufferedImage loadImage(String imageName) throws IOException {
		File parentFile = LevelLoader.getJarParentDirectory();
        
        if (parentFile != null) {
        	return ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(new File(parentFile, IMAGE_DIRECTORY + imageName + ".png").toPath())));
        }
        
        return ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get(IMAGE_DIRECTORY + imageName + ".png"))));
	}
	
	public static BufferedImage loadLevelImage(String levelName) throws IOException {
		IMAGES.put(levelName, loadImage(levelName));
		
		System.out.println("Loaded level image for '" + levelName + "'");
		
		return IMAGES.get(levelName);
	}
	
	public static BufferedImage getLevelImage(String levelName) throws IOException {
		try {
			if (!IMAGES.containsKey(levelName)) {
				return loadLevelImage(levelName);
			}
		} catch (NoSuchFileException e) {
			LevelImageSaver.saveImage(LevelImageSaver.createLevelImage(LevelLoader.getLevel(null, levelName)), levelName);
			return getLevelImage(levelName);
		}
		
		return IMAGES.get(levelName);
	}

}
