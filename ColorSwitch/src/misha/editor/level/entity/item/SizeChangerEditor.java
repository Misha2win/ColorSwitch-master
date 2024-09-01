/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.item.SizeChanger;

public class SizeChangerEditor extends AbstractItemEditor<SizeChanger> {
	
	private static final Rectangle ENLARGE_BUTTON = new Rectangle(10, 710, 40, 40);
	private static final Rectangle REDUCE_BUTTON = new Rectangle(60, 710, 40, 40);
	
	private boolean enlarge;
	
	public SizeChangerEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public SizeChangerEditor(LevelEditor levelEditor, SizeChanger sizeChanger) {
		super(levelEditor, sizeChanger);
		
		enlarge = false;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		DrawUtil.drawColorButton(g, ENLARGE_BUTTON, Color.WHITE, enlarge);
		g.setColor(Color.GRAY.darker().darker());
		Polygon upArrow = new Polygon();
		upArrow.addPoint(ENLARGE_BUTTON.x + 20, ENLARGE_BUTTON.y + 13);
		upArrow.addPoint(ENLARGE_BUTTON.x + 27, ENLARGE_BUTTON.y + 27);
		upArrow.addPoint(ENLARGE_BUTTON.x + 12, ENLARGE_BUTTON.y + 27);
		g.fill(upArrow);
		
		DrawUtil.drawColorButton(g, REDUCE_BUTTON, Color.WHITE, !enlarge);
		g.setColor(Color.GRAY.darker().darker());
		Polygon downArrow = new Polygon();
		downArrow.addPoint(REDUCE_BUTTON.x + 27, REDUCE_BUTTON.y + 13);
		downArrow.addPoint(REDUCE_BUTTON.x + 12, REDUCE_BUTTON.y + 13);
		downArrow.addPoint(REDUCE_BUTTON.x + 20, REDUCE_BUTTON.y + 27);
		g.fill(downArrow);
	}
	
	@Override
	protected SizeChanger getNewEntityInstance() {
		return new SizeChanger(point.x, point.y, enlarge);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		} else {
			if (ENLARGE_BUTTON.contains(e.getPoint())) {
				enlarge = true;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			} else if (REDUCE_BUTTON.contains(e.getPoint())) {
				enlarge = false;
				if (entity != null) {
					super.levelEditor.removeObjectFromLevel(entity);
					super.createEntity();
				}
			}
		}
	}
	
}
