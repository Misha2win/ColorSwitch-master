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

import java.awt.event.*;

public class ColorSwitch extends JFrame { // TODO Use specific imports!
	
	public static double scale = 1;

	public static final int NATIVE_WIDTH = 750;
	public static final int NATIVE_HEIGHT = 600;
	
	public static int WIDTH = (int) (NATIVE_WIDTH * scale);
	public static int HEIGHT = (int) (NATIVE_HEIGHT * scale);

	private GamePanel gamePanel;

	private ColorSwitch(String frameTitle, LevelManager levelManager) {
		super(frameTitle);

		pack();

		Insets insets = getInsets();
		setSize(WIDTH + insets.left + insets.right, HEIGHT + insets.top + insets.bottom);

		setLocationRelativeTo(null);
		setResizable(false);
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
				
				if (dim.width > dim.height) {
					HEIGHT = dim.height;
					WIDTH = (int)((double) NATIVE_WIDTH / NATIVE_HEIGHT * HEIGHT + 0.5);
				} else if (dim.height >= dim.height) {
					WIDTH = dim.width;
					HEIGHT = (int)((double) NATIVE_HEIGHT * WIDTH / NATIVE_WIDTH + 0.5);
				}
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
