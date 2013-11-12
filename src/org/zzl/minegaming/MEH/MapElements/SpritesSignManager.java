package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;




public class SpritesSignManager implements ISaveable {
	public SpritesSigns[] mapSigns;

	public SpritesSignManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapSigns=new SpritesSigns[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapSigns[i]= new SpritesSigns(rom);

		}
	}

	public void save()
	{
		for(SpritesSigns s : mapSigns)
			s.save();
	}
}
