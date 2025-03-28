/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelCreator;
import misha.game.level.LevelLoader;
import misha.game.level.LevelManager;
import misha.game.level.LevelSaver;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;

public class LevelUtil {
	
	public static BufferedImage createLevelImage(Level level) {
		BufferedImage img = new BufferedImage(ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		level.draw(g);

		return img;
	}
	
	@SuppressWarnings("deprecation")
	public static void playLevel(Level level) {
		LevelSaver.saveLevel(level, LevelLoader.LEVEL_DIRECTORY, "EditingLevel");
		
		LevelManager levelManager;
		try {
			levelManager = new LevelManager(LevelLoader.loadLevel(null, "../EditingLevel"));
			levelManager.getCurrentLevel().setLevelManager(levelManager);
			
			new Thread(() -> {
				new ColorSwitch(levelManager);
			}).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void saveLevel(Level level) {
		String name = level.getLevelName();
		
		boolean promptForLevelOrder = false;
		if (name.startsWith("EditingLevel")) {
			promptForLevelOrder = true;
			name = JOptionPane.showInputDialog("What should the name of this level be?");
		}
			
		System.out.println("Saving level as: " + name + ".level");
		boolean saveResult = LevelSaver.saveLevel(level, name);
		
		try {
			Field nameField = Level.class.getDeclaredField("levelName");
			nameField.setAccessible(true);
			nameField.set(level, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			LevelImageLoader.loadLevelImage(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (promptForLevelOrder) {
			askToAppendToLevelOrder(name);
		}
		
		if (saveResult) {
			JOptionPane.showMessageDialog(null, "Level Successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "There was an issue saving the level!", "Fail", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void saveLevelAsNew(Level level) {
		String name = JOptionPane.showInputDialog("What should the name of this level be?");
		
		String fileName = name;
		if (!fileName.endsWith(LevelLoader.LEVEL_EXTENSION))
			fileName += LevelLoader.LEVEL_EXTENSION;

		File directory = new File(LevelLoader.LEVELS_DIRECTORY);
		File file = new File(directory, fileName);

		try {
			if (!directory.exists()) {
				throw new IllegalStateException("Directory " + LevelLoader.LEVELS_DIRECTORY + " does not exists!");
			}

			if (file.exists()) {
				int option = JOptionPane.showConfirmDialog(null, "A level by the name of '" + name + "' already exists!\nWould you like to override it?", "Level already Exists!", JOptionPane.YES_NO_OPTION);
				if (option != JOptionPane.YES_OPTION) {
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LevelSaver.saveLevel(level, name);
		askToAppendToLevelOrder(name);
	}
	
	public static void askToAppendToLevelOrder(String levelName) {
		int option = JOptionPane.showConfirmDialog(null, "Would you like to append this level to LevelOrder.levels?", "Append to LevelOrder.levels?", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			try (FileWriter writer = new FileWriter(LevelLoader.LEVEL_DIRECTORY + "LevelsOrder.levels", true)) { // `true` enables append mode
	            writer.write("\n// Appended by editor\n" + levelName);
	            System.out.println("Appending " + levelName + " to LevelOrder.levels");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			LevelCreator.loadLevelOrderString();
		}
	}
	
	public static String promptUserForLevelOrderLevel() {
        String[] options = (LevelCreator.LEVEL_ORDER_STRING + "\nTestLevel").split("\n");

        JComboBox<String> comboBox = new JComboBox<>(options);

        int result = JOptionPane.showConfirmDialog(
        		null, 
        		comboBox, 
        		"Select a Level to Edit", 
        		JOptionPane.OK_CANCEL_OPTION, 
        		JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return (String) comboBox.getSelectedItem();
        } else {
            return null;
        }
	}
	
	public static boolean removeLevel(Level level) {
		int option = JOptionPane.showConfirmDialog(
				null, 
				"<html>Are you sure you wish to delete " + level.getLevelName() + "?<br><b style='color: red;'>This action is irreversible!</b>",
				"Confirm", 
				JOptionPane.YES_NO_OPTION
		);
		if (option == JOptionPane.YES_OPTION) {
			throw new IllegalStateException("This method is not implemented yet!");
		}
		
		return false;
	}
	
	public static void addObjectToLevel(Level level, Object obj) {
		modifyLevelContent(level, obj, true);
	}
	
	public static void removeObjectFromLevel(Level level, Object obj) {
		modifyLevelContent(level, obj, false);
	}
	
	@SuppressWarnings("deprecation")
	private static void modifyLevelContent(Level level, Object obj, boolean add) {
		if (obj == null)
			return;
		
		if (obj instanceof Entity) {
			Entity entity = (Entity) obj;
			
			Entity[] entities = null;
			if (entity instanceof Platform) {
				entities = level.getPlatforms();
			} else if (entity instanceof Point) {
				entities = level.getPoints();
			} else if (entity instanceof Obstacle) {
				entities = level.getObstacles();
			} else if (entity instanceof Item) {
				entities = level.getItems();
			}
			
			entities = (add ? Util.addToArray(entities, entity) : Util.removeFromArray(entities, entity));
			
			if (entity instanceof Platform) {
				level.setPlatforms((Platform[]) entities);
			} else if (entity instanceof Point) {
				level.setPoints((Point[]) entities);
			} else if (entity instanceof Obstacle) {
				level.setObstacles((Obstacle[]) entities);
			} else if (entity instanceof Item) {
				level.setItems((Item[]) entities);
			}
		} else if (obj instanceof String) {
			level.setText((String[]) (add ? Util.addToArray(level.getText(), (String) obj) : Util.removeFromArray(level.getText(), (String) obj)));
		} else {
			throw new IllegalArgumentException("This Object is not an Entity or String!");
		}
	}
	
}
