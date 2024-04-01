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
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelCreator;
import misha.game.level.LevelLoader;

public class LevelImageMaker {
	
	public static String imgDirectory = "./img";

	public static BufferedImage createLevelImage(Level level) {
		BufferedImage img = new BufferedImage(ColorSwitch.WIDTH, ColorSwitch.HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ColorSwitch.WIDTH, ColorSwitch.HEIGHT);
		level.draw(g);

		return img;
	}

	public static boolean saveImage(BufferedImage img, String name) {
		File outputfile = new File(imgDirectory + name + ".png");
		try {
			ImageIO.write(img, "png", outputfile);
			return true;
		} catch (IOException e) {
			System.out.println("Failed to create image!");
			e.printStackTrace();
			return false;
		}
	}

	public static void createAllLevelImageFiles() {
		Level[] levels = LevelCreator.createLevels(null);
		for (int i = 0; i < levels.length; i++) {
			if (levels[i] != null) {
				boolean success = saveImage(createLevelImage(levels[i]), "level" + i);
				System.out.println(success ? "Done saving level" + i + "!" : "Unable to save level" + i + "!");
			}
		}
		System.out.println("Done with all!");
	}

	public static BufferedImage[] loadLevelImages() {
		LinkedList<BufferedImage> images = new LinkedList<>();

		File imgFolder = new File(imgDirectory);
		for (File file : imgFolder.listFiles()) {
			if (file.getName().endsWith(".png")) {
				try {
					images.add(ImageIO.read(file));
				} catch (IOException e) {
					System.out.println("Unable to read file!");
					e.printStackTrace();
				}
			}
		}

		return images.toArray(new BufferedImage[images.size()]);
	}

	public static void saveLevelImagesWithNames() {
		File directory = new File(LevelLoader.LEVELS_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.isHidden()) {
                	saveImage(createLevelImage(LevelLoader.getLevel(null, file.getName())), file.getName().replace(".level", ""));
                }
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
	}

	public static void main(String[] args) {
		saveLevelImagesWithNames();
	}

}
