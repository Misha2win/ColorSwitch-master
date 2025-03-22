/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

import misha.game.level.LevelManager;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorSwitch extends JFrame {

	public static final int NATIVE_WIDTH = 750;
	public static final int NATIVE_HEIGHT = 600;
	
	public static final double NATIVE_ASPECT_RATIO = (double) NATIVE_WIDTH / NATIVE_HEIGHT;
	
	public static int SCREEN_WIDTH = NATIVE_WIDTH;
	public static int SCREEN_HEIGHT = NATIVE_HEIGHT;
	
	public static int GAME_WIDTH = (int) (NATIVE_WIDTH);
	public static int GAME_HEIGHT = (int) (NATIVE_HEIGHT);

	private GamePanel gamePanel;

	private ColorSwitch(String frameTitle, LevelManager levelManager) {
		super(frameTitle);

		pack();

		Insets insets = getInsets();
		setSize(SCREEN_WIDTH + insets.left + insets.right, GAME_HEIGHT + insets.top + insets.bottom);

		setLocationRelativeTo(null);
//		setResizable(false);
		setAlwaysOnTop(true);

		if (levelManager != null)
			gamePanel = new GamePanel(levelManager);
		else
			gamePanel = new GamePanel();

		getContentPane().add(gamePanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension dim = e.getComponent().getSize();
				dim.height -= insets.top + insets.bottom;
			    dim.width -= insets.left + insets.right;

			    int newWidth = dim.width;
			    int newHeight = dim.height;

			    if (newWidth / (double) newHeight > NATIVE_ASPECT_RATIO) {
			        newWidth = (int) (newHeight * NATIVE_ASPECT_RATIO + 0.5);
			    } else {
			        newHeight = (int) (newWidth / NATIVE_ASPECT_RATIO + 0.5);
			    }

			    GAME_WIDTH = newWidth;
			    GAME_HEIGHT = newHeight;
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {}

			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}

	public ColorSwitch() {
		this("Color Switch", null);
	}

	public ColorSwitch(LevelManager levelManager) {
		this("Color Swtich: Level Testing", levelManager);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gamePanel.stopRepainting();
				dispose();
			}
		});
	}

	public static void main(String[] args) {
		new ColorSwitch();
	}
}
