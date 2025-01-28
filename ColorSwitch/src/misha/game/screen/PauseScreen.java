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
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import misha.game.ColorSwitch;
import misha.screen.Screen;

public class PauseScreen extends Screen {
	
	private static RoundRectangle2D BACK_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125 - 210 - 20, ColorSwitch.NATIVE_HEIGHT - 90, 210, 75, 20, 20);
	private static RoundRectangle2D NEXT_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125 + 210 + 60, ColorSwitch.NATIVE_HEIGHT - 90, 210, 75, 20, 20);
	
	
	private BufferedImage background;
	
	public PauseScreen(ScreenManager manager) {
		super(manager);
	}

	@Override
	public void setup() {
	}

	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		g.drawImage(background, 0, 0, null);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT);
		
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
		FontMetrics metrics = g.getFontMetrics();
		
		String title = "Paused...";
		g.setColor(Color.WHITE);
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(title) / 2 - 1, 200 - 1);
		g.setColor(Color.BLACK);
		g.drawString(title, ColorSwitch.NATIVE_WIDTH / 2 - metrics.stringWidth(title) / 2 + 1, 200 + 1);
		
		BACK_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125, ColorSwitch.NATIVE_HEIGHT / 2 + 20, 250, 75, 20, 20);
		NEXT_BUTTON = new RoundRectangle2D.Float(ColorSwitch.NATIVE_WIDTH / 2 - 125, ColorSwitch.NATIVE_HEIGHT / 2 + 100 + 20, 250, 75, 20, 20);
		
		drawButton(g, BACK_BUTTON, "Resume");
		drawButton(g, NEXT_BUTTON, "Menu");
	}
	
	public void drawButton(Graphics2D g, RoundRectangle2D button, String text) {
		g.setColor(new Color(200, 200, 200));
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
		g.fill(button);
		g.setColor(Color.BLACK);
		g.drawString(text, (int) button.getX() + (int) button.getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, (int) button.getY() + 60);
	}
	
	@Override
	public void focused() {
		background = new BufferedImage(ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenManager.getScreen(ScreenManager.GAME_SCREEN).draw(background.getGraphics());
		background = blur(background, 5);
	}
	
	private static BufferedImage blur(BufferedImage image, int size) {
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = weight;
		}
		
		ConvolveOp op = new ConvolveOp(new Kernel(size, size, data));
		
		return op.filter(image, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (BACK_BUTTON.contains(e.getPoint())) {
			screenManager.setScreen(ScreenManager.GAME_SCREEN);
		} else if (NEXT_BUTTON.contains(e.getPoint())) {
			screenManager.setScreen(ScreenManager.MENU_SCREEN);
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
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.setScreen(ScreenManager.GAME_SCREEN);
		}
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
