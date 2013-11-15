package org.zzl.minegaming.MEH;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.zzl.minegaming.GBAUtils.ROMManager;

import ini4j.Ini;
import ini4j.spi.IniBuilder;


public class DataStore {
	
	//Everything we parse from the Ini
	public static Ini iP;
	private static Boolean passedTraits;
	//private Parser p;//For when we have YAML reading as well
 public	long ReadNumberEntry(String Section, String key)
	{
	
		String nkey=iP.get(Section, key);
		
	    int CommentIndex=-1;
	    long ReturnValue=0;
	    String FinalString="";
	    try{
	    	CommentIndex=nkey.indexOf(";");
		    if(CommentIndex!=-1){
			
		    	nkey=nkey.substring(0, CommentIndex);//Get rid of the comment
		    }
		    FinalString=nkey;
		    if(nkey.indexOf("0x") !=-1 ){
		    FinalString=nkey.substring(2);
		    
		    }
		    	 ReturnValue=Long.parseLong(FinalString, 16);
		}catch(Exception e){
		 //There's a chance the key may not exist, let's come up with a way to handle this case
			//
			ReturnValue =  0;
		
		}
		return ReturnValue;
	}
	
	String ReadString(String Section, String key)
	{
		String nkey=iP.get(Section, key);
		
	    int CommentIndex=-1;
	    String ReturnValue="";
	    String FinalString="";
	    try{
	    	CommentIndex=nkey.indexOf(";");
		    if(CommentIndex!=-1){
			
		    	nkey=nkey.substring(0, CommentIndex);//Get rid of the comment
		    }
		    FinalString=nkey;
		    if(nkey.indexOf("0x") !=-1 ){
		    FinalString=nkey.substring(2);
		    
		    }
		    	 ReturnValue=FinalString;
		}catch(Exception e){
		 //There's a chance the key may not exist, let's come up with a way to handle this case
			//
			ReturnValue = "";
		
		}
		return ReturnValue;
	}
	
	
	void ReadData(String ROMHeader){
	    //Read all the entries.
		Inherit = iP.get(ROMHeader, "Inherit");
		if(passedTraits = false && Inherit!="")
		{
		    //Genes passed, let's snip the traits. 
		    passedTraits=true;
			ReadData(ROMHeader);//Grab inherited values
		    
		}
		EngineVersion = ReadNumberEntry(ROMHeader, "Engine");
		Name=iP.get(ROMHeader, "Name");
		Language = ReadNumberEntry(ROMHeader, "Language" ); 
		Cries= ReadNumberEntry(ROMHeader, "Cries" );
		MapHeaders= ReadNumberEntry(ROMHeader, "MapHeaders" );  
		Maps= ReadNumberEntry(ROMHeader, "Maps" ); 
		MapLabels= ReadNumberEntry(ROMHeader, "MapLabels" ); 
		MapLabels=ROMManager.getActiveROM().getPointerAsInt((int)MapLabels);
		MonsterNames= ReadNumberEntry(ROMHeader, "MonsterNames" );
		MonsterBaseStats= ReadNumberEntry(ROMHeader, "MonsterBaseStats" );
		MonsterDexData= ReadNumberEntry(ROMHeader, "MonsterDexData" );
		TrainerClasses= ReadNumberEntry(ROMHeader, "TrainerClasses" );
		TrainerData= ReadNumberEntry(ROMHeader, "TrainerData" );
		TrainerPics= ReadNumberEntry(ROMHeader, "TrainerPics" );
		TrainerPals= ReadNumberEntry(ROMHeader, "TrainerPals" );
		TrainerPicCount= ReadNumberEntry(ROMHeader, "TrainerPicCount" );
		TrainerBackPics= ReadNumberEntry(ROMHeader, "TrainerBackPics" );
		TrainerBackPals= ReadNumberEntry(ROMHeader, "TrainerBackPals" );
		TrainerBackPicCount= ReadNumberEntry(ROMHeader, "TrainerBackPicCount" );
		ItemNames= ReadNumberEntry(ROMHeader, "ItemNames" ); 
		MonsterPics= ReadNumberEntry(ROMHeader, "MonsterPics" );
		MonsterPals= ReadNumberEntry(ROMHeader, "MonsterPals" );
		MonsterShinyPals= ReadNumberEntry(ROMHeader, "MonsterShinyPals" );
		MonsterPicCount= ReadNumberEntry(ROMHeader, "MonsterPicCount" );
		MonsterBackPics= ReadNumberEntry(ROMHeader, "MonsterBackPics" );
		HomeLevel= ReadNumberEntry(ROMHeader, "HomeLevel" );     
		SpriteBase= ReadNumberEntry(ROMHeader, "SpriteBase" );     
		SpriteColors= ReadNumberEntry(ROMHeader, "SpriteColors" );  
		SpriteNormalSet= ReadNumberEntry(ROMHeader, "SpriteNormalSet" );
		SpriteSmallSet= ReadNumberEntry(ROMHeader, "SpriteSmallSet" );
		SpriteLargeSet= ReadNumberEntry(ROMHeader, "SpriteLargeSet" );
		WildPokemon= ReadNumberEntry(ROMHeader, "WildPokemon" );
		FontGFX= ReadNumberEntry(ROMHeader, "FontGFX" ); 
		FontWidths= ReadNumberEntry(ROMHeader, "FontWidths" );
		AttackNameList= ReadNumberEntry(ROMHeader, "AttackNameList" );
		AttackTable= ReadNumberEntry(ROMHeader, "AttackTable" );
		StartPosBoy= ReadNumberEntry(ROMHeader, "StartPosBoy" );
		StartPosGirl= ReadNumberEntry(ROMHeader, "StartPosGirl" );
		MainTSPalCount= (int) ReadNumberEntry(ROMHeader, "MainTSPalCount");
		MainTSSize= (int) ReadNumberEntry(ROMHeader, "MainTSSize");
		LocalTSSize= (int) ReadNumberEntry(ROMHeader, "LocalTSSize");
		MainTSBlocks= (int) ReadNumberEntry(ROMHeader, "MainTSBlocks");
		LocalTSBlocks= (int) ReadNumberEntry(ROMHeader, "LocalTSBlocks");
		MainTSHeight= (int) ReadNumberEntry(ROMHeader, "MainTSHeight");
		LocalTSHeight= (int) ReadNumberEntry(ROMHeader, "LocalTSHeight");
		NumBanks= (int) ReadNumberEntry(ROMHeader, "NumBanks");
		String[] mBS = ReadString(ROMHeader, "MapBankSize").split(",");
		MapBankSize = new int[NumBanks];

		for(int i = 0; i < mBS.length; i++)
		{
			MapBankSize[i] = Integer.parseInt(mBS[i]);
		}
		//Name=ip.getString(ROMHeader, "Name");
		//Read the data for MEH
		WorldMapGFX= (int) ReadNumberEntry(ROMHeader, "WorldMapGFXPointer");
		WorldMapPal= (int) ReadNumberEntry(ROMHeader, "WorldMapPalPointer");
		WorldMapTileMap= (int) ReadNumberEntry(ROMHeader, "WorldMapTileMap");
		WorldMapSlot =(int)  ReadNumberEntry(ROMHeader, "WorldMapSlot");
		mehSettingShowSprites = (int) ReadNumberEntry("MEH", "mehSettingShowSprites");
		mehUsePlugins = (int) ReadNumberEntry("MEH", "mehUsePlugins");
		mehSettingCallScriptEditor = ReadString("MEH","mehSettingCallScriptEditor"); 
		
	}
	
	public static void WriteNumberEntry(String Section, String key, int val)//Writes can happen at any time...currently.... move to mapsave function for later
	{
	   
		String nkey=iP.get(Section, key);
		
	    int CommentIndex=-1;
	    long ReturnValue=0;
	    String FinalString="";
	    try{
	    	FinalString=Integer.toString(val);
	    	 iP.put(Section, key, FinalString);
		}catch(Exception e){
		 //There's a chance the key may not exist, let's come up with a way to handle this case
			//
			ReturnValue =  0;
		
		}
		
	}
	public DataStore(String FilePath, String ROMHeader){
		try {
			iP=new Ini(new File(FilePath));
			bDataStoreInited=true;
			passedTraits=false; 
			Inherit="";
			ReadData(ROMHeader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static   long EngineVersion;
	public static   String Inherit;
	public static	String Name;
	public static	long Language; 
	public static	long Cries   ;
	public static	long MapHeaders;  
	public static	long Maps    ; 
	public static	long MapLabels; 
	public static	long MonsterNames;
	public static	long MonsterBaseStats;
	public static	long MonsterDexData;
	public static	long TrainerClasses;
	public static	long TrainerData;
	public static	long TrainerPics;
	public static	long TrainerPals;
	public static	long TrainerPicCount;
	public static	long TrainerBackPics;
	public static	long TrainerBackPals;
	public static	long TrainerBackPicCount;
	public static	long ItemNames; 
	public static	long MonsterPics;
	public static	long MonsterPals;
	public static	long MonsterShinyPals;
	public static	long MonsterPicCount;
	public static	long MonsterBackPics;
	public static	long HomeLevel;     
	public static	long SpriteBase;     
	public static	long SpriteColors;  
	public static	long SpriteNormalSet;
	public static	long SpriteSmallSet;
	public static	long SpriteLargeSet;
	public static	long WildPokemon;
	public static	long FontGFX ; 
	public static	long FontWidths;
	public static	long AttackNameList;
	public static	long AttackTable;
	public static	long StartPosBoy;
	public static	long StartPosGirl;
	public static   int	 MainTSPalCount;
	public static	int  MainTSSize;
	public static	int  LocalTSSize;
	public static	int  MainTSBlocks;
	public static	int  LocalTSBlocks;
	public static	int  MainTSHeight;
	public static	int  LocalTSHeight;
	public static 	int  NumBanks;
	public static	int[] MapBankSize;
	public static	int WorldMapGFX;
	public static	int		WorldMapPal;
	public static	int		WorldMapSlot;
	public static	int		WorldMapTileMap;
	
	
	
	public static   int mehUsePlugins;
	public static   int mehSettingShowSprites;
	public static   String mehSettingCallScriptEditor;
	
	
	public static   boolean bDataStoreInited;//Not stored in INI :p
	
}
