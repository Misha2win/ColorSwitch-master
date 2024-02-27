/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level.entity.point;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import misha.editor.level.LevelEditor;
import misha.game.level.entity.point.PortalPoint;

public class PortalPointEditor extends AbstractPointEditor<PortalPoint> {
	
	private int portalLinkID;
	
	public PortalPointEditor(LevelEditor levelEditor) {
		this(levelEditor, null);
	}
	
	public PortalPointEditor(LevelEditor levelEditor, PortalPoint portalPoint) {
		super(levelEditor, portalPoint);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	@Override
	protected PortalPoint getEntity() {
		return new PortalPoint(point.x, point.y, portalLinkID);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getY() <= 600) {
			super.mousePressed(e);
			 
			 if (entity != null && !dragging) {
				super.unfocusEntity();
			}
		}
	}
	
}
