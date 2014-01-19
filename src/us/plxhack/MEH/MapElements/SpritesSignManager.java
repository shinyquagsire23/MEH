package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;




public class SpritesSignManager implements ISaveable {
	public SpritesSigns[] mapSigns;
    public int IsPresent(int x, int y){
    	int i=0;
    	for(i=0;i<mapSigns.length;i++){
    		if(mapSigns[i].bX==x && mapSigns[i].bY==y){
    			return i;
    		}
    	}
    	
    	return -1;
    	
    }
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
