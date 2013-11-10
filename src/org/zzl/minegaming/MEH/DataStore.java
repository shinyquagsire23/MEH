package org.zzl.minegaming.MEH;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ini4j.Ini;
import ini4j.spi.IniBuilder;


public class DataStore {
	
	//Everything we parse from the Ini
	private  Ini iP;
	private static Boolean passedTraits;
	//private Parser p;//For when we have YAML reading as well
	long ReadNumberEntry(String Section, String key){
	
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
	void ReadData(String ROMHeader){
	    //Read all the entries.
		Inherit = iP.get(ROMHeader, "Inherit");
		if(passedTraits = false && Inherit!="")
		{
		    //Genes passed, let's snip the traits. 
		    passedTraits=true;
			ReadData(ROMHeader);//Grab inherited values
		    
		}
		Name=iP.get(ROMHeader, "Name");
		Language = ReadNumberEntry(ROMHeader, "Language" ); 
		Cries= ReadNumberEntry(ROMHeader, "Cries" );
		MapHeaders= ReadNumberEntry(ROMHeader, "MapHeaders" );  
		Maps= ReadNumberEntry(ROMHeader, "Maps" ); 
		MapLabels= ReadNumberEntry(ROMHeader, "MapLabels" ); 
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
		//Name=ip.getString(ROMHeader, "Name");
	
		
		
		
	}
	
	
	public DataStore(String FilePath, String ROMHeader){
		try {
			iP=new Ini(new File(FilePath));
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
	
}
