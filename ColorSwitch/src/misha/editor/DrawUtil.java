/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DrawUtil {
	
	public static void drawButton(Graphics2D g, Rectangle rect, boolean highlight) {
		g.setColor(Color.WHITE);
		g.fill(rect);
		g.setColor(Color.BLACK);
		g.draw(rect);
		
		if (highlight) {
			g.setColor(new Color(150, 150, 150));
			g.fillRect(rect.x + 1, rect.y + 1, rect.width - 1, rect.height - 1);
		}
	}
	
	public static void drawColorButton(Graphics2D g, Rectangle button, Color outer, Color inner, boolean highlight) {
		g.setColor(Color.WHITE);
		g.fill(button);
		
		if (highlight) {
			g.setColor(new Color(150, 150, 150));
			g.fillRect(button.x + 1, button.y + 1, button.width - 1, button.height - 1);
		}
		
		g.setColor(outer);
		g.fillRect(button.x + 5, button.y + 5, 30, 30);
		g.setColor(inner);
		g.fillRect(button.x + 10, button.y + 10, 20, 20);
		g.setColor(Color.BLACK);
		g.draw(button);
	}
	
	public static void drawColorButton(Graphics2D g, Rectangle button, Color color, boolean highlight) {
		drawColorButton(g, button, color.darker(), color, highlight);
	}
	
}
