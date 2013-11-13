package org.zzl.minegaming.MEH.MapElements;

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
		aWildPokemon[0] = new WildPokemonData(rom, h.pGrass);
		aWildPokemon[1] = new WildPokemonData(rom, h.pWater);
		aWildPokemon[2] = new WildPokemonData(rom, h.pTrees);
		aWildPokemon[3] = new WildPokemonData(rom, h.pFishing);
	}
}
