package com.fravokados.techmobs.world.techdata;

import net.minecraft.nbt.NBTTagCompound;

/**
 * contains player techdata 
 * @author Nuklearwurst
 *
 */
public class TDPlayer {
	
	private static final String NBT_TECH_DATA = "player.techdata";
	private static final String NBT_SCOUTED_TECH_DATA = "player.scouted.techdata";
	
	public int techData = 0;
	public int scoutedTechData = 0;

	
	public boolean save(NBTTagCompound nbt) {
		//don't save data if it's empty
		if(!this.hasData())
			return false;
		nbt.setInteger(NBT_TECH_DATA, techData);
		nbt.setInteger(NBT_SCOUTED_TECH_DATA, scoutedTechData);
		return true;
	}
	
	public boolean load(NBTTagCompound nbt) {
		if(!nbt.hasKey(NBT_TECH_DATA))
			return false;
		techData = nbt.getInteger(NBT_TECH_DATA);
		scoutedTechData = nbt.getInteger(NBT_SCOUTED_TECH_DATA);
		return true;
	}
	
	public boolean hasData() {
		return techData != 0;
	}
}
