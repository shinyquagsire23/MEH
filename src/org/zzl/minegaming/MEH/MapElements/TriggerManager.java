package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class TriggerManager implements ISaveable {
	public Triggers[] mapTriggers;
    
	public int IsPresent(int x, int y){
    	int i=0;
    	for(i=0;i<mapTriggers.length;i++){
    		if(mapTriggers[i].bX==x && mapTriggers[i].bY==y){
    			return i;
    		}
    	}
    	
    	return -1;
    	
    }
	public void LoadTriggers(GBARom rom, int count){

		mapTriggers=new Triggers[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapTriggers[i]= new Triggers(rom);

		}
	}
	public TriggerManager(GBARom rom, int count){
		LoadTriggers(rom, count);

	}
	public TriggerManager(GBARom rom, int offset, int count){
		rom.Seek(offset);
		LoadTriggers(rom, count);
	}

	
	public void save()
	{
		for(Triggers t : mapTriggers)
			t.save();
	}
}
