/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level;

import java.util.ArrayList;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.ColorChanger;
import misha.game.level.entity.item.ColorMixer;
import misha.game.level.entity.item.DamagePack;
import misha.game.level.entity.item.HealthPack;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.item.Mirror;
import misha.game.level.entity.item.Painter;
import misha.game.level.entity.item.SuperJump;
import misha.game.level.entity.item.Teleporter;
import misha.game.level.entity.obstacle.Acid;
import misha.game.level.entity.obstacle.Lava;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.obstacle.Prism;
import misha.game.level.entity.obstacle.Water;
import misha.game.level.entity.platform.HealthGate;
import misha.game.level.entity.platform.MovingPlatform;
import misha.game.level.entity.platform.Platform;
import misha.game.level.entity.point.GoalPoint;
import misha.game.level.entity.point.Point;
import misha.game.level.entity.point.PortalPoint;
import misha.game.level.entity.point.SpawnPoint;

public class LevelCreator {

	public static Level[] createLevels(LevelManager levelManager) {
		ArrayList<Level> levels = new ArrayList<>(50);
		
//		levels.add(new Level(
//				levelManager,
//				CSColor.GREEN,
//				new Platform[] {
//						new Platform(CSColor.BLACK, 10, 10, 10, 580),
//						new Platform(CSColor.BLACK, 20, 580, 720, 10),
//						new Platform(CSColor.BLACK, 20, 10, 720, 10),
//						new Platform(CSColor.BLACK, 730, 20, 10, 560),
//						new Platform(CSColor.RED, 110, 450, 160, 10),
//						new Platform(CSColor.BLUE, 240, 510, 130, 10),
//						new Platform(CSColor.GREEN, 100, 510, 90, 10),
//						new Platform(CSColor.BLUE, 400, 440, 130, 10),
//						new Platform(CSColor.RED, 450, 480, 130, 10),
//						new Platform(CSColor.GREEN, 510, 540, 80, 10),
//						new Platform(CSColor.BLACK, 690, 520, 20, 20),
//						new Platform(CSColor.BLACK, 670, 460, 20, 20)
//				},
//				new Point[] {
//						new SpawnPoint(20, 560, true, false)
//				},
//				new Obstacle[] {
//						new Prism(CSColor.BLUE, 710, 20, Prism.DOWN),
//						new Prism(CSColor.GREEN, 690, 20, Prism.DOWN),
//						new Prism(CSColor.RED, 670, 20, Prism.DOWN)
//				},
//				new Item[] {
//						new Mirror(30, 490, CSColor.GREEN, false)
//				},
//				new String[] {
//				}
//		));
		
		levels.add(createLevel0(levelManager));
		levels.add(createColorChanger1(levelManager));
		levels.add(createColorChanger2(levelManager));
		levels.add(createColorChanger3(levelManager));
		levels.add(createPrism6(levelManager));
		levels.add(createPrism1(levelManager));
		levels.add(createPrism2(levelManager));
		levels.add(createPrism4(levelManager));
//		levels.add(createPrism3(levelManager)); // Impossible level if you cannot deactivate Prisms
		levels.add(createTeleporter1(levelManager));
		levels.add(createMirror1(levelManager));
		levels.add(createMirror6(levelManager));
		levels.add(createMirror2(levelManager));
		levels.add(createTeleporter2(levelManager));
		levels.add(createMirror3(levelManager));
		levels.add(createMirror4(levelManager));
		levels.add(createMirror5(levelManager));
		levels.add(createPrism5(levelManager));
		// Levels beyond this line do not really fit and need more levels to back up the mechanics
		levels.add(createPortalPoint1(levelManager));
		levels.add(createSuperJump1(levelManager));
		levels.add(createHealthGate1(levelManager));
		levels.add(createHealthGate2(levelManager));
		levels.add(createColorMixer1(levelManager));
		levels.add(createColorMixer2(levelManager));
		
		while (levels.size() < 50) {
			levels.add(null);
		}
		
		levels.set(levels.size() - 1, createTestLevel(levelManager));

		return levels.toArray(new Level[levels.size()]);
	}
	
	public static Level createTestLevel(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 730, 10),
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 730, 10, 10, 580),
						new Platform(CSColor.BLACK, 10, 580, 730, 10),
						new Platform(CSColor.BLACK, 20, 490, 640, 10),
						new Platform(CSColor.BLACK, 20, 400, 600, 10),
						new MovingPlatform(CSColor.RED, 20, 90, 20, 400, 60, 10),
						new MovingPlatform(CSColor.BLUE, 140, 90, 140, 400, 60, 10),
						new MovingPlatform(CSColor.GREEN, 80, 400, 80, 90, 60, 10),
						new MovingPlatform(CSColor.BLACK, 200, 400, 200, 90, 60, 10),
						new Platform(CSColor.BLUE, 610, 260, 60, 10),
						new Platform(CSColor.BLUE, 670, 340, 60, 10),
						new Platform(CSColor.BLACK, 260, 100, 10, 260),
						new Platform(CSColor.BLUE, 670, 180, 60, 10),
						new Platform(CSColor.RED, 540, 260, 60, 10),
						new Platform(CSColor.RED, 480, 340, 60, 10),
						new Platform(CSColor.BLACK, 470, 100, 10, 250),
						new Platform(CSColor.RED, 480, 180, 60, 10),
						new Platform(CSColor.GREEN, 540, 340, 130, 10),
						new Platform(CSColor.BLACK, 600, 180, 10, 90),
						new Platform(CSColor.BLACK, 260, 90, 280, 10),
						new HealthGate(true, 50, 420, 20, 60, 70),
						new HealthGate(false, 50, 260, 20, 60, 70),
						new Platform(CSColor.BLACK, 670, 90, 60, 10),
						new Platform(CSColor.BLACK, 380, 340, 10, 60),
						new Platform(CSColor.BLACK, 610, 410, 10, 50),
						new Platform(CSColor.BLACK, 650, 500, 10, 50),
						new Platform(CSColor.CYAN, 480, 260, 60, 10),
						new Platform(CSColor.CYAN, 540, 180, 60, 10),
						new Platform(CSColor.YELLOW, 670, 260, 60, 10),
						new Platform(CSColor.YELLOW, 610, 180, 60, 10),
						new Platform(CSColor.RED, 370, 280, 10, 60),
						new Platform(CSColor.BLUE, 360, 220, 10, 60),
						new Platform(CSColor.GREEN, 350, 160, 10, 60),
						new MovingPlatform(CSColor.RED, 270, 280, 410, 280, 60, 10),
						new MovingPlatform(CSColor.GREEN, 270, 160, 410, 160, 60, 10),
						new MovingPlatform(CSColor.BLUE, 410, 220, 270, 220, 60, 10),
						new MovingPlatform(CSColor.BLACK, 410, 340, 270, 340, 60, 10),
						new MovingPlatform(CSColor.BLACK, 20, 400, 200, 90, 60, 10),
						new Platform(CSColor.BLACK, 690, 400, 40, 10)
				},
				new Point[] {
						new SpawnPoint(710, 560, true, false),
						new PortalPoint(710, 380, 0),
						new PortalPoint(400, 20, 0),
						new PortalPoint(320, 20, 1),
						new PortalPoint(20, 20, 1),
						new SpawnPoint(340, 70, false, true),
						new SpawnPoint(380, 70, false, true)
				},
				new Obstacle[] {
						new Acid(670, 20, 60, 70),
						new Lava(480, 100, 60, 240),
						new Water(670, 100, 60, 240),
						new Prism(CSColor.BLUE, 280, 100, Prism.DOWN),
						new Prism(CSColor.GREEN, 320, 100, Prism.DOWN),
						new Prism(CSColor.RED, 400, 100, Prism.DOWN),
						new Prism(CSColor.BLACK, 440, 100, Prism.DOWN)
				},
				new Item[] {
						new ColorChanger(CSColor.RED, 40, 520),
						new ColorChanger(CSColor.GREEN, 80, 520),
						new ColorChanger(CSColor.BLUE, 120, 520),
						new ColorChanger(CSColor.YELLOW, 160, 520),
						new ColorChanger(CSColor.MAGENTA, 200, 520),
						new ColorChanger(CSColor.CYAN, 240, 520),
						new ColorChanger(CSColor.WHITE, 280, 520),
						new ColorChanger(CSColor.BLACK, 320, 520),
						new ColorMixer(CSColor.RED, 40, 430, true),
						new ColorMixer(CSColor.GREEN, 80, 430, true),
						new ColorMixer(CSColor.BLUE, 120, 430, true),
						new ColorMixer(CSColor.YELLOW, 160, 430, true),
						new ColorMixer(CSColor.MAGENTA, 200, 430, true),
						new ColorMixer(CSColor.CYAN, 240, 430, true),
						new ColorMixer(CSColor.WHITE, 280, 430, true),
						new ColorMixer(CSColor.RED, 320, 430, false),
						new ColorMixer(CSColor.GREEN, 360, 430, false),
						new ColorMixer(CSColor.BLUE, 400, 430, false),
						new ColorMixer(CSColor.YELLOW, 440, 430, false),
						new ColorMixer(CSColor.MAGENTA, 480, 430, false),
						new ColorMixer(CSColor.CYAN, 520, 430, false),
						new ColorMixer(CSColor.WHITE, 560, 430, false),
						new DamagePack(360, 520),
						new HealthPack(400, 520),
						new Mirror(480, 520, CSColor.GRAY, false),
						new Mirror(520, 520, CSColor.GRAY, true),
						new Teleporter(560, 520, -1, -1),
						new SuperJump(440, 520),
						//new Teleporter(600, 520, 710, 70)
						new Painter(600, 520, true)
				},
				new String[] {
						"",
						"",
						"This level is the testing level.",
						"There are no levels after this."
				}
		);
	}
	
	public static Level createLevel0(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.BLUE,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 730, 10),
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 730, 10, 10, 580),
						new Platform(CSColor.BLACK, 10, 580, 730, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 360, 520)
				},
				new String[] {
						"In this game, you must find out the rules and on your own!",
						"Use WASD to MOVE,",
						"Use E to USE the item in your inventory,",
						"Use R to RESTART the current level you are in.",
						"Good luck ;)"
				}
		);
	}

	public static Level createColorChanger1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.RED, 330, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 560, 520),
						new ColorChanger(CSColor.BLUE, 160, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createColorChanger2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.RED, 190, 20, 90, 560),
						new Platform(CSColor.BLUE, 470, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 630, 520),
						new ColorChanger(CSColor.BLUE, 90, 520),
						new ColorChanger(CSColor.RED, 360, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createColorChanger3(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 670, 120, 60, 10),
						new Platform(CSColor.RED, 530, 210, 110, 10),
						new Platform(CSColor.RED, 250, 390, 110, 10),
						new Platform(CSColor.BLUE, 110, 480, 110, 10),
						new Platform(CSColor.BLUE, 390, 300, 110, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 100)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.BLUE, 670, 520),
						new ColorChanger(CSColor.RED, 150, 420),
						new ColorChanger(CSColor.BLUE, 290, 330),
						new ColorChanger(CSColor.RED, 430, 240),
						new ColorChanger(CSColor.GREEN, 570, 150)
				},
				new String[] {
				}
		);
	}
	
	public static Level createSuperJump1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 370, 300, 360, 280)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 280)
				},
				new Obstacle[] {
				},
				new Item[] {
						new SuperJump(310, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createHealthGate1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 190, 20, 60, 430),
						new Platform(CSColor.BLACK, 560, 20, 0, 0),
						new Platform(CSColor.BLACK, 500, 20, 60, 430),
						new HealthGate(true, 50, 190, 450, 60, 130),
						new HealthGate(false, 50, 500, 450, 60, 130)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Lava(250, 20, 250, 560)
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
	public static Level createHealthGate2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 190, 20, 60, 430),
						new Platform(CSColor.BLACK, 500, 20, 60, 430),
						new HealthGate(false, 99, 190, 450, 60, 130),
						new HealthGate(true, 99, 500, 450, 60, 130)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new DamagePack(90, 520),
						new HealthPack(360, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createTeleporter1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 330, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Teleporter(160, 520, 420, 20)
				},
				new String[] {
				}
		);
	}
	
	public static Level createTeleporter2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 20, 200, 280, 50),
						new Platform(CSColor.BLACK, 450, 200, 280, 50),
						new Platform(CSColor.BLACK, 20, 250, 280, 330),
						new Platform(CSColor.BLACK, 450, 250, 280, 330)
				},
				new Point[] {
						new SpawnPoint(20, 180, true, false),
						new GoalPoint(710, 180)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 360, 540),
						new Teleporter(240, 140, -1, -1)
				},
				new String[] {
				}
		);
	}
	
	public static Level createMirror1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 330, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Teleporter(560, 520, 420, 20),
						new Mirror(270, 520, CSColor.GRAY, false)
				},
				new String[] {
				}
		);
	}
	
	public static Level createMirror2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 330, 20, 90, 560),
						new Platform(CSColor.BLACK, 420, 490, 230, 20),
						new Platform(CSColor.BLACK, 580, 400, 140, 0),
						new Platform(CSColor.BLACK, 500, 390, 230, 20),
						new Platform(CSColor.BLACK, 420, 290, 230, 20),
						new Platform(CSColor.BLACK, 500, 190, 230, 20),
						new Platform(CSColor.BLACK, 420, 90, 230, 20)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Mirror(270, 520, CSColor.GRAY, false),
						new Teleporter(440, 40, 420, 560)
				},
				new String[] {
				}
		);
	}

	public static Level createMirror3(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 20, 300, 160, 280),
						new Platform(CSColor.BLACK, 380, 300, 350, 280),
						new Platform(CSColor.BLACK, 250, 20, 60, 240)
				},
				new Point[] {
						new SpawnPoint(20, 280, true, false),
						new GoalPoint(710, 280)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Mirror(120, 240, CSColor.GRAY, false)
				},
				new String[] {
				}
		);
	}
	
	public static Level createMirror4(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.BLUE,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 20, 300, 160, 280),
						new Platform(CSColor.BLACK, 380, 300, 350, 280),
						new Platform(CSColor.RED, 250, 20, 60, 280),
						new Platform(CSColor.BLUE, 20, 20, 230, 210)
				},
				new Point[] {
						new SpawnPoint(20, 280, true, false),
						new GoalPoint(710, 280)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Mirror(120, 240, CSColor.RED, true),
						new ColorChanger(CSColor.GREEN, 540, 170)
				},
				new String[] {
				}
		);
	}
	
	public static Level createMirror5(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 670, 120, 60, 10),
						new Platform(CSColor.RED, 530, 210, 110, 10),
						new Platform(CSColor.RED, 250, 390, 110, 10),
						new Platform(CSColor.BLUE, 110, 480, 110, 10),
						new Platform(CSColor.BLUE, 390, 300, 110, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 100)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 570, 150),
						new Mirror(670, 520, CSColor.BLUE, true)
				},
				new String[] {
				}
		);
	}

	public static Level createMirror6(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 20, 450, 710, 130),
						new Platform(CSColor.BLACK, 20, 350, 230, 100),
						new Platform(CSColor.BLACK, 600, 250, 130, 200)
				},
				new Point[] {
						new SpawnPoint(20, 330, true, false),
						new GoalPoint(710, 230)
				},
				new Obstacle[] {
				},
				new Item[] {
						new Mirror(210, 290, CSColor.GRAY, false)
				},
				new String[] {
				}
		);
	}
	
	public static Level createColorMixer1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.BLUE,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 370, 300, 10, 280),
						new Platform(CSColor.BLUE, 20, 300, 350, 280),
						new Platform(CSColor.RED, 380, 300, 350, 280)
				},
				new Point[] {
						new SpawnPoint(20, 280, true, false),
						new GoalPoint(710, 280)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 410, 240),
						new ColorMixer(CSColor.RED, 310, 240, true)
				},
				new String[] {
				}
		);
	}
	
	public static Level createColorMixer2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.CYAN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLUE, 330, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorMixer(CSColor.BLUE, 160, 520, false)
				},
				new String[] {
				}
		);
	}
	
	public static Level createPortalPoint1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLACK, 330, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560),
						new PortalPoint(420, 560, 0),
						new PortalPoint(310, 560, 0)
				},
				new Obstacle[] {
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 570, 520),
						new ColorChanger(CSColor.BLUE, 160, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism1(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.RED, 190, 20, 90, 560),
						new Platform(CSColor.BLUE, 470, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Prism(CSColor.BLUE, 90, 480, Prism.UP),
						new Prism(CSColor.RED, 360, 480, Prism.UP),
						new Prism(CSColor.GREEN, 630, 480, Prism.UP)
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism2(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new MovingPlatform(CSColor.BLACK, 20, 290, 640, 290, 90, 10)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Prism(CSColor.BLUE, 50, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 210, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 370, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 530, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 690, 20, Prism.DOWN)
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism3(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.BLUE,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.RED, 190, 20, 90, 560),
						new Platform(CSColor.RED, 470, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Prism(CSColor.RED, 100, 560, Prism.UP),
						new Prism(CSColor.RED, 360, 560, Prism.UP),
						new Prism(CSColor.GREEN, 640, 560, Prism.UP)
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism4(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.RED,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.RED, 190, 20, 90, 560),
						new Platform(CSColor.BLUE, 470, 20, 90, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Prism(CSColor.RED, 150, 20, Prism.DOWN),
						new Prism(CSColor.RED, 170, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 430, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 450, 20, Prism.DOWN)
				},
				new Item[] {
						new ColorChanger(CSColor.GREEN, 630, 520),
						new ColorChanger(CSColor.BLUE, 90, 520),
						new ColorChanger(CSColor.RED, 360, 520)
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism5(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560),
						new Platform(CSColor.BLUE, 340, 20, 10, 430),
						new Platform(CSColor.BLUE, 110, 450, 240, 10),
						new Platform(CSColor.BLUE, 100, 250, 240, 10),
						new Platform(CSColor.BLUE, 20, 150, 240, 10),
						new Platform(CSColor.BLUE, 20, 350, 240, 10),
						new Platform(CSColor.BLUE, 390, 450, 240, 10),
						new Platform(CSColor.BLUE, 490, 350, 240, 10),
						new Platform(CSColor.RED, 400, 250, 240, 10),
						new Platform(CSColor.RED, 490, 150, 240, 10),
						new Platform(CSColor.BLUE, 390, 260, 10, 190),
						new Platform(CSColor.RED, 390, 20, 10, 240)
				},
				new Point[] {
						new GoalPoint(710, 560),
						new SpawnPoint(20, 560, true, false)
				},
				new Obstacle[] {
						new Prism(CSColor.RED, 360, 20, Prism.DOWN)
				},
				new Item[] {
						new ColorChanger(CSColor.BLUE, 670, 520),
						new ColorChanger(CSColor.RED, 50, 90),
						new ColorChanger(CSColor.GREEN, 670, 90)
				},
				new String[] {
				}
		);
	}
	
	public static Level createPrism6(LevelManager levelManager) {
		return new Level(
				levelManager,
				CSColor.GREEN,
				new Platform[] {
						new Platform(CSColor.BLACK, 10, 10, 10, 580),
						new Platform(CSColor.BLACK, 20, 580, 720, 10),
						new Platform(CSColor.BLACK, 20, 10, 720, 10),
						new Platform(CSColor.BLACK, 730, 20, 10, 560)
				},
				new Point[] {
						new SpawnPoint(20, 560, true, false),
						new GoalPoint(710, 560)
				},
				new Obstacle[] {
						new Prism(CSColor.RED, 130, 20, Prism.DOWN),
						new Prism(CSColor.BLUE, 290, 20, Prism.DOWN),
						new Prism(CSColor.RED, 450, 20, Prism.DOWN),
						new Prism(CSColor.GREEN, 610, 20, Prism.DOWN)
				},
				new Item[] {
				},
				new String[] {
				}
		);
	}
	
}

//// *** Create level 49 ***
//Level level0 = new Level(
//		levelManager,
//		CSColor.BLUE,
//		new Platform[] {
//				new Platform(CSColor.BLACK, 10, 10, 730, 10),
//				new Platform(CSColor.BLACK, 10, 10, 10, 580),
//				new Platform(CSColor.BLACK, 730, 10, 10, 580),
//				new Platform(CSColor.BLACK, 10, 580, 730, 10),
//				new Platform(CSColor.BLACK, 10, 500, 100, 10),
//				new Platform(CSColor.BLACK, 140, 400, 100, 10),
//				new Platform(CSColor.RED, 270, 300, 100, 10),
//				new Platform(CSColor.BLUE, 400, 200, 100, 10),
//				new Platform(CSColor.BLACK, 530, 100, 100, 10),
//				new MovingPlatform(CSColor.BLACK, 60, 60, 260, 60, 100, 10),
//				new MovingPlatform(CSColor.RED, 60, 60, 60, 260, 100, 10),
//				new HealthGate(false, 50, 550, 500, 100, 80)
//		},
//		new Point[] {
//				new SpawnPoint(20, 560, true, false),
//				new SpawnPoint(220, 560, false, true),
//				new SpawnPoint(320, 560, false, true),
//				new GoalPoint(710, 560)
//		},
//		new Obstacle[] {
//				new Lava(570, 50, 60, 50)
//		},
//		new Item[] {
//				new HealthPack(120, 460),
//				new HealthPack(220, 460),
//				new HealthPack(320, 460),
//				new SuperJump(20, 460),
//				new ColorMixer(CSColor.GREEN, 420, 460, true),
//				new ColorChanger(CSColor.BLUE, 520, 460),
//				new ColorChanger(CSColor.RED, 620, 460),
//				new Teleporter(165, 460, 300, 400),
//				new Mirror(260, 460, false)
//		},
//		new String[] {
//				"",
//				"",
//				"This level is the testing level.",
//				"There are no levels after this."
//		}
//);
//levels[49] = level0;

//// *** Create level 12 *** DISCARDED LEVEL
//Level level12 = new Level(levelManager, CSColor.BLUE);
//level12.setPlatforms(new Platform[] {
//	new Platform(CSColor.BLACK, 10, 10, 10, 580),
//	new Platform(CSColor.BLACK, 20, 580, 720, 10),
//	new Platform(CSColor.BLACK, 20, 10, 720, 10),
//	new Platform(CSColor.BLACK, 730, 20, 10, 560),
//	new Platform(CSColor.BLUE, 160, 20, 50, 560),
//	new Platform(CSColor.RED, 350, 20, 50, 560),
//	new Platform(CSColor.BLUE, 540, 20, 40, 560)
//});
//level12.setGoals(new GoalPoint[] {
//	new GoalPoint(710, 560)
//});
//level12.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, 560)
//});
//level12.getSpawnPoints()[0].setIsActive(true);
//level12.getSpawnPoints()[0].setIsObtainable(false);
//level12.setObstacles(new Obstacle[] {
//});
//level12.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, 70, 520),
//	new ColorChanger(CSColor.BLUE, 260, 520),
//	new ColorChanger(CSColor.GREEN, 640, 520)
//});
//level12.setText(new String[] {
//});
//levels[11] = level12;

//// THE OLD LEVELS
//// Create level 1
//Level level1 = new Level(levelManager, CSColor.BLUE);
//level1.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10)
//});
//level1.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level1.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level1.getSpawnPoints()[0].setIsActive(true);
//level1.setItems(new Item[] {
//	new ColorChanger(CSColor.GREEN, Game.WIDTH / 2 - 15, Game.HEIGHT - 100)
//});
//level1.setText(new String[] {
//	"In this game, you must find out the rules and",
//	"mechanics on you own!",
//	"Good luck! :)",
//	"(Only hint: press E to use an item in your iventory",
//	"and use 1, 2, and 3 to select them!)"
//	
//});
//levels[0] = level1;
//
//// Create level 2
//Level level2 = new Level(levelManager, CSColor.BLUE);
//level2.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(CSColor.RED, Game.WIDTH / 2 - 50, 10, 120, Game.HEIGHT - 30)
//});
//level2.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level2.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level2.getSpawnPoints()[0].setIsActive(true);
//level2.setItems(new Item[] {
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 170, Game.HEIGHT - 100)
//});
//levels[1] = level2;
//
//// Create level 3
//Level level3 = new Level(levelManager, CSColor.BLUE);
//level3.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(CSColor.BLUE, Game.WIDTH / 2 - 50, 10, 120, Game.HEIGHT - 30)
//});
//level3.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level3.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level3.getSpawnPoints()[0].setIsActive(true);
//level3.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, 150, Game.HEIGHT - 100),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 170, Game.HEIGHT - 100)
//});
//levels[2] = level3;
//
//// ***   Create level 4   ***
//Level level4 = new Level(levelManager, CSColor.BLUE);
//level4.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(CSColor.RED, 180, Game.HEIGHT - 150, 100, 10),
//	new Platform(CSColor.BLUE, 330, Game.HEIGHT - 250, 100, 10),
//	new Platform(CSColor.RED, 480, Game.HEIGHT - 350, 100, 10),
//	new Platform(CSColor.BLUE, 630, Game.HEIGHT - 450, 100, 10)
//});
//level4.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, 130)
//});
//level4.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level4.getSpawnPoints()[0].setIsActive(true);
//level4.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, Game.WIDTH - 100, Game.HEIGHT - 100),
//	new ColorChanger(CSColor.BLUE, 215, Game.HEIGHT - 190),
//	new ColorChanger(CSColor.RED, 365, Game.HEIGHT - 290),
//	new ColorChanger(CSColor.BLUE, 515, Game.HEIGHT - 390),
//	new ColorChanger(CSColor.GREEN, 665, Game.HEIGHT - 490)
//});
//levels[3] = level4;
//
//// ***   Create level 5   ***
//Level level5 = new Level(levelManager, CSColor.BLUE);
//level5.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(20, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	new Platform(530, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	
//	// The MovingPlatforms
//	new MovingPlatform(CSColor.RED, 230, Game.HEIGHT - 390, 420, Game.HEIGHT - 390, 100, 20)
//});
//level5.setObstacles(new Obstacle[] {
//	new Lava(220, Game.HEIGHT - 200, 310, 180)
//});
//level5.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 420)
//});
//level5.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 420)
//});
//level5.getSpawnPoints()[0].setIsActive(true);
//level5.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, Game.WIDTH / 2 - 15, 100),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH / 2 - 15, 140)
//});
//levels[4] = level5;
//
//// ***   Create level 6   ***
//Level level6 = new Level(levelManager, CSColor.BLUE);
//level6.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(20, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	new Platform(530, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	
//	// The MovingPlatforms
//	new MovingPlatform(CSColor.RED, 230, Game.HEIGHT - 390, 420, Game.HEIGHT - 390, 100, 20),
//	
//	new HealthGate(true, 75, Game.WIDTH - 120, 20, 60, 180)
//});
//level6.setObstacles(new Obstacle[] {
//	new Lava(220, 20, 155, Game.HEIGHT - 40),
//	new Water(220 + 155, 20, 155, Game.HEIGHT - 40)
//});
//level6.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 420)
//});
//level6.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 420)
//});
//level6.getSpawnPoints()[0].setIsActive(true);
//level6.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, Game.WIDTH / 2 - 15, 100),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH / 2 - 15, 140),
//	new HealthPack(Game.WIDTH - 185, 150)
//});
//levels[5] = level6;
//
//// ***   Create level 7   ***
//Level level7 = new Level(levelManager, CSColor.GREEN);
//level7.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(20, Game.HEIGHT - 450, 600, 20),
//	new Platform(130, Game.HEIGHT - 340, 600, 20),
//	new Platform(20, Game.HEIGHT - 230, 600, 20),
//	new Platform(130, Game.HEIGHT - 120, 600, 20),
//	new HealthGate(true, 75, Game.WIDTH - 110, Game.HEIGHT - 100, 60, 80)
//});
//level7.setObstacles(new Obstacle[] {
//	new Lava(20, Game.HEIGHT - 450, 355, 430),
//	new Water(375, Game.HEIGHT - 450, 355, 430)
//});
//level7.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level7.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 470)
//});
//level7.getSpawnPoints()[0].setIsActive(true);
//level7.setItems(new Item[] {
//	new HealthPack(Game.WIDTH - 550, 115),
//	new HealthPack(Game.WIDTH - 450, 115),
//	new HealthPack(Game.WIDTH - 350, 115),
//	new HealthPack(Game.WIDTH - 250, 115),
//	new HealthPack(Game.WIDTH - 550, 220),
//	new HealthPack(Game.WIDTH - 450, 220),
//	new HealthPack(Game.WIDTH - 350, 220),
//	new HealthPack(Game.WIDTH - 250, 220),
//	new HealthPack(Game.WIDTH - 550, 330),
//	new HealthPack(Game.WIDTH - 450, 330),
//	new HealthPack(Game.WIDTH - 350, 330),
//	new HealthPack(Game.WIDTH - 250, 330),
//	new HealthPack(Game.WIDTH - 550, 440),
//	new HealthPack(Game.WIDTH - 450, 440),
//	new HealthPack(Game.WIDTH - 350, 440),
//	new HealthPack(Game.WIDTH - 250, 440),
//	new HealthPack(Game.WIDTH - 550, 540),
//	new HealthPack(Game.WIDTH - 450, 540),
//	new HealthPack(Game.WIDTH - 350, 540),
//	new HealthPack(Game.WIDTH - 250, 540)
//});
//levels[6] = level7;
//
//// ***   Create level 8   ***
//Level level8 = new Level(levelManager, CSColor.BLUE);
//level8.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(CSColor.BLUE, 200, 20, 50, Game.HEIGHT - 40),
//	new Platform(CSColor.RED, 500, 20, 50, Game.HEIGHT - 40)
//});
//level8.setObstacles(new Obstacle[] {
//	new Water(250, 20, 250, 450)
//});
//level8.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level8.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40),
//	new SpawnPoint(Game.WIDTH / 2 - 10, Game.HEIGHT - 40)
//	
//});
//level8.getSpawnPoints()[0].setIsActive(true);
//level8.getSpawnPoints()[1].setIsObtainable(true);
//level8.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, 85, Game.HEIGHT - 100),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 125, Game.HEIGHT - 100)
//});
//levels[7] = level8;
//
//// ***   Create level 9   ***
//Level level9 = new Level(levelManager, CSColor.BLUE);
//level9.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(230, 20, 20, Game.HEIGHT - 140),
//	new MovingPlatform(CSColor.RED, 250, 140, 250, Game.HEIGHT - 20, 240, 10),
//	new Platform(490, 140, 20, Game.HEIGHT - 160),
//	new Platform(490, 140, 140, 20),
//	new Platform(590, 240, 140, 20),
//	new Platform(490, 340, 140, 20),
//	new Platform(590, 440, 140, 20),
//	new HealthGate(true, 99, Game.WIDTH - 240, Game.HEIGHT - 140, 220, 30)
//});
//level9.setObstacles(new Obstacle[] {
//	new Lava(250, 20, 240, 560),
//	new Water(500, 160, 240, 300)
//});
//level9.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level9.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level9.getSpawnPoints()[0].setIsActive(true);
//level9.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, 85, Game.HEIGHT - 100),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 215, Game.HEIGHT - 60),
//	new ColorChanger(CSColor.BLUE, Game.WIDTH - 205, 100)
//});
//levels[8] = level9;
//
//// ***   Create level 10   ***
//Level level10 = new Level(levelManager, CSColor.BLUE);
//level10.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(Game.WIDTH / 2 - 5, 10, 10, Game.HEIGHT - 110),
//	new Platform(Game.WIDTH / 4 - 5, 100, 10, Game.HEIGHT - 120),
//	new Platform(20, 100, 100, 10),
//	new Platform(90, 200, 100, 10),
//	new Platform(20, 300, 100, 10),
//	new Platform(90, 400, 100, 10),
//	new Platform(20, 500, 100, 10),
//	new Platform(CSColor.BLUE, Game.WIDTH / 2 - 5, Game.HEIGHT - 100, 10, 80),
//	new HealthGate(true, 70, Game.WIDTH / 2 - 185, Game.HEIGHT - 100, 180, 40),
//	new MovingPlatform(CSColor.BLUE, 380, 100, 380, Game.HEIGHT - 20, 180, 10),
//	new Platform(3 * Game.WIDTH / 4 - 5, 100, 10, Game.HEIGHT - 120),
//	new HealthGate(true, 99, 565, 100, 165, 50),
//	new HealthGate(true, 99, 565, 450, 165, 50)
//});
//level10.setObstacles(new Obstacle[] {
//	new Lava(20, 110, 170, 390),
//	new Lava(190, 110, 180, 390),
//	new Water(190, 500, 10, 90),
//	new Lava(380, 100, 100, 60),
//	new Lava(470, 200, 100, 60),
//	new Lava(380, 300, 100, 60),
//	new Lava(470, 400, 100, 60),
//	new Lava(560, 150, 50, 350),
//	new Lava(685, 150, 50, 350),
//	new Lava(565, 400, 165, 50)
//});
//level10.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level10.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40),
//	new SpawnPoint(Game.WIDTH / 2 + 80, Game.HEIGHT - 40)
//});
//level10.getSpawnPoints()[0].setIsActive(true);
//level10.getSpawnPoints()[1].setIsObtainable(true);
//level10.setItems(new Item[] {
//	new HealthPack(40, 240),
//	new HealthPack(40, 340),
//	new HealthPack(135, 290),
//	new ColorChanger(CSColor.RED, 170, 60),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 115, Game.HEIGHT / 2 - 15),
//	new ColorChanger(CSColor.RED, Game.WIDTH - 115, Game.HEIGHT / 2 - 70)
//});
//levels[9] = level10;
//
//// Create level 11
//Level level11 = new Level(levelManager, CSColor.GREEN);
//level11.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10)
//});
//level11.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level11.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level11.getSpawnPoints()[0].setIsActive(true);
//level11.setItems(new Item[] {
//});
//level11.setText(new String[] {
//	"Almost there!",
//	"Get ready for more new mechanics ;)"
//});
//levels[10] = level11;
//
//// Create level 12
//Level level12 = new Level(levelManager, CSColor.BLUE);
//level12.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(20, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	new Platform(530, Game.HEIGHT - 400, 200, Game.HEIGHT - 220)
//});
//level12.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, 180)
//});
//level12.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, 180)
//});
//level12.getSpawnPoints()[0].setIsActive(true);
//level12.setItems(new Item[] {
//	new SuperJump(120, 130),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH / 2 - 15, 170)
//});
//levels[11] = level12;
//
//// Create level 13
//Level level13 = new Level(levelManager, CSColor.BLUE);
//level13.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(530, Game.HEIGHT - 400, 200, Game.HEIGHT - 220),
//	new Platform(CSColor.BLUE, 530, 20, 20, 180)
//});
//level13.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, 180)
//});
//level13.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level13.getSpawnPoints()[0].setIsActive(true);
//level13.setItems(new Item[] {
//	new SuperJump(Game.WIDTH / 2 - 15, 450),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH / 2 - 15, 170)
//});
//levels[12] = level13;
//
//// Create level 14
//Level level14 = new Level(levelManager, CSColor.BLUE);
//level14.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20 - 310, 10),
//	new Platform(Game.WIDTH - 210, Game.HEIGHT - 20, 200, 10),
//	
//	// The platforms
//	new Platform(20, 140, 550, 10),
//	new Platform(Game.WIDTH - 190, 140, 10, 130),
//	new Platform(Game.WIDTH - 190, 140, 100, 10),
//	new Platform(Game.WIDTH - 110, 200, 100, 10),
//	new Platform(Game.WIDTH - 190, 260, 100, 10),
//	new Platform(Game.WIDTH - 300, 220, 10, 120),
//	new Platform(Game.WIDTH - 400, 150, 10, 120),
//	new Platform(Game.WIDTH - 510, 220, 10, 120),
//	new Platform(Game.WIDTH - 620, 150, 10, 120),
//	new Platform(130, 340, 600, 10),
//	new Platform(130, 340, 10, 120),
//	new Platform(260, 460, 10, 130),
//	new Platform(400, 340, 10, 120),
//	new Platform(540, 420, 10, 160),
//	new Platform(10, 260, 90, 10),
//	new Platform(CSColor.RED, 20, 450, 110, 10),
//	new HealthGate(false, 50, 140, 350, 260, 110),
//	new HealthGate(false, 45, 410, 350, 130, 110)
//	
//	//new HealthGate(false, 50, 20, 150, Game.WIDTH - 40, Game.HEIGHT - 170)
//});
//level14.setObstacles(new Obstacle[] {
//	new Lava(190, 20, 470, 120),
//	new HealthPool(Game.WIDTH - 150, 200, 100, 10),
//	new HealthPool(Game.WIDTH - 150, 260, 100, 10),
//	new HealthPool(Game.WIDTH - 60, 310, 10, 30),
//	new HealthPool(Game.WIDTH - 120, 310, 10, 30),
//	new HealthPool(Game.WIDTH - 180, 310, 10, 30),
//	new HealthPool(Game.WIDTH - 240, 310, 10, 30),
//	new HealthPool(20, 150, Game.WIDTH - 210, 110),
//	new HealthPool(330, Game.HEIGHT / 2 - 30, 50, 70),
//	new Lava(20, 260, Game.WIDTH - 210, 10),
//	new Lava(20, 260, 80, 190),
//	new HealthPool(140, 350, 100, 110),
//	new HealthPool(140, 350, 260, 60),
//	new HealthPool(300, 350, 110, 110),
//	new HealthPool(410, 350, 50, 110),
//	new HealthPool(410, 350, 130, 20),
//	new HealthPool(510, 420, 30, 40)
//});
//level14.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level14.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, 120)
//});
//level14.getSpawnPoints()[0].setIsActive(true);
//level14.setItems(new Item[] {
//	new SuperJump(Game.WIDTH - 280, Game.HEIGHT - 60),
//	new ColorChanger(CSColor.GREEN, Game.WIDTH - 160, Game.HEIGHT - 60),
//	new HealthPack(605, 160),
//	new HealthPack(605, 220),
//	new HealthPack(290, 280),
//	new HealthPack(330, 280),
//	new HealthPack(370, 280),
//	new HealthPack(200, 300),
//	new HealthPack(150, 210),
//	new DamagePack(60, 195),
//	new ColorChanger(CSColor.RED, true, 35, 480),
//	new ColorChanger(CSColor.RED, true, 85, 480),
//});
//levels[13] = level14;
//
//// Create level 15
//Level level15 = new Level(levelManager, CSColor.GREEN);
//level15.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10)
//});
//level15.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level15.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level15.getSpawnPoints()[0].setIsActive(true);
//level15.setItems(new Item[] {
//});
//level15.setText(new String[] {
//	"Congrats you have finished all of the levels",
//	"I have made so far!",
//	"",
//	"There was a problem loading your stats!"
//});
//levels[14] = level15;
//
//// Create level 16
//Level level16 = new Level(levelManager, CSColor.GREEN);
//level16.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 - 60, 10, 120, Game.HEIGHT - 30)
//});
//level16.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level16.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level16.getSpawnPoints()[0].setIsActive(true);
//level16.setItems(new Item[] {
//	new Teleporter(Game.WIDTH / 2 - 190, Game.HEIGHT - 100, Game.WIDTH / 2 - 60 + 220, Game.HEIGHT - 100)
//});
//levels[15] = level16;
//
//// Create level 17
//Level level17 = new Level(levelManager, CSColor.GREEN);
//level17.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// Larger divider
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 - 60, 10, 120, Game.HEIGHT - 30),
//	
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 10 + 40, 100, 225, 10),
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 100 + 40, 175, 225, 10),
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 10 + 40, 250, 225, 10),
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 100 + 40, 325, 225, 10),
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 10 + 40, 400, 225, 10),
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 + 100 + 40, 475, 225, 10)
//});
//level17.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT - 40)
//});
//level17.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level17.getSpawnPoints()[0].setIsActive(true);
//level17.setItems(new Item[] {
//	new Teleporter(Game.WIDTH - 80, 50, Game.WIDTH / 2 + 60, Game.HEIGHT - 40),
//	new Mirror(Game.WIDTH / 2 - 120, Game.HEIGHT - 80)
//});
//levels[16] = level17;
//
//// Create level 17
//Level level18 = new Level(levelManager, CSColor.GREEN);
//level18.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// Larger divider
//	new Platform(CSColor.BLACK, Game.WIDTH / 2 - 60, 10, 120, Game.HEIGHT - 30),
//	
//	new Platform(CSColor.BLACK, Game.WIDTH / 2, Game.HEIGHT / 2, Game.WIDTH / 2 - 10, Game.HEIGHT / 2 - 10)
//});
//level18.setGoals(new GoalPoint[] {
//	new GoalPoint(Game.WIDTH - 40, Game.HEIGHT / 2 - 20)
//});
//level18.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level18.getSpawnPoints()[0].setIsActive(true);
//level18.setItems(new Item[] {
//	new Teleporter(Game.WIDTH / 4 - 30, Game.HEIGHT / 2 - 100, Game.WIDTH / 2 + 60, Game.HEIGHT / 2 - 20),
//	new Mirror(Game.WIDTH / 4 - 30, Game.HEIGHT - 100)
//});
//levels[17] = level18;
//
//Level level19 = new Level(levelManager, CSColor.BLUE);
//level19.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	
//	// The platforms
//	new Platform(CSColor.RED, 180, Game.HEIGHT - 150, 100, 10),
//	new Platform(CSColor.BLUE, 330, Game.HEIGHT - 250, 100, 10),
//	new Platform(CSColor.RED, 480, Game.HEIGHT - 350, 100, 10),
//	new Platform(CSColor.BLUE, 630, Game.HEIGHT - 450, 100, 10)
//});
//level19.setGoals(new GoalPoint[] {
//	new GoalPoint(20, 130)
//});
//level19.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//});
//level19.getSpawnPoints()[0].setIsActive(true);
//level19.setItems(new Item[] {
//	new Mirror(Game.WIDTH - 100, Game.HEIGHT - 100),
//	
//	new ColorChanger(CSColor.RED, Game.WIDTH / 2 - 10, Game.HEIGHT / 2),
//		
//	new ColorChanger(CSColor.GREEN, 665, Game.HEIGHT - 490)
//});
//levels[18] = level19;
//
//Level level2o = new Level(levelManager, CSColor.GREEN);
//level19.setPlatforms(new Platform[] {
//	new Platform(CSColor.BLACK, 10, 10, 10, 580),
//	new Platform(CSColor.BLACK, 20, 580, 720, 10),
//	new Platform(CSColor.BLACK, 20, 10, 720, 10),
//	new Platform(CSColor.BLACK, 730, 20, 10, 560),
//	new Platform(CSColor.BLACK, 360, 20, 30, 560),
//	new Platform(CSColor.BLACK, 20, 140, 710, 20),
//	new Platform(CSColor.BLACK, 20, 290, 710, 20),
//	new Platform(CSColor.BLACK, 20, 440, 710, 20)
//});
//level19.setGoals(new GoalPoint[] {
//	new GoalPoint(710, 120)
//});
//level19.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, 560)
//});
//level19.getSpawnPoints()[0].setIsActive(true);
//level19.getSpawnPoints()[0].setIsObtainable(false);
//level19.setObstacles(new Obstacle[] {
//});
//level19.setItems(new Item[] {
//	new Teleporter(320, 540, 390, 560),
//	new Teleporter(690, 540, 20, 420),
//	new Teleporter(320, 400, 390, 420),
//	new Teleporter(690, 400, 20, 270),
//	new Teleporter(320, 250, 390, 270),
//	new Teleporter(690, 250, 20, 120),
//	new Teleporter(320, 100, 390, 120)
//});
//level19.setText(new String[] {
//});
//levels[19] = level19;
//
//Level level21 = new Level(levelManager, CSColor.BLUE);
//level21.setPlatforms(new Platform[] {
//	new Platform(CSColor.BLACK, 10, 10, 10, 580),
//	new Platform(CSColor.BLACK, 20, 580, 720, 10),
//	new Platform(CSColor.BLACK, 730, 10, 10, 570),
//	new Platform(CSColor.BLACK, 20, 10, 710, 10),
//	new Platform(CSColor.BLACK, 20, 300, 210, 280),
//	new Platform(CSColor.BLACK, 530, 300, 200, 280),
//	new Platform(CSColor.RED, 230, 300, 300, 280)
//});
//level21.setGoals(new GoalPoint[] {
//	new GoalPoint(710, 280)
//});
//level21.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, 280)
//});
//level21.getSpawnPoints()[0].setIsActive(true);
//level21.getSpawnPoints()[0].setIsObtainable(false);
//level21.setObstacles(new Obstacle[] {
//});
//level21.setItems(new Item[] {
//	new Mirror(110, 260),
//	new ColorChanger(CSColor.GREEN, 620, 260)
//});
//level21.setText(new String[] {
//});
//levels[20] = level21;
//
////  Create level 22 
//Level level22 = new Level(levelManager, CSColor.GREEN);
//level22.setPlatforms(new Platform[] {
//    new Platform(CSColor.BLACK, 10, 10, 10, 580),
//    new Platform(CSColor.BLACK, 20, 580, 720, 10),
//    new Platform(CSColor.BLACK, 20, 10, 720, 10),
//    new Platform(CSColor.BLACK, 730, 20, 10, 560),
//    new Platform(CSColor.BLACK, 180, 160, 120, 410),
//    new Platform(CSColor.BLACK, 180, 570, 120, 10),
//    new Platform(CSColor.BLACK, 560, 160, 50, 420)
//});
//level22.setGoals(new GoalPoint[] {
//    new GoalPoint(710, 560)
//});
//level22.setSpawnPoints(new Point[] {
//    new SpawnPoint(20, 560)
//});
//level22.getSpawnPoints()[0].setIsActive(true);
//level22.getSpawnPoints()[0].setIsObtainable(false);
//level22.setObstacles(new Obstacle[] {
//    new Lava(300, 160, 260, 420)
//});
//level22.setItems(new Item[] {
//    new SuperJump(110, 540),
//    new SuperJump(110, 300),
//    new SuperJump(260, 90),
//    new SuperJump(410, 100),
//    new SuperJump(560, 100)
//});
//
//level22.setText(new String[] {
//});
//levels[21] = level22;

//// ***   Create level 10   ***
//Level level10 = new Level(levelManager, CSColor.BLUE);
//level10.setPlatforms(new Platform[] {
//	// The walls
//	new Platform(10, 10, Game.WIDTH - 20, 10),
//	new Platform(10, 10, 10, Game.HEIGHT - 20),
//	new Platform(Game.WIDTH - 20, 10, 10, Game.HEIGHT - 20),
//	new Platform(10, Game.HEIGHT - 20, Game.WIDTH - 20, 10),
//	new MovingPlatform(CSColor.BLUE, 20, 10, 20, 580, (Game.WIDTH - 40) / 3, 10),
//	new MovingPlatform(CSColor.GREEN, 257, 580, 257, 10, (Game.WIDTH - 40) / 3, 10),
//	new MovingPlatform(CSColor.RED, 257 + 257 - 21, 10, 257 + 257 - 21, 580, (Game.WIDTH - 40) / 3 + 1, 10)
//});
//level10.setObstacles(new Obstacle[] {
//	new Lava(20, 20, (Game.WIDTH - 40) / 2, 400),
//	new Water((Game.WIDTH - 40) / 2 + 20, 20, (Game.WIDTH - 40) / 2, 400)
//});
//level10.setGoals(new GoalPoint[] {
//});
//level10.setSpawnPoints(new Point[] {
//	new SpawnPoint(20, Game.HEIGHT - 40)
//	
//});
//level10.getSpawnPoints()[0].setIsActive(true);
//level10.setItems(new Item[] {
//	new ColorChanger(CSColor.RED, 20, Game.HEIGHT - 50),
//	new ColorChanger(CSColor.GREEN, 20, Game.HEIGHT - 50),
//	new ColorChanger(CSColor.BLUE, 20, Game.HEIGHT - 50),
//	new HealthPack(20, Game.HEIGHT - 50)
//});
//for (Item item : level10.getItems()) {
//	item.setRefresh(true);
//}
//levels[9] = level10;
