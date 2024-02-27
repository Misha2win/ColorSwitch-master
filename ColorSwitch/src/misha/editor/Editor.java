/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import javax.swing.*;

import misha.game.level.Level;

public class Editor extends JFrame {
	
	public static final int WIDTH = 750;
	public static final int HEIGHT = 600 + 210;
	
	private EditorPanel gamePanel;

	public Editor(Level level) {
		super("Color Switch: Level Editor");
		
		setSize(WIDTH, HEIGHT + 28);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		gamePanel = new EditorPanel(level);
		getContentPane().add(gamePanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		new Thread(() -> {
			while (true) {
				gamePanel.repaint();
				try { Thread.sleep(10); } catch (Exception e) { e.printStackTrace(); }
			}
		}).start();
	}
	
	public Editor(int levelNum) {
		super("Color Switch: Level Editor");
		
		setSize(WIDTH, HEIGHT + 28);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		gamePanel = new EditorPanel(levelNum);
		getContentPane().add(gamePanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		new Thread(() -> {
			while (true) {
				gamePanel.repaint();
				try { Thread.sleep(10); } catch (Exception e) { e.printStackTrace(); }
			}
		}).start();
	}

	public static void main(String[] args) {
		Editor game = new Editor(0);
	}
	
}
