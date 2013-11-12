package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;




public class SpritesExitManager {
	 SpritesExit[] mapExits;
	
	SpritesExitManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapExits=new  SpritesExit[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapExits[i]= new  SpritesExit(rom);
			
		}
	}
}
