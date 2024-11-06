package misha.game.screen;

import java.util.ArrayList;

import misha.screen.Screen;
import misha.screen.AbstractScreenManager;

public class ScreenManager extends AbstractScreenManager {

	public static final int MENU_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	public static final int PAUSE_SCREEN = 2;
	public static final int LEVEL_SCREEN = 3;
	
	/**
	 * Constructs a new ScreenManager
	 * 
	 * @param surface the surface to give all of the Screens
	 */
	public ScreenManager() {
		currentScreen = ScreenManager.MENU_SCREEN;
		
		this.screens = new ArrayList<Screen>();
		screens.add(new MenuScreen(this));
		screens.add(new GameScreen(this));
		screens.add(new PauseScreen(this));
		screens.add(new LevelScreen(this));
		
		screens.get(currentScreen).focused();
		
		setup();
	}
	
	public GameScreen getGameScreen() {
		return (GameScreen) getScreen(GAME_SCREEN);
	}
	
}
