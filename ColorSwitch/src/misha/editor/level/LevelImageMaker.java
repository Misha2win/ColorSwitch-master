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
import java.lang.reflect.Method;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import misha.game.Game;
import misha.game.level.Level;
import misha.game.level.LevelCreator;
import misha.game.level.LevelManager;

public class LevelImageMaker {

	public static BufferedImage createLevelImage(Level level) {
		BufferedImage img = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		level.draw(g);

		return img;
	}

	public static boolean saveImage(BufferedImage img, String name) {
		File outputfile = new File("./img/" + name + ".png");
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

		File imgFolder = new File("./img/");
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
		for (Method method : LevelCreator.class.getMethods()) {
			if (method.getReturnType().equals(Level.class)) {
				System.out.println("Generating image for " + method.getName() + "() level method");
				
				Level level = null;
				try {
					level = (Level) method.invoke(LevelImageMaker.class, (LevelManager) null);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				saveImage(createLevelImage(level), method.getName().replace("create", ""));
			}
		}
	}

	public static void main(String[] args) {
		saveLevelImagesWithNames();
	}

}
