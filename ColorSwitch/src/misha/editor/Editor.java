/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.awt.Insets;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.swing.*;

public class Editor extends JFrame {
	
	public static final int WIDTH = 750;
	public static final int HEIGHT = 600 + 210 + 10;
	
	private EditorPanel gamePanel;
	
	public Editor(int levelNum) {
		super("Color Switch: Level Editor");
		
		pack();
        
        Insets insets = getInsets();
        setSize(WIDTH + insets.left + insets.right, HEIGHT + insets.top + insets.bottom);
        
		setLocationRelativeTo(null);
		setResizable(false);
		
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        boolean isDebug = runtimeMxBean.getInputArguments().toString().contains("jdwp");
		if (isDebug) {
			System.out.println("Code was run with debug as true!");
			setAlwaysOnTop(true);
		}
		
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
