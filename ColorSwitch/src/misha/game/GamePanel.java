/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import misha.game.level.LevelManager;
import misha.game.screen.ScreenManager;

public class GamePanel extends JPanel implements KeyListener, MouseListener {
	
	private ScreenManager screenManager;
	
	private long frameCountStartTime = 0;
	private long fpsUpdateFrequency = 1000;
	private long frameCount = 0;
	private double fps = 0;
	
	private boolean isRepainting;
	
	public GamePanel(LevelManager levelManager) {
		setBackground(Color.BLACK);
		
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		requestFocus();
		
		isRepainting = true;
		
		screenManager = new ScreenManager();
		
		if (levelManager != null)
			screenManager.getGameScreen().setLevelManager(levelManager);
		
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
		Graphics imageGraphics = image.getGraphics();
		
		screenManager.draw(imageGraphics);
		
		String fpsStr = "FPS: " + fps;
		imageGraphics.setFont(new Font("MONOSPACED", Font.PLAIN, 14));
		FontMetrics metrics = imageGraphics.getFontMetrics();
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.drawString(fpsStr, ColorSwitch.NATIVE_WIDTH - 25 - metrics.stringWidth(fpsStr), 30 - metrics.getHeight() / 2 + metrics.getAscent());
		
		imageGraphics.dispose();
		
		g.drawImage(image, getWidth() / 2 - ColorSwitch.WIDTH / 2, getHeight() / 2 - ColorSwitch.HEIGHT / 2, ColorSwitch.WIDTH, ColorSwitch.HEIGHT, null);
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

	@Override
	public void keyPressed(KeyEvent e) {
		screenManager.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		screenManager.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		screenManager.keyTyped(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		screenManager.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screenManager.mouseReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		screenManager.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		screenManager.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		screenManager.mouseExited(e);
	}
	
}
