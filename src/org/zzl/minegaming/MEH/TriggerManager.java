package org.zzl.minegaming.MEH;

import org.zzl.minegaming.GBAUtils.GBARom;

public class TriggerManager {
Triggers[] mapTriggers;
	void LoadTriggers(GBARom rom, int count){
		
		 mapTriggers=new Triggers[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapTriggers[i]= new Triggers(rom);
			
		}
	}
	TriggerManager(GBARom rom, int count){
		LoadTriggers(rom, count);
		
	}
	TriggerManager(GBARom rom, int offset, int count){
		rom.Seek(offset);
		LoadTriggers(rom, count);
	}
}
