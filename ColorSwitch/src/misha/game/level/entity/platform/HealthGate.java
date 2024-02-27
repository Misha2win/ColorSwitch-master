/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.Updatable;
import misha.game.level.entity.player.Player;

public class HealthGate extends Platform implements Updatable {

	private boolean isOpen;
	private boolean moreThan;
	private int healthRule;
	
	public HealthGate(boolean rule, int healthRule, int x, int y, int w, int h) {
		super(CSColor.BLACK, x, y, w, h);
		moreThan = rule;
		this.healthRule = healthRule;
	}
	
	public void setHealthRule(int newHealthRule) {
		healthRule = newHealthRule;
	}
	
	public int getHealthRule() {
		return healthRule;
	}
	
	public void setRule(boolean newRule) {
		moreThan = newRule;
	}
	
	public boolean getRule() {
		return moreThan;
	}
	
	public boolean getIsOpen() {
		return isOpen;
	}
	
	@Override
	public void update() {
		if (level == null) {
			throw new IllegalStateException("update() was envoked but this Updatable is not in a level!");
		}
		
		Player player = level.getLevelManager().getPlayer();
		if (moreThan)
			isOpen = player.getHealth() >= healthRule;
		else
			isOpen = player.getHealth() <= healthRule;
	}
	
	@Override
	public void draw(Graphics2D g) {
		// Draw rect
		g.setColor(color.equals(CSColor.BLACK) ? (isOpen ? new Color(150, 150, 150, 125) : new Color(0, 0, 0, 125)) : null);
		g.fillRect((int)x, (int)y, width, height);
		
		// Draw text
		g.setColor(Color.BLACK);
		g.setFont(new Font("MONOSPACED", Font.PLAIN, 30));
		String str = (moreThan ? ">" : "<") + "" + healthRule;
		g.drawString(str, x + width / 2 - g.getFontMetrics().stringWidth(str) / 2, y + height / 2 + 12);
	}
	
}
