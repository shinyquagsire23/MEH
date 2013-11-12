package PokemonClasses;

import org.zzl.minegaming.GBAUtils.GBARom;




public class SpritesExitManager {
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
}
