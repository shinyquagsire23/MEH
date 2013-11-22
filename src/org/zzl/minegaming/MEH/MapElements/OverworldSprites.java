package org.zzl.minegaming.MEH.MapElements;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.GBAImageType;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.Palette;
import org.zzl.minegaming.MEH.DataStore;


public class OverworldSprites {
	 //Rom structure 
	/*
	public long lIndex; //Primary Key in sprite database?
	public  long lSlot;
	public  long iOffTop; //Hotspot//s top area. Normally 16.
	public  int iOffBot; //Hotspot//s bottom area. Normally 32.
	public  int iPal; //Palette to use. Probably contains more than that, only last digit actually matters.
	public  int filler;
	public  long u1; //An unknown pointer.*/
	public  int StarterWord;
	public  byte iPal;
	public  byte sprVald1;
	public  byte sprVald2;
	public  byte sprVald3;
	public  int FrameSize;
	public int width;
	public int height;
	public int oam1;
	public int oam2;
	public  long ptr2anim;
	public  long ptrSize; //Pointer to unknown data. Determines sprite size.
	public  long ptrAnim; //Pointer to unknown data. Determines sprite mobility: just one sprite, can only turn (gym leaders) or fully mobile.
	public  long ptrGraphic; //Pointer to pointer to graphics <- not a typo ;)
	public  long LoadCode; //Another unknown pointer.
	Point pntSz;
	//Class vars
	public  long trueGraphicsPointer;
	public  Graphics gcBuff;
	public  Image imgBuffer;
	public  GBAImage rawImage;
	public static Palette[] myPal;
	private BufferedImage[] bi;
	
	public int mSpriteSize;
	 
	/*			if(renderTileset)
				{
					for(int i = 0; i < 13; i++)
					{
						g.drawImage(globalTiles.getTileSet(i),i*128,0,this);
					
					}
				}
	*/
	
	void GrabPal(GBARom rom)
	{
		  OverworldSprites.myPal = new Palette[16];
		  
			
			for(int i = 0; i < 16; i++)
			{
				int ptr = rom.getPointerAsInt((int)DataStore.SpriteColors + (i * 8));
				OverworldSprites.myPal[rom.readByte((int)DataStore.SpriteColors + (i * 8) + 4)] = new Palette(GBAImageType.c16, BitConverter.GrabBytes(rom.getData(), ptr,32));
			}
		  
	
	}
    void DrawSmall(){
    	gcBuff.drawImage(getTile(0,iPal&0xf),0,0,null);
		gcBuff.drawImage(getTile(1,iPal&0xf),8,0,null);
		gcBuff.drawImage(getTile(2,iPal&0xf),0,8,null);
		gcBuff.drawImage(getTile(3,iPal&0xf),8,8,null);
    }
    void DrawMedium(){
    	gcBuff.drawImage(getTile(0,iPal&0xf),0,0,null);
		gcBuff.drawImage(getTile(1,iPal&0xf),8,0,null);
		gcBuff.drawImage(getTile(2,iPal&0xf),0,8,null);
		gcBuff.drawImage(getTile(3,iPal&0xf),8,8,null);
		gcBuff.drawImage(getTile(4,iPal&0xf),0,16,null);
		gcBuff.drawImage(getTile(5,iPal&0xf),8,16,null);
		gcBuff.drawImage(getTile(6,iPal&0xf),0,24,null);
		gcBuff.drawImage(getTile(7,iPal&0xf),8,24,null);
    }
    //AutoX and AutoY are only for drawlarge 
   
    void DrawLarge(){
     try{
     gcBuff.drawImage(getTile(0,iPal&0xf), 0, 0,null);
	 gcBuff.drawImage(getTile(1,iPal&0xf), 8, 0,null);
	 gcBuff.drawImage(getTile(2,iPal&0xf), 16, 0,null);
	 gcBuff.drawImage(getTile(3,iPal&0xf), 24, 0,null);
	 gcBuff.drawImage(getTile(4,iPal&0xf), 0, 8,null);
	 gcBuff.drawImage(getTile(5,iPal&0xf), 8, 8,null);
	 gcBuff.drawImage(getTile(6,iPal&0xf), 16, 8,null);
	 gcBuff.drawImage(getTile(7,iPal&0xf), 24, 8,null);
	 gcBuff.drawImage(getTile(8,iPal&0xf), 0, 16,null);
	 gcBuff.drawImage(getTile(9,iPal&0xf), 8, 16,null);
	 gcBuff.drawImage(getTile(10,iPal&0xf), 16, 16,null);
	 gcBuff.drawImage(getTile(11,iPal&0xf), 24, 16,null);
	 gcBuff.drawImage(getTile(12,iPal&0xf), 0, 24,null);
	 gcBuff.drawImage(getTile(13,iPal&0xf), 8, 24,null);
	 gcBuff.drawImage(getTile(14,iPal&0xf), 16, 24,null);
	 gcBuff.drawImage(getTile(15,iPal&0xf), 24, 24,null);
     }catch(Exception e){
	 gcBuff.drawRect(0, 0, 24, 24);   
     }
   
    }
	void PaintMeLikeYourWomenInMagazines(){
		
			
			
			
			imgBuffer = new BufferedImage(128,128,BufferedImage.TYPE_INT_ARGB);
			
			gcBuff=imgBuffer.getGraphics();
			switch(mSpriteSize){
			case 0:
				DrawSmall();
				break;
			case 1:
				DrawMedium();
				break;
			case 2:
				DrawLarge();
				break;
			}
			
		
			
			//}
				
			
	}

	void MakeMeReal(GBARom rom){
		
		
		
		int sz=0;
		if(ptrSize==DataStore.SpriteSmallSet){
			sz=(4*32)/2;
			mSpriteSize=0;
			
			
		}else
		if(ptrSize==DataStore.SpriteNormalSet){
			sz=(8*32)/2;
			mSpriteSize=1;
			
			
		}else
		if(ptrSize==DataStore.SpriteLargeSet){
			sz=(16*32)/2;
			mSpriteSize=2;
			
		}else{
			sz=(32*32)/2;
			mSpriteSize=1;
				
				
		}
	
		int i=0;
		rom.Seek((int) trueGraphicsPointer);
		byte[] dBuff=rom.readBytes(sz*2);
		int DataBuffer[];
		
			DataBuffer=BitConverter.ToInts(dBuff); //Read decompressed data
	    
	
		rawImage = new GBAImage(DataBuffer,myPal[iPal&0xF],new Point(128,128));//pntSz);	
		
		bi = new BufferedImage[16];
		for(i=0;i<16;i++){
			this.bi[i] = rawImage.getBufferedImageFromPal(OverworldSprites.myPal[i]);
		}
		 PaintMeLikeYourWomenInMagazines();//Honestly not sure what I'm totally doing here. 
	}
	void Load(GBARom rom){
	 
		 StarterWord = rom.readWord();
		 iPal=rom.readByte();
		 sprVald1=rom.readByte();
		 sprVald2=rom.readByte();
		 sprVald3=rom.readByte();
		 FrameSize= rom.readWord();
		 width= rom.readWord();
		 height= rom.readWord();
		 oam1= rom.readWord();
		 oam2= rom.readWord();
		 ptr2anim= rom.getPointer();
		 ptrSize= rom.getPointer(); //Pointer to unknown data. Determines sprite size.
		 ptrAnim= rom.getPointer(); //Pointer to unknown data. Determines sprite mobility: just one sprite, can only turn (gym leaders) or fully mobile.
		 ptrGraphic= rom.getPointer(); //Pointer to pointer to graphics <- not a typo ;)
		 LoadCode= rom.getPointer();
	 trueGraphicsPointer=rom.getPointer((int) ptrGraphic);//Grab the real one
	 if(OverworldSprites.myPal==null){
		 GrabPal(rom);
	 }
	 MakeMeReal(rom);
	 //if pal size is 0 then we need to grab it
	}
	   public BufferedImage getTile(int tileNum, int palette)
		{
			
			
			int x = ((tileNum) % (8)) * 8;
			int y = ((tileNum) / (8)) * 8;
			BufferedImage toSend = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
			try
			{
				toSend =  bi[palette].getSubimage(x, y, 8, 8);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				//System.out.println("Attempted to read 8x8 at " + x + ", " + y);
			}
		
			
			return toSend;
		}
	    
	    
	    
	    
	public OverworldSprites(GBARom rom){
	   Load(rom);	
	}
	public OverworldSprites(GBARom rom, int offset){
	   rom.Seek(offset);
	   Load(rom);
	}
	
}
