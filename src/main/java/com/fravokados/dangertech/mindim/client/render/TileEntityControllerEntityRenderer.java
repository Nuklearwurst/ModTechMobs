package com.fravokados.dangertech.mindim.client.render;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.plugin.PluginLookingGlass;
import com.fravokados.dangertech.mindim.plugin.lookingglass.PluginLookingGlassImpl;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Nuklearwurst
 */
public class TileEntityControllerEntityRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float delta) {
		if(PluginLookingGlass.isAvailable() && tileEntity != null & tileEntity instanceof TileEntityPortalControllerEntity) {
			TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) tileEntity;
			if(te.isActive() && te.renderInfo != null && te.renderInfo.lookingGlass != null && te.renderInfo.lookingGlass.isValid()) {
				if(te.getMetrics() == null) {
					te.updateMetrics();
				}
				if(te.getMetrics() != null) {
					PluginLookingGlassImpl.renderPortalEntity(tileEntity, x, y, z, delta, te.getMetrics(), te.renderInfo);
				}
			}
		}
	}
}
