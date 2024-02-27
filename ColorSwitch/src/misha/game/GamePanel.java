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
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Font;
import misha.game.level.entity.PhysicsEngine;
import misha.game.level.LevelManager;

public class GamePanel extends JPanel implements KeyListener, MouseListener {

	public LevelManager levelManager;
	
	private boolean isRepainting;
	private boolean drawGame;
	
	private RectangularShape[] buttons;
	
	public GamePanel() {
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		
		buttons = new RectangularShape[] {
			new RoundRectangle2D.Float(Game.WIDTH / 2 - 125, 225, 250, 75, 20, 20),
			new RoundRectangle2D.Float(Game.WIDTH / 2 - 125, 325, 250, 75, 20, 20)
		};
		
		isRepainting = true;
		drawGame = false;
	
		levelManager = new LevelManager(16);
		
		start();
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		super.paintComponent(g);
		
		if (drawGame) {
			levelManager.draw(g);
		} else {
			levelManager.getLevel(3).draw(g);
			g.setColor(new Color(200, 200, 200, 120));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("MONOSPACED", Font.PLAIN, 60));
			
			String str = "Color Switch";
			g.drawString(str, Game.WIDTH / 2 - g.getFontMetrics().stringWidth(str) / 2 - 1, 150);
			g.setColor(Color.BLACK);
			g.drawString(str, Game.WIDTH / 2 - g.getFontMetrics().stringWidth(str) / 2 + 2, 150 + 2);
			
			for (int i = 0; i < buttons.length; i++) {
				RectangularShape button = buttons[i];
				
				g.setColor(new Color(200, 200, 200));
				g.fill(button);
				
				if (i == 0) {
					str = "Play";
				} else if (i == 1) {
					str = "Quit";
				}
				
				g.setColor(Color.BLACK);
				g.drawString(str, Game.WIDTH / 2 - g.getFontMetrics().stringWidth(str) / 2, (int)button.getY() + 55);
			}
		}
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
					if (drawGame)
						update();
					repaint();
					try { Thread.sleep(10); } catch (Exception ex) {}
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
		Point p = e.getPoint();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(p)) {
				if (i == 0) {
					drawGame = true;
				} else if (i == 1) {
					System.exit(0);
				}
			}
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
