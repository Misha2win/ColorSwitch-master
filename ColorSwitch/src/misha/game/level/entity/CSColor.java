/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

import java.awt.Color;

public class CSColor {
	
	public static final CSColor BLACK 	= new CSColor(0b000);
	public static final CSColor RED 	= new CSColor(0b100);
	public static final CSColor GREEN 	= new CSColor(0b010);
	public static final CSColor BLUE 	= new CSColor(0b001);
	public static final CSColor YELLOW 	= new CSColor(0b110);
	public static final CSColor MAGENTA = new CSColor(0b101);
	public static final CSColor CYAN 	= new CSColor(0b011);
	public static final CSColor WHITE 	= new CSColor(0b111);
	public static final CSColor GRAY 	= new CSColor(0b1000);
	
	private final int color;
	
	public CSColor(boolean red, boolean green, boolean blue) {
		this((red ? RED.color : 0) | (green ? GREEN.color : 0) | (blue ? BLUE.color : 0));
	}
	
	private CSColor(int color) {
		this.color = color;
	}
	
	public CSColor add(CSColor other) {
		return new CSColor((this.color & 0b111) | (other.color & 0b111));
	}
	
	public CSColor subtract(CSColor other) {
		return new CSColor((this.color & 0b111) & (~other.color & 0b111));
	}
	
	public boolean collidesWith(CSColor other) {
		return ((this.color & 0b111) & (other.color & 0b111)) != 0;
	}
	
	public Color getGraphicsColor() {
		Color c = null;
		
		if (color == BLACK.color) {
			c = Color.BLACK;
		} else if (color == RED.color) {
			c = Color.RED;
		} else if (color == GREEN.color) {
			c = Color.GREEN;
		} else if (color == BLUE.color) {
			c = Color.BLUE;
		} else if (color == YELLOW.color) {
			c = Color.YELLOW;
		} else if (color == MAGENTA.color) {
			c = Color.MAGENTA;
		} else if (color == CYAN.color) {
			c = Color.CYAN;
		} else if (color == WHITE.color) {
			c = Color.WHITE;
		} else if (color == GRAY.color) {
			c = Color.GRAY;
		}
		
		return c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CSColor) {
			if (this.color == ((CSColor) o).color) {
				return true;
			}
		}
		
		return false;
	}
	
}
