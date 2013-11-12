package org.zzl.minegaming.MEH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.GBAImageType;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.Palette;

import mapElements.SpriteDataClass;

public class SpriteLoader {

	SpriteDataClass[] romSprites;
	
	
	SpriteLoader(GBARom rom){
		int i=0;
		//Load sprite pals
		SpriteDataClass.myPal = new Palette[16];
		        
		rom.Seek((int)DataStore.SpriteColors);
		
		for(i=0;i<16;i++){
			SpriteDataClass.myPal[i] = new Palette(GBAImageType.c16, rom.readBytes(32));
		}
		long BaseOffset=DataStore.SpriteBase;
		//Load Sprite structures
		romSprites= new SpriteDataClass[255];//If we can find an actual value..
		rom.Seek((int) DataStore.SpriteBase);
		for(i=0;i<10;i++){
			romSprites[i]=new SpriteDataClass(rom);
			
		}
		//Read Sprite GFX
		for(i=0;i<10;i++){
			romSprites[i].ReadGraphics(rom);
		}
			
			
			
			
		
		
		
	}
}
