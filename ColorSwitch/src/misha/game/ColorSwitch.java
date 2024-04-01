/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game;

import java.awt.Insets;

import javax.swing.JFrame;

import misha.game.level.LevelManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorSwitch extends JFrame {
    public static int WIDTH = 750;
    public static int HEIGHT = 600;

    private GamePanel gamePanel;
    
    private ColorSwitch(String frameTitle, LevelManager levelManager) {
        super(frameTitle);

        pack();
        
        Insets insets = getInsets();
        setSize(WIDTH + insets.left + insets.right, HEIGHT + insets.top + insets.bottom);
        
        setLocationRelativeTo(null);
        setResizable(true);
        setAlwaysOnTop(true);

        if (levelManager != null)
        	gamePanel = new GamePanel(levelManager);
        else
        	gamePanel = new GamePanel();
        
        getContentPane().add(gamePanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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
        ColorSwitch game = new ColorSwitch();
    }
}
