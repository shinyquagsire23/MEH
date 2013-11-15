package org.zzl.minegaming.MEH.MapElements;

import java.awt.Image;

import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.MEH.DataStore;

public class OverworldSpritesManager {
   public OverworldSprites[] Sprites;
   
   public Image GetImage(int index){
	   return Sprites[index].imgBuffer;
   }
   
   public OverworldSpritesManager(GBARom rom, SpritesNPC[] NPCs){
	   if(DataStore.mehSettingShowSprites == 0) return;//Don't load if not enabled.
	   Sprites=new OverworldSprites[NPCs.length];
	   int i=0; 
	   for(i=0;i<NPCs.length;i++){
		   try{
		   Sprites[i]=new OverworldSprites(rom,(int)DataStore.SpriteBase+(NPCs[i].bSpriteSet*36) );
		   }
		   catch(Exception e){
			  
		   }
	   }
	   
	
   }
}
