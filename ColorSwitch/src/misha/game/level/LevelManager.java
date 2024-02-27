/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.awt.Graphics2D;
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
	
	public void setCurrentLevel(Level level) {
		levels[currentLevel] = level;
	}
	
	public Level getCurrentLevel() {
		return levels[currentLevel];
	}

	public void resetLevel() {
		levels[currentLevel] = LevelCreator.createLevels(this)[currentLevel];
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

			resetPlayer();
		}
		System.out.println("Advancing to level " + currentLevel);
	}

	public void previousLevel() {
		if (currentLevel > 0) {
			currentLevel--;
			
			resetLevel();
			resetPlayer();
		}
		System.out.println("Going back to level " + currentLevel);
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
