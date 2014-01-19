package us.plxhack.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.GBARom;

public class WildData
{
	public WildPokemonData[] aWildPokemon = new WildPokemonData[4];
	public WildDataHeader wildDataHeader;
	private GBARom rom;
	
	public WildData(GBARom rom, WildDataHeader h)
	{
		this.rom = rom;
		wildDataHeader = h;
		
		rom.Seek((int)h.pGrass);
		if(h.pGrass != 0)
			aWildPokemon[0] = new WildPokemonData(rom, WildDataType.GRASS);
		
		rom.Seek((int)h.pWater);
		if(h.pWater != 0)
			aWildPokemon[1] = new WildPokemonData(rom, WildDataType.WATER);
		
		rom.Seek((int)h.pTrees);
		if(h.pTrees != 0)
			aWildPokemon[2] = new WildPokemonData(rom, WildDataType.TREE);
		
		rom.Seek((int)h.pFishing);
		if(h.pFishing != 0)
			aWildPokemon[3] = new WildPokemonData(rom, WildDataType.FISHING);
	}
}
