/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;

import misha.editor.level.entity.EditableEntity;
import misha.editor.level.entity.EditableEntity.EditableEntityType;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Entity;
import misha.game.level.entity.Updatable;

@EditableEntity({ EditableEntityType.PLATFORMS })
public class PhotonicPlatform extends Platform implements Updatable {
	
	static { Entity.addSubclass(PhotonicPlatform.class); }
	
	public PhotonicPlatform(int x, int y, int w, int h) {
		super(CSColor.GRAY, x, y, w, h);
	}
	
	@Override
	public void update() {
		color = CSColor.GRAY;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color.getGraphicsColor());
		g.fillRect((int)x, (int)y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, width, height);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + String.format(" %s %s %s %s", (int) x, (int) y, width, height); 
	}
	
	@Override
	public Entity clone() {
		return new PhotonicPlatform((int) x, (int) y, width, height);
	}
	
}
