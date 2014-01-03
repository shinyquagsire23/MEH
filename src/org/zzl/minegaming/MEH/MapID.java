package org.zzl.minegaming.MEH;

public class MapID
{
	private int bank, map;
	
	public MapID(int bank, int map)
	{
		this.bank = bank;
		this.map = map;
	}
	
	public int getBank()
	{
		return bank;
	}
	
	public int getMap()
	{
		return map;
	}

	@Override
	public boolean equals(Object b)
	{
		if(b == null)
			return false;
		if(!(b instanceof MapID))
			return false;
		
		MapID bI = (MapID)b;
		if(bI.bank == this.bank)
			if(bI.map == this.map)
				return true;
		
		return false;
	}
}
