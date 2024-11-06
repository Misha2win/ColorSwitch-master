/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.LinkedList;

import misha.game.level.entity.player.Player;
import misha.game.level.entity.point.SpawnPoint;

public class LevelManager {

	private Player player;

	private Level[] levels;
	private int currentLevel;
	
	private boolean debugMode;

	public LevelManager(int startingLevel) {
		this.currentLevel = startingLevel;
		this.levels = LevelCreator.createLevels(this);
		player = new Player(0, 0);
		resetPlayer();
	}
	
	public LevelManager(Level... levels) {
		this(0);
		
		this.levels = levels;
		resetPlayer();
	}
	
	public LevelManager() {
		this(0);
	}
	
	public void toggleDebugMode() {
		debugMode = !debugMode;
	}
	
	public boolean getDebugMode() {
		return debugMode;
	}
	
	public Level getLevel(int levelNum) {
		return levels[levelNum];
	}
	
	public String[] getLevelNames() {
		LinkedList<String> levelNames = new LinkedList<>();
		
		for (Level level : levels) {
			levelNames.add(level != null ? level.getLevelName() : null);
		}
		
		return levelNames.toArray(new String[levelNames.size()]);
	}
	
	public String[] getNonNullLevelNames() {
		LinkedList<String> levelNames = new LinkedList<>();
		
		for (Level level : levels) {
			if (level != null) {
				levelNames.add(level.getLevelName());
			}
		}
		
		return levelNames.toArray(new String[levelNames.size()]);
	}
	
	public boolean setLevel(int levelNum) {
		if (levelNum >= 0 && levelNum < levels.length) {
			if (levels[levelNum] != null) {
				currentLevel = levelNum;
				return true;
			}
		}
		
		return false;
	}
	
	public Level getCurrentLevel() {
		return levels[currentLevel];
	}

	public void resetLevel() {
		try {
			levels[currentLevel] = LevelLoader.getLevel(this, getCurrentLevel().getLevelName());
		} catch (IOException e) {
			System.err.println("There was an issue reloading level " + getCurrentLevel().getLevelName());
			e.printStackTrace();
		}
		resetPlayer();
	}

	public Player getPlayer() {
		return player;
	}

	public int getLevelNum() {
		return currentLevel;
	}

	public void nextLevel() {
		if (currentLevel < levels.length - 1) {
			currentLevel++;

			if (levels[currentLevel] == null) {
				currentLevel = 49;
			}

			resetLevel();
		}
		
		if (debugMode)
			System.out.println("Advancing to level " + currentLevel + " (" + getCurrentLevel().getLevelName() + ")");
	}

	public void previousLevel() {
		if (currentLevel > 0) {
			currentLevel--;
			
			resetLevel();
		}
		
		if (debugMode)
			System.out.println("Going back to level " + currentLevel + " (" + getCurrentLevel().getLevelName() + ")");
	}
	
	public void resetPlayer() {
		player.setLevel(getCurrentLevel());
		SpawnPoint spawn = getCurrentLevel().getActiveSpawnPoint();
		player.setPos(spawn.getX(), spawn.getY());
		player.reset();
	}
	
	public void draw(Graphics2D g) {
		getCurrentLevel().draw(g);
		player.draw(g);
	}
	
	public void update() {
		player.update();
		getCurrentLevel().update();
	}

}
