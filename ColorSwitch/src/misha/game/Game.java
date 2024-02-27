/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import com.apple.eawt.*;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.*;

@SuppressWarnings("unused")
public class Game extends JFrame {
	
	public static int WIDTH = 750;
	public static int HEIGHT = 600;
	
	private GamePanel gamePanel;

	public Game() {
		super("Color Switch");
		
		Image img = Toolkit.getDefaultToolkit().getImage("img/ico.png");
		setIconImage(img);
		
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
