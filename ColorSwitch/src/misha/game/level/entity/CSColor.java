/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

import java.awt.Color;

public class CSColor {
	
	public static final CSColor BLACK 	= new CSColor(0b0_000);
	public static final CSColor RED 	= new CSColor(0b0_100);
	public static final CSColor GREEN 	= new CSColor(0b0_010);
	public static final CSColor BLUE 	= new CSColor(0b0_001);
	public static final CSColor YELLOW 	= new CSColor(0b0_110);
	public static final CSColor MAGENTA = new CSColor(0b0_101);
	public static final CSColor CYAN 	= new CSColor(0b0_011);
	public static final CSColor WHITE 	= new CSColor(0b0_111);
	public static final CSColor GRAY 	= new CSColor(0b1_000);
	
	private final int color;
	
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
	
	public static String getStringFromColor(CSColor color) {
		if (color.equals(BLACK))
			return "black";
		else if (color.equals(RED))
			return "red";
		else if (color.equals(GREEN))
			return "green";
		else if (color.equals(BLUE))
			return "blue";
		else if (color.equals(YELLOW))
			return "yellow";
		else if (color.equals(MAGENTA))
			return "magenta";
		else if (color.equals(CYAN))
			return "cyan";
		else if (color.equals(WHITE))
			return "white";
		else if (color.equals(GRAY))
			return "gray";
	
		return "unknown";
	}
	
	public static String getCSColorStringFromColor(CSColor color) {
		if (color.equals(CSColor.BLACK))
			return "CSColor.BLACK";
		else if (color.equals(CSColor.RED))
			return "CSColor.RED";
		else if (color.equals(CSColor.GREEN))
			return "CSColor.GREEN";
		else if (color.equals(CSColor.BLUE))
			return "CSColor.BLUE";
		else if (color.equals(CSColor.YELLOW))
			return "CSColor.YELLOW";
		else if (color.equals(CSColor.MAGENTA))
			return "CSColor.MAGENTA";
		else if (color.equals(CSColor.CYAN))
			return "CSColor.CYAN";
		else if (color.equals(CSColor.WHITE))
			return "CSColor.WHITE";
		else if (color.equals(GRAY))
			return "CSColor.GRAY";
	
		return "unknown";
	}
	
	public static CSColor getColorFromString(String name) {
		name = name.toLowerCase();
		
		if (name.equals("black"))
			return CSColor.BLACK;
		else if (name.equals("red"))
			return CSColor.RED;
		else if (name.equals("green"))
			return CSColor.GREEN;
		else if (name.equals("blue"))
			return CSColor.BLUE;
		else if (name.equals("yellow"))
			return CSColor.YELLOW;
		else if (name.equals("magenta"))
			return CSColor.MAGENTA;
		else if (name.equals("cyan"))
			return CSColor.CYAN;
		else if (name.equals("white"))
			return CSColor.WHITE;
		else if (name.equals("gray"))
			return CSColor.GRAY;
		
		throw new IllegalArgumentException("Could not find a color for \"" + name + "\"");
	}
	
}
