package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;

public class TriggerManager {
	  public Triggers[] mapTriggers;
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
}
