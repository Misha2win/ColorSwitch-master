/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.level;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.ColorChanger;
import misha.game.level.entity.item.ColorMixer;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.item.Mirror;
import misha.game.level.entity.item.Teleporter;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.PortalPoint;
import misha.game.level.entity.point.SpawnPoint;
import misha.game.level.Level;

public class LevelPrinter {
	
	public static void printLevelCode(Level level, int levelNum) {
		String levelName = "level" + (levelNum + 1);
		
		String str = "";
		
		str += "// *** Create level " + (levelNum + 1) + " ***\n"; 
		
		str += "Level " + levelName + " = new Level(\n";
		
		str += "\t\tlevelManager,\n";
		
		str += "\t\t" + LevelPrinter.getColorString(level.getLevelColor()) + ",\n"; 
		
		str += "\t\tnew Platform[] {\n";
		
		for (Platform platform : level.getPlatforms()) {
			String color = getColorString(platform.getColor());
			String platformClass = platform.getClass().getSimpleName();
			str += "\t\t\t\tnew ";
			if (platform.getClass().equals(Platform.class))
				str += createMethodCallString(platformClass, color, (int) platform.getX(), (int) platform.getY(), platform.getWidth(), platform.getHeight());
			else if (platform.getClass().equals(MovingPlatform.class))
				str += createMethodCallString(platformClass, color, (int) platform.getX(), (int) platform.getY(), (int) ((MovingPlatform) platform).getX2(), (int) ((MovingPlatform) platform).getY2(), platform.getWidth(), platform.getHeight());
			if (platform.getClass().equals(HealthGate.class))
				str += createMethodCallString(platformClass, ((HealthGate) platform).getRule(), ((HealthGate) platform).getHealthRule(), (int) platform.getX(), (int) platform.getY(), platform.getWidth(), platform.getHeight());
			str +=  (level.getPlatforms()[level.getPlatforms().length - 1] == platform ? "" : ",") + "\n";
		}
		
		str += "\t\t},\n";
		
		str += "\t\tnew Point[] {\n";
		
		for (Point point : level.getPoints()) {
			str += "\t\t\t\tnew "; 
			if (point instanceof SpawnPoint) {
				SpawnPoint spawnPoint = (SpawnPoint) point;
				str += createMethodCallString(point.getClass().getSimpleName(), (int) point.getX(), (int) point.getY(), spawnPoint.getIsActive(), spawnPoint.getIsObtainable());
			} else if (point instanceof GoalPoint) {
				str += createMethodCallString(point.getClass().getSimpleName(), (int) point.getX(), (int) point.getY());
			} else if (point instanceof PortalPoint) {
				PortalPoint portalPoint = (PortalPoint) point;
				str += createMethodCallString(point.getClass().getSimpleName(), (int) point.getX(), (int) point.getY(), portalPoint.getPortalLink());
			}
			str +=  (level.getPoints()[level.getPoints().length - 1] == point ? "" : ",") + "\n";
		}
		
		str += "\t\t},\n";
		
		str += "\t\tnew Obstacle[] {\n";
		
		for (Obstacle obstacle : level.getObstacles()) {
			String obstacleClass = obstacle.getClass().getSimpleName();
			if (obstacle instanceof Prism) {
				String color = getColorString(obstacle.getColor());
				Prism prism = (Prism) obstacle;
				str += "\t\t\t\tnew " + createMethodCallString(obstacleClass, color, (int) obstacle.getX(), (int) obstacle.getY(), getPrismDirection(prism.getDirection()));
			} else
				str += "\t\t\t\tnew " + createMethodCallString(obstacleClass, (int) obstacle.getX(), (int) obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
			str +=  (level.getObstacles()[level.getObstacles().length - 1] == obstacle ? "" : ",") + "\n";
		}
		
		str += "\t\t},\n";
		
		str += "\t\tnew Item[] {\n";
		
		for (Item item : level.getItems()) {
			String color = getColorString(item.getColor());
			String itemClass = item.getClass().getSimpleName();
			if (item.getClass().equals(ColorChanger.class)) {
				str += "\t\t\t\tnew " + createMethodCallString(itemClass, color, (int) item.getX(), (int) item.getY());
			} else if (item.getClass().equals(ColorMixer.class)) {
				ColorMixer colorMixer = (ColorMixer) item;
				str += "\t\t\t\tnew " + createMethodCallString(itemClass, color, (int) item.getX(), (int) item.getY(), colorMixer.getAdd());
			} else if (item.getClass().equals(Mirror.class)) {
				Mirror mirror = (Mirror) item;
				str += "\t\t\t\tnew " + createMethodCallString(itemClass, (int) item.getX(), (int) item.getY(), mirror.persistOnce);
			} else if (item.getClass().equals(Teleporter.class)) {
				Teleporter teleporter = (Teleporter) item;
				str += "\t\t\t\tnew " + createMethodCallString(itemClass, (int) item.getX(), (int) item.getY(), teleporter.getX2(), teleporter.getY2());
			} else {
				str += "\t\t\t\tnew " + createMethodCallString(itemClass, (int) item.getX(), (int) item.getY());
			}
			str +=  (level.getItems()[level.getItems().length - 1] == item ? "" : ",") + "\n";
		}
		
		str += "\t\t},\n";
		
		str += "\t\tnew String[] {\n";
		
		for (String string : level.getText()) {
			str += "\t\t\t\t\"" + string + "\"";
			str +=  (level.getText()[level.getText().length - 1] == string ? "" : ",") + "\n";
		}
		
		str += "\t\t}\n";
		
		str += ");\n";
		
		str += "levels[" + levelNum + "] = " + levelName + ";";
		
		System.out.println(str + "\n");
	}
	
	private static String getPrismDirection(int direction) {
		if (direction == Prism.UP)
			return "Prism.UP";
		else if (direction == Prism.RIGHT)
			return "Prism.RIGHT";
		else if (direction == Prism.DOWN)
			return "Prism.DOWN";
		else if (direction == Prism.LEFT)
			return "Prism.LEFT";
		else
			return "NotADirection";
	}
	
	private static String getColorString(CSColor color) {
		if (color != null)
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
			else 
				return "unknown";
		else
			return "nullPointer";
	}
	 
	private static String createMethodCallString(String methodName, Object... parameters) {
		String parameterString = "";
		for (int i = 0; i < parameters.length; i++) {
			parameterString += parameters[i] + (i == parameters.length - 1 ? "" : ", ");
		}
		return methodName + "(" + parameterString + ")";
	}
	
}
