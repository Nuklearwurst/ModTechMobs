package com.fravokados.dangertech.mindim.plugin.lookingglass;

import com.fravokados.dangertech.mindim.portal.PortalMetrics;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Nuklearwurst
 */
public class LookingGlassRenderInfo {
//	public IWorldView worldView;

	public void create(Integer dimid, BlockPos coords, int width, int height) {
//		worldView = PluginLookingGlassImpl.lookingGlassAPI.createWorldView(dimid, coords, width, height);
//		worldView.setAnimator(new PortalCameraAnimator(worldView.getCamera(), coords.getX() + 0.5D, coords.getY() + 0.5D, coords.getZ() + 0.5D, dimid, Minecraft.getMinecraft().thePlayer));
	}

	public void destroy() {
//		if(worldView != null) {
//			PluginLookingGlassImpl.lookingGlassAPI.cleanupWorldView(worldView);
//			worldView = null;
//		}
	}

//	public boolean isValid() {
//		return worldView != null;
//	}

	public void create(int targetDimension, BlockPos coords, PortalMetrics metrics, EnumFacing originDirection, int dimensionId) {
		int width = (int) ((metrics.getMaxSide() - metrics.getMinSide() - 1) * 16);
		int height = (int) ((metrics.getMaxUp() - metrics.getMinUp() - 1) * 16);
//		worldView = PluginLookingGlassImpl.lookingGlassAPI.createWorldView(targetDimension, coords, width, height);
//		worldView.setAnimator(new PortalCameraAnimator(worldView.getCamera(), metrics.originX + originDirection.getFrontOffsetX(), metrics.originY + originDirection.getFrontOffsetY(), metrics.originZ + originDirection.getFrontOffsetZ(), dimensionId, Minecraft.getMinecraft().thePlayer));
	}
}
