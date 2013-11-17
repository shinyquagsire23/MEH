package org.zzl.minegaming.MEH.MapElements;

import java.awt.Image;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.MEH.DataStore;

public class OverworldSpritesManager extends Thread implements Runnable
{
	public OverworldSprites[] Sprites = new OverworldSprites[256];
	private GBARom rom;

	public Image GetImage(int index)
	{
		if(Sprites[index] != null)
			return Sprites[index].imgBuffer;
		else
			return loadSprite(index).imgBuffer;
	}

	public OverworldSpritesManager(GBARom rom, SpritesNPC[] NPCs)
	{
		this.rom = rom;
	}
	
	public OverworldSprites loadSprite(int num)
	{
		int ptr = (int) rom.getPointer((int) DataStore.SpriteBase + (num * 4));
		Sprites[num] = new OverworldSprites(rom, ptr);
		return Sprites[num];
	}
	
	@Override
	public void run()
	{
		if (DataStore.mehSettingShowSprites == 0)
			return;// Don't load if not enabled.
		for (int i = 0; i < DataStore.NumSprites; i++)
		{
				loadSprite(i);
		}
	}
}
