package org.zzl.minegaming.MEH.MapElements;

import org.zzl.minegaming.GBAUtils.BitConverter;
import org.zzl.minegaming.GBAUtils.GBARom;

public class ConnectionData
{
	private GBARom rom;
	private MapHeader mapHeader;
	public long pNumConnections;
	public long pData;
	public Connection[] aConnections;
	
	public ConnectionData(GBARom rom, MapHeader mHeader)
	{
		this.rom = rom;
		mapHeader = mHeader;
		load();
	}
	
	public void load()
	{
		rom.Seek(BitConverter.shortenPointer(mapHeader.pConnect));
		pNumConnections = rom.getPointer(true);
		pData = rom.getPointer(true);
		aConnections = new Connection[BitConverter.shortenPointer(pNumConnections)];
		
		rom.Seek(BitConverter.shortenPointer(pData));
		for(int i = 0; i < pNumConnections; i++)
		{
			aConnections[i] = new Connection(rom,rom.internalOffset);
		}
	}
	
	public void save()
	{
		rom.Seek(BitConverter.shortenPointer(mapHeader.pConnect));
		rom.writePointer(pNumConnections);
		rom.writePointer(pData);
		
		rom.Seek(BitConverter.shortenPointer(pData));
		for(int i = 0; i < pNumConnections; i++)
		{
			aConnections[i].save();
		}
	}
}
