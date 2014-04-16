package us.plxhack.MEH.IO.Render;

import org.zzl.minegaming.GBAUtils.GBAImage;
import org.zzl.minegaming.GBAUtils.GBAImageType;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.Palette;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TilemapBuffer {
	public GBAImageType myType;
    public  Graphics gcBuff;
	public  Image imgBuffer;
	public GBAImage rawImage;
	private BufferedImage bi;
	private byte[] dcmpTilemap;
	private BufferedImage[] mybi;
	public boolean bLoaded;
	public int SelectedMap;
	private boolean RefreshMap;
	private BufferedImage renderedMap;
	public Palette p;//8bpp colors
	public Palette[] myPal;//4bpp colors
	public int[] dcmpGFX;
	public GBARom rom;
	public int mapnum;

	
	public TilemapBuffer(GBAImageType type, Palette[] colors, byte[] Tilemap, int[] GFX ){
		RefreshMap=true;
		  Load4BPP(GBAImageType.c16, colors, Tilemap, GFX);
		
		
	}
	public TilemapBuffer(GBAImageType type, Palette colors, byte[] Tilemap, int[] GFX){
		 Load8BPP(GBAImageType.c256, colors, Tilemap, GFX);
		
		
	}
	public BufferedImage GetRenderedMap()
	{
	  if(RefreshMap){
		  DrawMap();
		  RefreshMap=false;
	  }
	 return renderedMap;
	}
	public BufferedImage GetImage(int Pal){
		return mybi[Pal];
	}
	public BufferedImage GetImage(){
		
		
		return bi;
		
	  //DOES NOT SET
	}
	

	public void SetTile(int x, int y, int map){
		SelectedMap=map;
		SetTile(x,y);
	}
	public void SetTile(int x, int y){
		RefreshMap=true;
		
		
	}
	
	private void Draw4BPP()
	{
		int i=0;
		int len=(dcmpTilemap.length);//We're 8bpp now just trying to get the regular tiles to display
									  i=0;
		int tile_x=0;
        int tile_y=0;
        //This is gonna be dumb...
        i=0;
        int[] tiles=new int[dcmpTilemap.length/2];
        byte[] special=new byte[dcmpTilemap.length/2];
        int counter=0;
        for(i=0;i<len/2;i++){
        	
        	tiles[i]=(dcmpTilemap[counter] & 0xFF) + ((dcmpTilemap[counter+1] & 0xFF) << 8);
        	special[i]=dcmpTilemap[counter+1];
        	counter+=2;
        }
        for(i=0;i<tiles.length;i++){
             tile_x=(i%30);
             tile_y=(i/30);
        	 gcBuff.setColor(Color.red);
	            gcBuff.drawRect(tile_x*8,tile_y*8, 8, 8);
        	try{
        	int posInMap=0x0;
        	int val=tiles[(byte)posInMap+i]+(special[(byte)posInMap+i] << 8);
        	int curtile=tiles[posInMap+i] & 0x3FF;
        	int pal=(special[(byte)posInMap+i]&0xF0) >> 4;
        	boolean hf=(special[(byte)posInMap+i]&0x4)==4;
        	boolean hv= (special[(byte)posInMap+i]&0x8)==8;
        	
        	System.out.println(String.format("%05x", i*2    ) + " " +
        			String.format("%04x",tile_x) + " " + 
        			String.format("%04x",tile_y) + " " +  
        			String.format("%04x",val   ) + " " + 
        			String.format("%04x",(pal)) + " " +
        			String.format("%04x",(curtile)) + " " + 
        			          Boolean.toString(hf) +" " +Boolean.toString(hv) );
        				       
        					 
        	  gcBuff.drawImage(get4BPPTile( curtile, pal , hf, hv),
        			  tile_x*8,tile_y*8,null);
        	}catch(Exception e){}
        }
      
        
        bLoaded=true;     
		
	}
	
	public void Draw8BPP(){
		        int tile_x=0;
                int tile_y=0;
                for(tile_y =0; tile_y < 32; tile_y++)
                {
	           			
	                for(tile_x = 0; tile_x <32; tile_x++)
					{
		         
						
			                
		                	
		                	int srcx=(tile_x)*8;
			                int srcy=(tile_y)*8;
			                
			                int kX=tile_x;
			                int kY=(tile_y) * 64;
			                int kZ=0x000;
			                gcBuff.drawImage(get8BPPTile(dcmpTilemap[kX + kY +kZ ] & 0xFF),
				    						srcx,
				    						srcy,null);
					}
				}
                bLoaded=true; 
		
		
	}
    public void DrawMap(){
      switch(myType){
      case c16:
          Draw4BPP();
    	  
    	   break;
      
     
      case c256:
        
    	  Draw8BPP();
    	   break;
      
      }
    	
    }
    public void RenderBufferGFX()
    {
    	
    	 switch(myType){
         case c16:
            int i=0;
        	
 	 		for(i=0;i<myPal.length;i++){
 	 			mybi[i] = (new GBAImage(dcmpGFX,myPal[i],new Point(256,512))).getBufferedImageFromPal(myPal[i],false);
 	 		}
       	  
       	   break;
         
        
         case c256:
           
     		rawImage = new GBAImage(dcmpGFX,p,new Point(512,512));//pntSz);
    		
    		bi = rawImage.getBufferedImage();
       	   break;
         
         }
    }

    void Load4BPP( GBAImageType type, Palette[] colors, byte[] Tilemap, int[] GFX  ){
    	dcmpGFX=GFX;
    	myType=type;
    	myPal=colors;
    	dcmpTilemap= Tilemap;
  		mybi = new BufferedImage[6];
    	rawImage = new GBAImage(dcmpGFX,myPal[0],new Point(512,512));//pntSz);	
    
        int i=0;
        RenderBufferGFX();
        initGCBuff();
    	
    }
    
    
    void Load8BPP( GBAImageType type, Palette colors, byte[] Tilemap, int[] GFX  ){
    	dcmpGFX=GFX;
    	myType=type;
    	p=colors;
    	dcmpTilemap= Tilemap;
    	rawImage = new GBAImage(dcmpGFX,p,new Point(512,512));//pntSz);	
  		bi = rawImage.getBufferedImage();
        RenderBufferGFX();
        initGCBuff();
  		
    }
    
    
    
    
    private void initGCBuff(){
		imgBuffer = new BufferedImage(1024,1024,BufferedImage.TYPE_INT_ARGB);
		
		gcBuff=imgBuffer.getGraphics();
    }
    
   
    	
    
    
    public int GetTile(int x, int y, int map){
    
		SelectedMap=map;
		 return GetTile(x,y);
	}
	public int GetTile(int x, int y){
		
		
		return 0;//For now D;
	}
	//Fit the following into GBAUtils eventually....
    public BufferedImage get8BPPTile(int tileNum)
		{
			
			
			int x = ((tileNum) % (64)) * 8;
			int y = ((tileNum) / (64)) * 8;
			BufferedImage toSend = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
			try
			{
				toSend =  GetImage().getSubimage(x, y, 8, 8);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				//System.out.println("Attempted to read 8x8 at " + x + ", " + y);
			}
		
			
			return toSend;
		}
	public BufferedImage get4BPPTile(long tile)
	{
		 return get4BPPTile((int)tile&0x3FF, (int)((tile&0xF000)>>12), (tile&0x400)==0x400, (tile&0x800)==0x800);
	}
	public BufferedImage get4BPPTile(long tileNum, long palette, boolean xFlip, boolean yFlip)
	{
		
			
		 int x = (int) (((tileNum & 0x3FF) % (32)) * 8);
		 int y = (int) (((tileNum & 0x3FF) / (32)) * 8);
			BufferedImage toSend = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
			try
			{
				toSend =  GetImage((int) palette).getSubimage(x, y, 8, 8);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			//	System.out.println("Attempted to read 8x8 at " + x + ", " + y);
			}
	

			
			return toSend;
	 }

}
