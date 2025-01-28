/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import misha.game.ColorSwitch;
import misha.game.level.Level;
import misha.game.level.LevelLoader;
import misha.screen.Screen;

public class MenuScreen extends Screen {
	
	private final static RoundRectangle2D PLAY_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125, 225, 250, 75, 20, 20);
	private final static RoundRectangle2D QUIT_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125, 325, 250, 75, 20, 20);
	
	private Level backgroundLevel;
	
	public MenuScreen(ScreenManager manager) {
		super(manager);
		
		try {
			backgroundLevel = LevelLoader.getLevel(null, "ColorChanger3");
		} catch (IOException e) {
			System.err.println("There was an issue when loading the background level!");
			e.printStackTrace();
		}
	}

	@Override
	public void setup() {
	}

	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		backgroundLevel.draw(g);
		g.setColor(new Color(200, 200, 200, 120));
		g.fillRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
		FontMetrics metrics = g.getFontMetrics();
		
		String title = "Color Switch";
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(title) / 2 - 1, 150);
		g.setColor(Color.BLACK);
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(title) / 2 + 2, 150 + 2);
		
		String playString = "Play";
		g.setColor(new Color(200, 200, 200));
		g.fill(PLAY_BUTTON);
		g.setColor(Color.BLACK);
		g.drawString(playString, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(playString) / 2, (int)PLAY_BUTTON.getY() + 55);
		
		String quitString = "Quit";
		g.setColor(new Color(200, 200, 200));
		g.fill(QUIT_BUTTON);
		g.setColor(Color.BLACK);
		g.drawString(quitString, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(quitString) / 2, (int)QUIT_BUTTON.getY() + 55);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (PLAY_BUTTON.contains(e.getPoint())) {
			screenManager.setScreen(ScreenManager.LEVEL_SCREEN);
		} else if (QUIT_BUTTON.contains(e.getPoint())) {
			System.exit(0);
		}
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}
