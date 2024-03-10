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
import javax.swing.JPanel;
import java.awt.geom.RoundRectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;

import misha.game.level.entity.PhysicsEngine;
import misha.game.level.LevelManager;

public class GamePanel extends JPanel implements KeyListener, MouseListener {
	
	private final static RoundRectangle2D PLAY_BUTTON = new RoundRectangle2D.Float(ColorSwitch.WIDTH / 2 - 125, 225, 250, 75, 20, 20);
	private final static RoundRectangle2D QUIT_BUTTON = new RoundRectangle2D.Float(ColorSwitch.WIDTH / 2 - 125, 325, 250, 75, 20, 20);
	
	private long pTime;
	private int fpsCounter;
	private int updateFpsCounter;

	public LevelManager levelManager;
	
	private boolean isRepainting;
	private boolean drawGame;
	
	public GamePanel() {
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		
		isRepainting = true;
		drawGame = false;
	
		levelManager = new LevelManager(0);
		
		pTime = System.currentTimeMillis();
		start();
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		super.paintComponent(g);
		
		long currentTime = System.currentTimeMillis();
		int fps = 10000 / (int)(currentTime - pTime != 0 ? currentTime - pTime : 1);
		pTime = currentTime;
		
		if (updateFpsCounter >= 10 && fps != 1000) {
			updateFpsCounter = 0;
			fpsCounter = (int) fps;
		}
		updateFpsCounter++;
	
		FontMetrics metrics;
		
		if (drawGame) {
			levelManager.draw(g);
		} else {
			levelManager.getLevel(3).draw(g);
			g.setColor(new Color(200, 200, 200, 120));
			g.fillRect(0, 0, ColorSwitch.WIDTH, ColorSwitch.HEIGHT);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
			metrics = g.getFontMetrics();
			
			String title = "Color Switch";
			g.drawString(title, ColorSwitch.WIDTH / 2 - metrics.stringWidth(title) / 2 - 1, 150);
			g.setColor(Color.BLACK);
			g.drawString(title, ColorSwitch.WIDTH / 2 - metrics.stringWidth(title) / 2 + 2, 150 + 2);
			
			String playString = "Play";
			g.setColor(new Color(200, 200, 200));
			g.fill(PLAY_BUTTON);
			g.setColor(Color.BLACK);
			g.drawString(playString, ColorSwitch.WIDTH / 2 - metrics.stringWidth(playString) / 2, (int)PLAY_BUTTON.getY() + 55);
			
			String quitString = "Quit";
			g.setColor(new Color(200, 200, 200));
			g.fill(QUIT_BUTTON);
			g.setColor(Color.BLACK);
			g.drawString(quitString, ColorSwitch.WIDTH / 2 - metrics.stringWidth(quitString) / 2, (int)QUIT_BUTTON.getY() + 55);
		}
		
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 14));
		metrics = g.getFontMetrics();
		
		String fpsStr = "FPS: " + (fpsCounter / 10f);
		g.setColor(Color.BLACK);
		g.drawString(fpsStr, ColorSwitch.WIDTH - 25 - metrics.stringWidth(fpsStr), 30 - metrics.getHeight() / 2 + metrics.getAscent());
	}
	
	private void update() {
		levelManager.update();
		PhysicsEngine.calcPhysics(levelManager);
	}
	
	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRepainting) {
					try { Thread.sleep(10); } catch (Exception ex) {}
					
					if (drawGame)
						update();
					repaint();
				}
			}
		}).start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (drawGame)
			levelManager.getPlayer().keyPressed(e);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (e.getKeyCode() == KeyEvent.VK_B) {
			levelManager.toggleDebugMode();
			System.out.println("Debug mode toggled: " + levelManager.getDebugMode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (drawGame)
			levelManager.getPlayer().keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignore
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (PLAY_BUTTON.contains(e.getPoint())) {
			drawGame = true;
		} else if (QUIT_BUTTON.contains(e.getPoint())) {
			System.exit(0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Ingore
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Ignore
	}
	
}
