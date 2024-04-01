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
import misha.editor.level.LevelEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.Level;
import misha.game.level.LevelCreator;

public class EditorPanel extends JPanel {
	
	private LevelEditor editor;
	
	private EditorPanel(Level editingLevel, int levelNum) {
		editor = new LevelEditor(editingLevel, levelNum);
		
		super.setFocusable(true);
		super.addKeyListener(editor);
		super.addMouseListener(editor);
		super.addMouseMotionListener(editor);
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
		
		editor.draw(g);
	}
	
}
