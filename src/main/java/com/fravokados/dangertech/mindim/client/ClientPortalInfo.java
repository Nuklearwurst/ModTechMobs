package com.fravokados.dangertech.mindim.client;

import com.fravokados.dangertech.mindim.plugin.lookingglass.LookingGlassRenderInfo;
import com.fravokados.dangertech.mindim.portal.PortalMetrics;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	public EnumFacing originDirection;
	public EnumFacing targetDirection;

	public LookingGlassRenderInfo lookingGlass;

	public void createLookingGlass(PortalMetrics metrics, World worldObj) {
		lookingGlass = new LookingGlassRenderInfo();
		lookingGlass.create(targetDimension, new BlockPos(targetX, targetY, targetZ), metrics, originDirection, worldObj.provider.getDimensionId());
	}

	public void destroyLookingGlass() {
		if(lookingGlass != null) {
			lookingGlass.destroy();
			lookingGlass = null;
		}
	}
}
