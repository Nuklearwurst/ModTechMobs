package com.fravokados.dangertech.mindim.client;

import com.fravokados.dangertech.mindim.plugin.lookingglass.LookingGlassRenderInfo;
import com.fravokados.dangertech.mindim.portal.PortalMetrics;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * stores portal information relevant to the client (for rendering)
 * @author Nuklearwurst
 */
@SideOnly(Side.CLIENT)
public class ClientPortalInfo {

	public int targetDimension;

	public int targetX;
	public int targetY;
	public int targetZ;

	public ForgeDirection originDirection;
	public ForgeDirection targetDirection;

	public LookingGlassRenderInfo lookingGlass;

	public void createLookingGlass(PortalMetrics metrics, World worldObj) {
		lookingGlass = new LookingGlassRenderInfo();
		lookingGlass.create(targetDimension, new ChunkCoordinates(targetX, targetY, targetZ), metrics, originDirection, worldObj.provider.dimensionId);
	}

	public void destroyLookingGlass() {
		if(lookingGlass != null) {
			lookingGlass.destroy();
			lookingGlass = null;
		}
	}
}
