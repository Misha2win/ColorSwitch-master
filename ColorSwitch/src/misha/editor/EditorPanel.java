/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import misha.game.level.entity.CSColor;
import misha.editor.screen.ScreenManager;
import misha.game.level.Level;
import misha.game.level.LevelCreator;

public class EditorPanel extends JPanel {
	
	private ScreenManager screenManager;
	
	private EditorPanel(Level editingLevel, int levelNum) {
		this.screenManager = new ScreenManager(editingLevel, levelNum);
		
		super.setFocusable(true);
		
		super.addKeyListener(screenManager);
		super.addMouseListener(screenManager);
		super.addMouseMotionListener(screenManager);
	}
	
	public EditorPanel(Level editingLevel) {
		this(editingLevel, -1);
	}
	
	public EditorPanel(int levelNum) {
		this(LevelCreator.createLevels(null)[levelNum], levelNum);
	}
	
	@SuppressWarnings("deprecation")
	public EditorPanel() {
		this(new Level("EditingLevel", CSColor.GREEN), -1);
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		super.paintComponent(g);
		
		screenManager.draw(g);
	}
	
}
