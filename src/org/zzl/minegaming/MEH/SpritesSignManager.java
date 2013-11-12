package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;




public class SpritesSignManager {
	SpritesSigns[] mapSigns;
	
	SpritesSignManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapSigns=new SpritesSigns[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapSigns[i]= new SpritesSigns(rom);
			
		}
	}
}
