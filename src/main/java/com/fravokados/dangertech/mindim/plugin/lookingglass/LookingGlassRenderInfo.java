package com.fravokados.dangertech.mindim.plugin.lookingglass;

import com.fravokados.dangertech.mindim.portal.PortalMetrics;
import com.xcompwiz.lookingglass.api.view.IWorldView;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Nuklearwurst
 */
public class LookingGlassRenderInfo {
	public IWorldView worldView;

	public void create(Integer dimid, ChunkCoordinates coords, int width, int height) {
		worldView = PluginLookingGlassImpl.lookingGlassAPI.createWorldView(dimid, coords, width, height);
		worldView.setAnimator(new PortalCameraAnimator(worldView.getCamera(), coords.posX + 0.5D, coords.posY + 0.5D, coords.posZ + 0.5D, dimid, Minecraft.getMinecraft().thePlayer));
	}

	public void destroy() {
		if(worldView != null) {
			PluginLookingGlassImpl.lookingGlassAPI.cleanupWorldView(worldView);
			worldView = null;
		}
	}

	public boolean isValid() {
		return worldView != null;
	}

	public void create(int targetDimension, ChunkCoordinates coords, PortalMetrics metrics, ForgeDirection originDirection, int dimensionId) {
		int width = (int) ((metrics.getMaxSide() - metrics.getMinSide() - 1) * 16);
		int height = (int) ((metrics.getMaxUp() - metrics.getMinUp() - 1) * 16);
		worldView = PluginLookingGlassImpl.lookingGlassAPI.createWorldView(targetDimension, coords, width, height);
		worldView.setAnimator(new PortalCameraAnimator(worldView.getCamera(), metrics.originX + originDirection.offsetX, metrics.originY + originDirection.offsetY, metrics.originZ + originDirection.offsetZ, dimensionId, Minecraft.getMinecraft().thePlayer));
	}
}
