/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	public static int WIDTH = 750;
	public static int HEIGHT = 600;
	
	private GamePanel gamePanel;

	public Game() {
		super("Color Switch");
		
		setSize(WIDTH, HEIGHT + 28);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		gamePanel = new GamePanel();
		getContentPane().add(gamePanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		Game game = new Game();
	}
}
