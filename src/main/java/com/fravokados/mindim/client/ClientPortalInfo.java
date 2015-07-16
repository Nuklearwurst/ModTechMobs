package com.fravokados.mindim.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * stores portal information relevant to the client (for rendering)
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class ClientPortalInfo {

	public int targetX;
	public int targetY;
	public int targetZ;

	public ForgeDirection originDirection;
	public ForgeDirection targetDirection;


}
