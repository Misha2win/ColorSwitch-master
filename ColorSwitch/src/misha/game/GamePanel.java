/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import misha.game.level.LevelManager;
import misha.game.screen.ScreenManager;

public class GamePanel extends JPanel {
	
	private ScreenManager screenManager;
	
	private long frameCountStartTime = 0;
	private long fpsUpdateFrequency = 1000;
	private long frameCount = 0;
	private double fps = 0;
	
	private boolean isRepainting;
	
	public GamePanel(LevelManager levelManager) {
		setBackground(Color.BLACK);
		
		setFocusable(true);
		requestFocus();
		
		isRepainting = true;
		
		screenManager = new ScreenManager();
		addKeyListener(screenManager);
		addMouseListener(screenManager);
		
		if (levelManager != null) {
			screenManager.getGameScreen().setLevelManager(levelManager);
			screenManager.setScreen(ScreenManager.GAME_SCREEN);
		}
		
		start();
	}
	
	public GamePanel() {
		this(null);
	}
	
	public void stopRepainting() {
		isRepainting = false;
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		super.paintComponent(g);
		
		if (System.currentTimeMillis() - frameCountStartTime >= fpsUpdateFrequency) {
			frameCountStartTime = System.currentTimeMillis();
			fps = frameCount / (fpsUpdateFrequency / 1000f);
			fps = ((int)(fps * 10)) / 10f; // Round to one decimal place
			frameCount = 0;
		} else {
			frameCount++;
		}
		
		BufferedImage image = new BufferedImage(ColorSwitch.NATIVE_WIDTH, ColorSwitch.NATIVE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
		
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // or VALUE_BILINEAR for smoother scaling
		
		screenManager.draw(imageGraphics);
		
		String fpsStr = "FPS: " + fps;
		imageGraphics.setFont(new Font("MONOSPACED", Font.PLAIN, 14));
		FontMetrics metrics = imageGraphics.getFontMetrics();
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.drawString(fpsStr, ColorSwitch.NATIVE_WIDTH - 25 - metrics.stringWidth(fpsStr), 30 - metrics.getHeight() / 2 + metrics.getAscent());
		
		imageGraphics.dispose();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // Ensures sharp edges
		g.drawImage(image, getWidth() / 2 - ColorSwitch.GAME_WIDTH / 2, getHeight() / 2 - ColorSwitch.GAME_HEIGHT / 2, ColorSwitch.GAME_WIDTH, ColorSwitch.GAME_HEIGHT, null);
	}
	
	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRepainting) {
					try { Thread.sleep(10); } catch (Exception ex) {}
					
					screenManager.update();
					repaint();
				}
			}
		}).start();
	}
	
}
