package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.DataStore;
import org.zzl.minegaming.GBAUtils.GBARom;
import org.zzl.minegaming.GBAUtils.ISaveable;

public class WildPokemonData implements ISaveable, Cloneable
{
	private WildDataType type;
	private GBARom rom;
	private long pData;
	public byte bRatio;
	public byte bDNEnabled;
	public long pPokemonData;
	public WildPokemon[][] aWildPokemon;
	public long[] aDNPokemon;
	private static final int[] numPokemon = new int[] { 12, 5, 5, 10 };

	public WildPokemonData(GBARom rom, WildDataType t)
	{
		this.rom = rom;
		type = t;
		if(rom.internalOffset > 0x1FFFFFF)
		{
			return;
		}
		
		try
		{
			bRatio = rom.readByte();
			bDNEnabled = rom.readByte();
			rom.internalOffset += 0x2;
			pPokemonData = rom.getPointer();
			aWildPokemon = new WildPokemon[(bDNEnabled > 0 ? 4 : 1)][numPokemon[type.ordinal()]];
			aDNPokemon = new long[4];
			
				for (int j = 0; j < 4; j++)
				{
					if (bDNEnabled == 0x1)
						aDNPokemon[j] = rom.getPointer((int) (pPokemonData) + (j * 4), true);
					else
						aDNPokemon[j] = -1;
				}

			
			for(int j = 0; j < (bDNEnabled > 0 ? 4 : 1); j++)
			{
				if(bDNEnabled == 0)
					rom.Seek((int) pPokemonData);
				else
					rom.Seek((int)aDNPokemon[j] & 0x1FFFFFF);
				
				for (int i = 0; i < numPokemon[type.ordinal()]; i++)
				{
					aWildPokemon[j][i] = new WildPokemon(rom);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public WildPokemonData(GBARom rom, WildDataType t, byte ratio)
	{
		this.rom = rom;
		type = t;
		bRatio = ratio;
		pPokemonData = -1;
		bDNEnabled = 0;
		aWildPokemon = new WildPokemon[(bDNEnabled > 0 ? 4 : 1)][numPokemon[type.ordinal()]];

		rom.Seek((int) pPokemonData);
		for (int i = 0; i < numPokemon[type.ordinal()]; i++)
		{
			aWildPokemon[0][i] = new WildPokemon(rom, 1, 1, 0);
		}
	}
	
	public WildPokemonData(GBARom rom, WildDataType t, boolean enableDN)
	{
		this.rom = rom;
		type = t;
		bRatio = 0x21;
		bDNEnabled = (byte)(enableDN ? 0x1 : 0x0);
		pPokemonData = -1;
		aWildPokemon = new WildPokemon[(bDNEnabled > 0 ? 4 : 1)][numPokemon[type.ordinal()]];

		rom.Seek((int) pPokemonData);
		for(int j = 0; j < (bDNEnabled > 0 ? 4 : 1); j++)
		{	
			for (int i = 0; i < numPokemon[type.ordinal()]; i++)
			{
				aWildPokemon[j][i] = new WildPokemon(rom,1,1,0);
			}
		}
	}
	
	public WildPokemonData(WildPokemonData d)
	{
		this.rom = d.rom;
		try
		{
			this.aDNPokemon = d.aDNPokemon.clone();
		}
		catch(Exception e){return;}
		WildPokemon[][] pokeTransfer = new WildPokemon[d.aWildPokemon.length][numPokemon[d.type.ordinal()]];
		
		for(int j = 0; j < d.aWildPokemon.length; j++)
		{
			for(int i = 0; i < numPokemon[d.type.ordinal()]; i++)
			{
				pokeTransfer[j][i] = new WildPokemon(d.rom,d.aWildPokemon[j][i].bMinLV,d.aWildPokemon[j][i].bMaxLV,d.aWildPokemon[j][i].wNum);
			}
		}
		this.aWildPokemon = pokeTransfer.clone();
		
		this.bDNEnabled = d.bDNEnabled;
		this.bRatio = d.bRatio;
		this.pData = d.pData;
		this.pPokemonData = d.pPokemonData;
		this.type = d.type;
	}

	public void convertToDN()
	{
		bDNEnabled = 1;
		WildPokemon[][] pokeTransfer = new WildPokemon[4][numPokemon[type.ordinal()]];
		
		for(int j = 0; j < 4; j++)
		{
			for(int i = 0; i < numPokemon[type.ordinal()]; i++)
			{
				pokeTransfer[j][i] = new WildPokemon(rom,aWildPokemon[0][i].bMinLV,aWildPokemon[0][i].bMaxLV,aWildPokemon[0][i].wNum);
			}
		}
		
		aWildPokemon = pokeTransfer.clone();
	}

	public static int getSize()
	{
		return 8;
	}

	public int getWildDataSize()
	{
		return numPokemon[type.ordinal()] * WildPokemon.getSize();
	}

	public WildDataType getType()
	{
		return type;
	}

	public void save()
	{
		rom.writeByte(bRatio);
		rom.writeByte(bDNEnabled);
		rom.internalOffset += 0x2;

		if (pPokemonData == -1)
		{
			pPokemonData = rom.findFreespace((bDNEnabled == 1 ? 4*4 : getWildDataSize()), (int)DataStore.FreespaceStart);
			rom.floodBytes((int)pPokemonData, (byte)0, (bDNEnabled == 1 ? 4*4 : getWildDataSize())); //Prevent them from taking the same freespace
		}
		for(int i = 0; i < 4; i++)
			if(aDNPokemon[i] == -1 && bDNEnabled == 0x1)
			{
				aDNPokemon[i] = rom.findFreespace(getWildDataSize(), (int)DataStore.FreespaceStart);
				rom.floodBytes((int)aDNPokemon[i], (byte)0, getWildDataSize()); //Prevent them from taking the same freespace
			}

		rom.writePointer((int) pPokemonData);
		
		if(bDNEnabled == 1)
		{
			rom.Seek((int)pPokemonData);
			rom.writePointer((int)aDNPokemon[0]);
			rom.writePointer((int)aDNPokemon[1]);
			rom.writePointer((int)aDNPokemon[2]);
			rom.writePointer((int)aDNPokemon[3]);
		}
		
		for(int j = 0; j < (bDNEnabled > 0 ? 4 : 1); j++)
		{
			if(bDNEnabled == 0)
				rom.Seek((int) pPokemonData);
			else
				rom.Seek((int) aDNPokemon[j]);
			
			for (int i = 0; i < numPokemon[type.ordinal()]; i++)
			{
				try
				{
					aWildPokemon[j][i].save();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public Object clone() throws CloneNotSupportedException
	{
		return new WildPokemonData(this);
	}
}
