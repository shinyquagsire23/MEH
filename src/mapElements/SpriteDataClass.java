package mapElements;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.Palette;
import org.zzl.minegaming.MEH.BlockRenderer;
import org.zzl.minegaming.MEH.DataStore;
import org.zzl.minegaming.MEH.MapEditorPanel;

public class SpriteDataClass {
	  public long lIndex; //Primary Key in sprite database?
	  public long lSlot;
	  public int iOffTop; //Hotspot//s top area. Normally 16.
	  public int iOffBot; //Hotspot//s bottom area. Normally 32.
	  public int iPal; //Palette to use. Probably contains more than that, only last digit actually matters.
	  public int filler;
	  public long u1; //An unknown pointer.
	  public long ptrSize; //Pointer to unknown data. Determines sprite size.
	  public long ptrAnim; //Pointer to unknown data. Determines sprite mobility: just one sprite, can only turn (gym leaders) or fully mobile.
	  public long ptrGraphic;//Pointer to pointer to graphics <- not a typo ;)
	  public long u2; //Another unknown pointer.
	  //Program related variables
	  public long trueptrGraphic;
	  public static Palette[] myPal;
	  public int[] rawData;
	  public GBAImage myImage;
	  public Graphics gcBuff;
	  public Image imgBuffer=null;
	  public BufferedImage bi;
	  void ReadHeader(GBARom rom){
	
		
		  
		  lIndex= BitConverter.ToInt32(rom.readBytes(4));
		  lSlot =BitConverter.ToInt32(rom.readBytes(4));
		  iOffTop=rom.readWord();
		  iOffBot=rom.readWord();
		  iPal=rom.readWord();
		  filler=rom.readWord();
		  u1=rom.getPointer();
		  ptrSize=rom.getPointer();
		  ptrAnim=rom.getPointer();
		  ptrGraphic=rom.getPointer();
		  try{
		  trueptrGraphic=rom.getPointer((int) ptrGraphic);
		  }
		  catch(Exception e){
			  
		  }
		  u2=rom.getPointer();
	  }
	  int len=0;
	  void Read(GBARom rom){
		  rom.Seek((int) trueptrGraphic);
		 rawData=new int[len*16];
		 int i=0;
		 for(i=0;i<len*16;i++){
		  rawData[i] = rom.readWord();
		 }
		
		  
	  }
	  void ReadSmall(GBARom rom){
		  len=4;
	  }
	  void ReadNormal(GBARom rom){
		  len=8;
	  }

	  void ReadLarge(GBARom rom){
		  len=16;
	  }
	  public void ReadGraphics(GBARom rom){
		  //Kawa thought this was hard, poor old chap
		  if(ptrSize == DataStore.SpriteSmallSet){
			  ReadSmall(rom);
		  }else
		  if(ptrSize == DataStore.SpriteNormalSet){
			  ReadNormal(rom);
		  }else
		  if(ptrSize == DataStore.SpriteLargeSet){
			  ReadLarge(rom);
		  }
		  Read(rom);
		  myImage = new GBAImage( rawData,myPal[2],new Point(32,32));	
		  DrawSprite();
	  }
	  public void DrawSprite(){
		  switch(len){
		  case 4: //2 tiles
			  
			  break;
		  case 8://4 tiles
			  break;
		  case 16://8 tiles
			  break;
		  
		  }
		  
		    imgBuffer = new BufferedImage((len/2)*8,(len/2)*8,BufferedImage.TYPE_INT_ARGB);
			
			gcBuff=imgBuffer.getGraphics();
			for(int i = 0; i < len; i++) 
			{
			   
				int x = (i % len) * 8;
				int y = (i / len) * 8;
                
			
				try{
				gcBuff.drawImage(myImage.getBufferedImageFromPal(myPal[0]).getSubimage(8, 0, 8, 8), x, y, (ImageObserver) this);
				}catch(Exception e){
					
				}
			}
	  }
	  public SpriteDataClass(GBARom rom, int offset){
		  rom.Seek(offset);
		  ReadHeader(rom);
		   
		 
	  }
	  public SpriteDataClass(GBARom rom){
		  ReadHeader(rom);
	  }
}
