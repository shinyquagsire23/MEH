package org.zzl.minegaming.MEH.MapElements;

import java.awt.Image;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;
import org.zzl.minegaming.MEH.Map;




public class OverworldSpriteManager implements ISaveable {
	
	
	
    public static Image[] imgSprites;
    public int[] indexMatch;
	public void LoadSprites(GBARom rom, int offset){
		int i=0;
		imgSprites= new Image[255];
		
		for(i=0;i<255;i++){
			
			OverworldSprites t=new OverworldSprites(rom, offset + (i*36));
			imgSprites[i]= t.imgBuffer;
		}
		
	}
	
    public  OverworldSpriteManager(GBARom rom, int offset)
	{
    	
		
		
		int i=0;
		
			try{
			 LoadSprites(rom, offset);
			}
			catch(Exception e){
			 System.out.println(e.getMessage());
			}

	
	
		
		
	}

	public void save()
	{
		//for(SpritesNPC n : mapNPCs)
			//n.save();
	}
}
