/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Point;
import misha.editor.DrawUtil;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.platform.HealthGateEditor;
import misha.editor.level.entity.platform.MovingPlatformEditor;
import misha.editor.level.entity.platform.PlatformEditor;
import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;

public class ObstacleSelector {
	
	public static final int NOTHING = 0;
	public static final int PLATFORM = 1;
	public static final int MOVING_PLATFORM = 2;
	public static final int HEALTH_GATE = 3;
	
	private static final Rectangle PLATFORM_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle MOVING_PLATFORM_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle HEALTH_GATE_BUTTON = new Rectangle(110, 660, 40, 40);
	
	private int highlightOption;
	
	public void setHighlight(int option) {
		if (option >= 0 && option <= 3)
			highlightOption = option;
	}
	
	public EntityEditor<?> getEditor(LevelEditor levelEditor, Platform[] platforms, Point point) {
		Platform clickedPlatform = null;
		for (Platform platform : platforms) {
			if (platform.getRect().contains(point)) {
				clickedPlatform = platform;
			}
		}
		
		if (clickedPlatform == null) {
			if (PLATFORM_BUTTON.contains(point)) {
				highlightOption = PLATFORM;
				return new PlatformEditor(levelEditor);
			} else if (MOVING_PLATFORM_BUTTON.contains(point)) {
				highlightOption = MOVING_PLATFORM;
				return new MovingPlatformEditor(levelEditor);
			} else if (HEALTH_GATE_BUTTON.contains(point)) {
				highlightOption = HEALTH_GATE;
				return new HealthGateEditor(levelEditor);
			}
		} else {
			if (clickedPlatform.getClass().equals(Platform.class)) {
				highlightOption = PLATFORM;
				return new PlatformEditor(levelEditor, clickedPlatform);
			} else if (clickedPlatform.getClass().equals(MovingPlatform.class)) {
				highlightOption = MOVING_PLATFORM;
				return new MovingPlatformEditor(levelEditor, (MovingPlatform) clickedPlatform);
			} else if (clickedPlatform.getClass().equals(HealthGate.class)) {
				highlightOption = HEALTH_GATE;
				return new HealthGateEditor(levelEditor, (HealthGate) clickedPlatform);
			}
		}
		
		return null;
	}
	
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, PLATFORM_BUTTON, highlightOption == PLATFORM);
		g.setColor(Color.BLACK);
		g.fillRect(PLATFORM_BUTTON.x + 5, PLATFORM_BUTTON.y + 5, 30, 30);
		
		DrawUtil.drawButton(g, MOVING_PLATFORM_BUTTON, highlightOption == MOVING_PLATFORM);
		g.setColor(Color.BLACK);
		g.fillRect(MOVING_PLATFORM_BUTTON.x + 5, MOVING_PLATFORM_BUTTON.y + 5, 30, 30);
		g.setColor(Color.WHITE);
		g.drawOval(MOVING_PLATFORM_BUTTON.x + 15, MOVING_PLATFORM_BUTTON.y + 15, 6, 6);
		g.drawLine(MOVING_PLATFORM_BUTTON.x + 5, MOVING_PLATFORM_BUTTON.y + 5, MOVING_PLATFORM_BUTTON.x + 18, MOVING_PLATFORM_BUTTON.y + 18);
		
		DrawUtil.drawButton(g, HEALTH_GATE_BUTTON, highlightOption == HEALTH_GATE);
		g.setColor(Color.GRAY);
		g.fillRect(HEALTH_GATE_BUTTON.x + 5, HEALTH_GATE_BUTTON.y + 5, 30, 30);
	}
	
}
