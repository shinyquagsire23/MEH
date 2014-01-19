package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;




public class SpritesNPCManager implements ISaveable {
	public SpritesNPC[] mapNPCs;
	public int[] GetSpriteIndices(){
		int i=0;
		int indices[]=new int[mapNPCs.length];
    	for(i=0;i<mapNPCs.length;i++){
    		indices[i]=mapNPCs[i].bSpriteSet;
    	}
    	return indices;
	}
    public int IsPresent(int x, int y){
    	int i=0;
    	for(i=0;i<mapNPCs.length;i++){
    		if(mapNPCs[i].bX==x && mapNPCs[i].bY==y){
    			return i;
    		}
    	}
    	
    	return -1;
    	
    }
	public  SpritesNPCManager(GBARom rom, int offset, int count)
	{
		rom.Seek(offset);
		mapNPCs=new SpritesNPC[count];
		int i=0;
		for(i=0;i<count;i++)
		{
			mapNPCs[i]= new SpritesNPC(rom);

		}
	}

	
	public void save()
	{
		for(SpritesNPC n : mapNPCs)
			n.save();
	}
}
