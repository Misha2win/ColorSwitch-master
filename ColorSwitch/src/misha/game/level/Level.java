/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Font;
import misha.game.ColorSwitch;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.Updatable;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.SpawnPoint;

public class Level implements Updatable {
	
	private final String levelName;
	
	private LevelManager levelManager;

	private CSColor levelColor;
	
	private ArrayList<Updatable> updatables;
	
	private Platform[] platforms;
	private Point[] points;
	private Obstacle[] obstacles;
	private Item[] items;
	private String[] text;
	
	private Platform[] ghostPlatforms;
	
	@Deprecated
	public Level(String levelName, LevelManager levelManager, CSColor c) {
		this(levelName, levelManager, c, new Platform[] {}, new Point[] {}, new Obstacle[] {}, new Item[] {}, new String[] {});
	}
	
	@Deprecated
	public Level(String levelName, CSColor c) {
		this(levelName, null, c);
	}
	
	public Level(String levelName, LevelManager levelManager, CSColor levelColor, Platform[] platforms, Point[] points, Obstacle[] obstacles, Item[] items, String[] text) {
		this.levelName = levelName;
		this.levelManager = levelManager;
		this.levelColor = levelColor;
		this.updatables = new ArrayList<Updatable>();
		this.ghostPlatforms = new Platform[0];
		setPlatforms(platforms);
		setPoints(points);
		setObstacles(obstacles);
		setItems(items);
		setText(text);
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public LevelManager getLevelManager() {
		return levelManager;
	}
	
	@Deprecated
	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}
	
	public ArrayList<Updatable> getUpdatables() {
		return updatables;
	}
	
	@Deprecated
	public void setLevelColor(CSColor color) {
		levelColor = color;
	}
	
	public CSColor getLevelColor() {
		return levelColor;
	}
	
	@Deprecated
	public void setText(String[] text) {
		this.text = text;
	}
	
	public String[] getText() {
		return text;
	}
	
	@Deprecated
	public void setItems(Item[] items) {
		this.items = items;
		for (Item item : items) {
			item.setLevel(this);
			
			updatables.add(item);
		}
	}
	
	public Item[] getItems() {
		return items;
	}
	
	@Deprecated
	public void setObstacles(Obstacle[] obstacles) {
		this.obstacles = obstacles;
		for (Obstacle obstacle : obstacles) {
			obstacle.setLevel(this);
			
			updatables.add(obstacle);
		}
	}
	
	public Obstacle[] getObstacles() {
		return obstacles;
	}

	@Deprecated
	public void setPlatforms(Platform[] platforms) {
		this.platforms = platforms;
		
		for (Platform platform : platforms) {
			platform.setLevel(this);
			
			if (platform instanceof Updatable) {
				updatables.add((Updatable)platform);
			}
		}
	}
	
	public Platform[] getPlatforms() {
		return platforms;
	}
	
	@Deprecated
	public void setPoints(Point[] points) {
		this.points = points;
		
		for (Point point : points) {
			point.setLevel(this);
			
			updatables.add(point);
		}
	}
	
	public Point[] getPoints() {
		return points;
	}
	
	public SpawnPoint getActiveSpawnPoint() {
		SpawnPoint activeSpawnPoint = null;
		
		for (Point point : points) {
			if (point instanceof SpawnPoint) {
				SpawnPoint spawnPoint = (SpawnPoint) point;
				if (spawnPoint.getIsActive()) {
					activeSpawnPoint = spawnPoint;
					break;
				}
			}
		}
		
		return activeSpawnPoint;
	}
	
	public void setActiveSpawnPoint(SpawnPoint spawnPoint) {
		getActiveSpawnPoint().setIsActive(false);
		spawnPoint.setIsActive(true);
	}
	
	public void createGhostPlatforms(CSColor color) {
		ghostPlatforms = new Platform[platforms.length];
		
		for (int i = 0; i < platforms.length; i++) {
			Platform platform = platforms[i];
			
			Platform ghostPlatform = null;
			
			CSColor c = null;
			
			CSColor levelColor = (levelManager != null && levelManager.getPlayer() != null ? levelManager.getPlayer().getColor() : this.levelColor);
			
			if (color.equals(CSColor.GRAY))
				color = CSColor.GREEN;
			
			if (levelColor.equals(platform.getColor())) { // TODO refactor this be just subtract and add color instead
				c = color;
			} else if (color.equals(platform.getColor())) {
				c = levelColor;
			} else if (platform.getColor().equals(CSColor.BLACK)) {
				c = CSColor.BLACK;
			} else if (platform.getColor().collidesWith(color)) {
				c = platform.getColor().subtract(color).add(levelColor);
			} else {
				c = CSColor.GRAY;
			}
			
			int ghostX = (int) (ColorSwitch.WIDTH - platform.getX() - platform.getWidth());
			if (platform instanceof HealthGate) {
				HealthGate gate = (HealthGate) platform;
				ghostPlatform = new HealthGate(gate.getRule(), gate.getHealthRule(), ghostX, (int)gate.getY(), gate.getWidth(), gate.getHeight());
			} else if (platform instanceof MovingPlatform) {
				MovingPlatform mp = (MovingPlatform) platform;
				int ghostX1 = (int) (ColorSwitch.WIDTH - mp.getX1() - platform.getWidth());
				int ghostX2 = (int) (ColorSwitch.WIDTH - mp.getX2() - platform.getWidth());
				ghostPlatform = new MovingPlatform(c, ghostX1, (int)mp.getY1(), ghostX2, (int)mp.getY(), mp.getWidth(), mp.getHeight());
				ghostPlatform.setPos(ghostX, platform.getY());
			} else {
				ghostPlatform = new Platform(c, ghostX, (int) platform.getY(), platform.getWidth(), platform.getHeight());
			}
			
			ghostPlatform.setLevel(this);
			
			ghostPlatforms[i] = ghostPlatform;
		}
	}
	
	public Platform[] getGhostPlatforms() {
		return ghostPlatforms;
	}
	
	public void draw(Graphics2D g) {
		boolean debug = levelManager != null && levelManager.getDebugMode();
		
		for (Obstacle obstacle : obstacles) {
			obstacle.draw(g);
		}
		
		for (Point point : points) {
			point.draw(g);
		}
		
		for (Item item : items) {
			item.draw(g);
		}
		
		for (Platform platform : platforms) {
			platform.draw(g);
		}
		
		if (levelManager != null && levelManager.getPlayer() != null && levelManager.getPlayer().getMirrored()) {
			g.setColor(new Color(0, 0, 0, 50));
			g.fillRect(ColorSwitch.WIDTH / 2, 0, 1, ColorSwitch.HEIGHT);
			
			if (debug) {
				for (Platform platform : ghostPlatforms) {
					platform.draw(g);
				}
			}
		}
		
		if (text.length > 0) {
			g.setFont(new Font("MONOSPACED", Font.PLAIN, 20));
			g.setColor(Color.BLACK);
			for (int i = 0; i < text.length; i++) {
				String str = text[i];
				g.drawString(str, ColorSwitch.WIDTH / 2 - g.getFontMetrics().stringWidth(str) / 2, 200 + (i * 25));
			}
		}
	}

	@Override
	public void update() {
		for (Updatable e : updatables) {
			e.update();
		}
		
		for (Platform platform : ghostPlatforms) {
			if (platform instanceof Updatable) {
				((Updatable) platform).update();
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) { // TODO This needs to be implemented in all relevent Entity classes as well!
		if (obj instanceof Level) {
			Level other = (Level) obj;
			
			// Check platforms
			for (Platform p1 : platforms) {
				boolean foundEqual = false;
				
				for (Platform p2 : other.platforms) {
					if (p1.equals(p2)) {
						foundEqual = true;
						break;
					}
				}
				
				if (!foundEqual)
					return false;
			}
			
			// Check Obstacles
			for (Obstacle o1 : obstacles) {
				boolean foundEqual = false;
				
				for (Obstacle o2 : other.obstacles) {
					if (o1.equals(o2)) {
						foundEqual = true;
						break;
					}
				}
				
				if (!foundEqual)
					return false;
			}
			
			// Check Items
			for (Item i1 : items) {
				boolean foundEqual = false;
				
				for (Item i2 : other.items) {
					if (i1.equals(i2)) {
						foundEqual = true;
						break;
					}
				}
				
				if (!foundEqual)
					return false;
			}
			
			// Check Points
			for (Point p1 : points) {
				boolean foundEqual = false;
				
				for (Point p2 : other.points) {
					if (p1.equals(p2)) {
						foundEqual = true;
						break;
					}
				}
				
				if (!foundEqual)
					return false;
			}
			
			// Check Texts
			for (String s1 : text) {
				boolean foundEqual = false;
				
				for (String s2 : other.text) {
					if (s1.equals(s2)) {
						foundEqual = true;
						break;
					}
				}
				
				if (!foundEqual)
					return false;
			}
		}
		
		return true;
	}
	
}
