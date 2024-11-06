/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelLoader;
import misha.game.level.LevelManager;
import misha.game.level.LevelSaver;
import misha.game.level.entity.Entity;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;

public class LevelUtil {
	
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
		LevelSaver.saveLevel(level, name);
		
		if (promptForLevelOrder) {
			askToAppendToLevelOrder(name);
		}
	}
	
	public static void saveLevelAsNew(Level level) {
		String name = JOptionPane.showInputDialog("What should the name of this level be?");
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
		}
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
