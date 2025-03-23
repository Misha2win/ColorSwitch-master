/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.screen;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import misha.game.ColorSwitch;

public class ScreenUtil {
	
	public static Rectangle2D getRect(RectangularShape rect) {
		double x = rect.getX();
		double y = rect.getY();
		double w = rect.getWidth();
		double h = rect.getHeight();
		
		if (ColorSwitch.GAME_WIDTH != ColorSwitch.NATIVE_WIDTH || ColorSwitch.GAME_HEIGHT != ColorSwitch.NATIVE_HEIGHT) {
			double widthRatio = (double) ColorSwitch.GAME_WIDTH / ColorSwitch.NATIVE_WIDTH;
			double heightRatio = (double) ColorSwitch.GAME_HEIGHT / ColorSwitch.NATIVE_HEIGHT;
			
			x = x * widthRatio + (ColorSwitch.SCREEN_WIDTH - ColorSwitch.GAME_WIDTH) / 2;
			y = y * heightRatio + (ColorSwitch.SCREEN_HEIGHT - ColorSwitch.GAME_HEIGHT) / 2;
			w *= widthRatio;
			h *= heightRatio;
		}
		
		return new Rectangle2D.Double(x, y, w, h);
	}

	public static boolean scaledContains(RectangularShape rect, Point p) {
		return getRect(rect).contains(p);
	}
	
}
