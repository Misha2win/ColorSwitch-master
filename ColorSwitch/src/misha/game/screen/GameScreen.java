/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import misha.game.level.LevelManager;
import misha.game.level.entity.PhysicsEngine;
import misha.screen.Screen;

public class GameScreen extends Screen {
	
	public LevelManager levelManager;
	
	public GameScreen(ScreenManager manager, LevelManager levelManager) {
		super(manager);
		
		this.levelManager = levelManager;
	}
	
	public GameScreen(ScreenManager manager) {
		this(manager, new LevelManager(0));
	}
	
	public LevelManager getLevelManager() {
		return levelManager;
	}
	
	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	@Override
	public void setup() {
	}
	
	@Override
	public void update() {
		levelManager.update();
		PhysicsEngine.calcPhysics(levelManager);
		
		LevelScreen levelScreen = ((LevelScreen) screenManager.getScreen(ScreenManager.LEVEL_SCREEN));
		if (levelManager.getLevelNum() > levelManager.getNonNullLevelNames().length) {
			levelScreen.unlockLevel(levelManager.getNonNullLevelNames().length - 1);
		} else {
			levelScreen.unlockLevel(levelManager.getLevelNum());
		}
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw((Graphics2D) g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		levelManager.getPlayer().keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_B) {
			levelManager.toggleDebugMode();
			System.out.println("Debug mode toggled: " + levelManager.getDebugMode());
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.setScreen(ScreenManager.PAUSE_SCREEN);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		levelManager.getPlayer().keyReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}
