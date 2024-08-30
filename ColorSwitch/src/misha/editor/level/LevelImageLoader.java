/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class LevelImageLoader {
	
	public static final String IMAGE_DIRECTORY = "./resources/level/images/";
	
	private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();
	
	private static BufferedImage loadImage(String imageName) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get(IMAGE_DIRECTORY + imageName + ".png"))));
	}
	
	public static BufferedImage loadLevelImage(String levelName) throws IOException {
		IMAGES.put(levelName, loadImage(levelName));
		
		return IMAGES.get(levelName);
	}
	
	public static BufferedImage getLevelImage(String levelName) throws IOException {
		if (!IMAGES.containsKey(levelName)) {
			return loadLevelImage(levelName);
		}
		
		return IMAGES.get(levelName);
	}

}
