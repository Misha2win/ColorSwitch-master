/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelLoader;

public class LevelImageSaver {
	
	static {
		System.out.println("Saving level images...");
		LevelImageSaver.saveAllLevels();
	}

	public static BufferedImage createLevelImage(Level level) {
		BufferedImage img = new BufferedImage(ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		level.draw(g);

		return img;
	}

	public static boolean saveImage(BufferedImage img, String name) {
		File outputfile = new File(LevelImageLoader.IMAGE_DIRECTORY + name + ".png");
		
		File parentDirectory = LevelLoader.getJarParentDirectory();
		if (parentDirectory != null) {
			outputfile = new File(parentDirectory, LevelImageLoader.IMAGE_DIRECTORY + name + ".png");
		}
		
		try {
			ImageIO.write(img, "png", outputfile);
			return true;
		} catch (IOException e) {
			System.out.println("Failed to create image!");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean createAndSaveImage(Level level) {
		return saveImage(createLevelImage(level), level.getLevelName());
	}
	
	public static boolean createAndSaveImage(Level level, String name) {
		return saveImage(createLevelImage(level), name);
	}

	public static void saveAllLevels() {
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);
		
		File parentDirectory = LevelLoader.getJarParentDirectory();
		if (parentDirectory != null) {
			directory = new File(parentDirectory, LevelLoader.LEVELS_DIRECTORY);
		}

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden() && file.getName().endsWith(".level")) {
                	try {
						saveImage(createLevelImage(LevelLoader.getLevel(null, file.getName())), file.getName().replace(".level", ""));
						System.out.println("Saved level image for '" + file.getName() + "'");
						LevelImageLoader.loadLevelImage(file.getName().replace(".level", ""));
					} catch (IOException e) {
						System.err.println("Could not create image for " + file.getName());
						e.printStackTrace();
					}
                }
            }
        } else {
            System.err.println("Directory '" + directory.getAbsolutePath() + "' does not exist or is not a directory.");
        }
	}

}
