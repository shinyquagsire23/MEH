package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;




public class SpritesExitManager implements ISaveable {
	public SpritesExit[] mapExits;
	
    public SpritesExitManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapExits=new  SpritesExit[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapExits[i]= new  SpritesExit(rom);
			
		}
	}
    
    public void save()
	{
		for(SpritesExit e : mapExits)
			e.save();
	}
}
