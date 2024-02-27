/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Taskbar;

public class ColorSwitch extends JFrame {
	
	public static int WIDTH = 750;
	public static int HEIGHT = 600;
	
	private GamePanel gamePanel;

	public ColorSwitch() {
		super("Color Switch");
		
		Image img = Toolkit.getDefaultToolkit().getImage("img/icon.png");
		setIconImage(img);
		if (Taskbar.isTaskbarSupported()) {
			try {
				Taskbar.getTaskbar().setIconImage(img);
			} catch (Exception e) {
				System.out.println("Unable to set taskbar image...");
			}
		}
		
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
		ColorSwitch game = new ColorSwitch();
	}
}
