/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor.selector;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;

import misha.editor.DrawUtil;
import misha.editor.level.LevelEditor;
import misha.editor.level.entity.EntityEditor;
import misha.editor.level.entity.item.ColorChangerEditor;
import misha.editor.level.entity.item.ColorMixerEditor;
import misha.editor.level.entity.item.DamagePackEditor;
import misha.editor.level.entity.item.HealthPackEditor;
import misha.editor.level.entity.item.MirrorEditor;
import misha.editor.level.entity.item.SuperJumpEditor;
import misha.editor.level.entity.item.TeleporterEditor;
import misha.game.level.entity.CSColor;
import misha.game.level.entity.item.ColorChanger;
import misha.game.level.entity.item.ColorMixer;
import misha.game.level.entity.item.DamagePack;
import misha.game.level.entity.item.HealthPack;
import misha.game.level.entity.item.Item;
import misha.game.level.entity.item.Mirror;
import misha.game.level.entity.item.SuperJump;
import misha.game.level.entity.item.Teleporter;

public class ItemSelector extends AbstractEntitySelector<Item> {
	
	private static final int COLOR_CHANGER = 1;
	private static final int COLOR_MIXER = 2;
	private static final int DAMAGE_PACK = 3;
	private static final int HEALTH_PACK = 4;
	private static final int MIRROR = 5;
	private static final int SUPER_JUMP = 6;
	private static final int TELEPORTER = 7;
	
	private static final Rectangle COLOR_CHANGER_BUTTON = new Rectangle(10, 660, 40, 40);
	private static final Rectangle COLOR_MIXER_BUTTON = new Rectangle(60, 660, 40, 40);
	private static final Rectangle DAMAGE_PACK_BUTTON = new Rectangle(110, 660, 40, 40);
	private static final Rectangle HEALTH_PACK_BUTTON = new Rectangle(160, 660, 40, 40);
	private static final Rectangle MIRROR_BUTTON = new Rectangle(210, 660, 40, 40);
	private static final Rectangle SUPER_JUMP_BUTTON = new Rectangle(260, 660, 40, 40);
	private static final Rectangle TELEPORTER_BUTTON = new Rectangle(310, 660, 40, 40);
	
	private static final Item COLOR_CHANGER_IMAGE = new ColorChanger(CSColor.RED, COLOR_CHANGER_BUTTON.x + 5, COLOR_CHANGER_BUTTON.y + 5);
	private static final Item COLOR_MIXER_IMAGE = new ColorMixer(CSColor.RED, COLOR_MIXER_BUTTON.x + 5, COLOR_MIXER_BUTTON.y + 5, true);
	private static final Item DAMAGE_PACK_IMAGE = new DamagePack(DAMAGE_PACK_BUTTON.x + 5, DAMAGE_PACK_BUTTON.y + 5);
	private static final Item HEALTH_PACK_IMAGE = new HealthPack(HEALTH_PACK_BUTTON.x + 5, HEALTH_PACK_BUTTON.y + 5);
	private static final Item MIRROR_IMAGE = new Mirror(MIRROR_BUTTON.x + 5, MIRROR_BUTTON.y + 5, CSColor.GRAY, false);
	private static final Item SUPER_JUMP_IMAGE = new SuperJump(SUPER_JUMP_BUTTON.x + 5, SUPER_JUMP_BUTTON.y + 5);
	private static final Item TELEPORTER_IMAGE = new Teleporter(TELEPORTER_BUTTON.x + 5, TELEPORTER_BUTTON.y + 5, -1, -1);
	
	@Override
	public EntityEditor<? extends Item> getEditor(LevelEditor levelEditor, Item[] items, Point point) {
		Item clickedItem = null;
		for (Item item : items) {
			if (item.getRect().contains(point)) {
				clickedItem = item;
			}
		}
		
		if (clickedItem == null) {
			if (COLOR_CHANGER_BUTTON.contains(point)) {
				highlightOption = COLOR_CHANGER;
				return new ColorChangerEditor(levelEditor);
			} else if (COLOR_MIXER_BUTTON.contains(point)) {
				highlightOption = COLOR_MIXER;
				return new ColorMixerEditor(levelEditor);
			} else if (DAMAGE_PACK_BUTTON.contains(point)) {
				highlightOption = DAMAGE_PACK;
				return new DamagePackEditor(levelEditor);
			} else if (HEALTH_PACK_BUTTON.contains(point)) {
				highlightOption = HEALTH_PACK;
				return new HealthPackEditor(levelEditor);
			} else if (MIRROR_BUTTON.contains(point)) {
				highlightOption = MIRROR;
				return new MirrorEditor(levelEditor);
			} else if (SUPER_JUMP_BUTTON.contains(point)) {
				highlightOption = SUPER_JUMP;
				return new SuperJumpEditor(levelEditor);
			} else if (TELEPORTER_BUTTON.contains(point)) {
				highlightOption = TELEPORTER;
				return new TeleporterEditor(levelEditor);
			}
		} else {
			if (clickedItem.getClass().equals(ColorChanger.class)) {
				highlightOption = COLOR_CHANGER;
				return new ColorChangerEditor(levelEditor, (ColorChanger) clickedItem);
			} else if (clickedItem.getClass().equals(ColorMixer.class)) {
				highlightOption = COLOR_MIXER;
				return new ColorMixerEditor(levelEditor, (ColorMixer) clickedItem);
			} else if (clickedItem.getClass().equals(DamagePack.class)) {
				highlightOption = DAMAGE_PACK;
				return new DamagePackEditor(levelEditor, (DamagePack) clickedItem);
			} else if (clickedItem.getClass().equals(HealthPack.class)) {
				highlightOption = HEALTH_PACK;
				return new HealthPackEditor(levelEditor, (HealthPack) clickedItem);
			} else if (clickedItem.getClass().equals(Mirror.class)) {
				highlightOption = MIRROR;
				return new MirrorEditor(levelEditor, (Mirror) clickedItem);
			} else if (clickedItem.getClass().equals(SuperJump.class)) {
				highlightOption = SUPER_JUMP;
				return new SuperJumpEditor(levelEditor, (SuperJump) clickedItem);
			} else if (clickedItem.getClass().equals(Teleporter.class)) {
				highlightOption = TELEPORTER;
				return new TeleporterEditor(levelEditor, (Teleporter) clickedItem);
			}
		}
		
		return null;
	}
	
	public void draw(Graphics2D g) {
		DrawUtil.drawButton(g, COLOR_CHANGER_BUTTON, highlightOption == COLOR_CHANGER);
		COLOR_CHANGER_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, COLOR_MIXER_BUTTON, highlightOption == COLOR_MIXER);
		COLOR_MIXER_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, DAMAGE_PACK_BUTTON, highlightOption == DAMAGE_PACK);
		DAMAGE_PACK_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, HEALTH_PACK_BUTTON, highlightOption == HEALTH_PACK);
		HEALTH_PACK_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, MIRROR_BUTTON, highlightOption == MIRROR);
		MIRROR_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, SUPER_JUMP_BUTTON, highlightOption == SUPER_JUMP);
		SUPER_JUMP_IMAGE.draw(g);
		
		DrawUtil.drawButton(g, TELEPORTER_BUTTON, highlightOption == TELEPORTER);
		TELEPORTER_IMAGE.draw(g);
	}
	
}
