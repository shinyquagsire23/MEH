package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;




public class SpritesNPCManager {
	SpritesNPC[] mapNPCs;
	
	SpritesNPCManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapNPCs=new SpritesNPC[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapNPCs[i]= new SpritesNPC(rom);
			
		}
	}
}
