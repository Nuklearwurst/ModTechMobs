package com.fravokados.dangertech.mindim.client.render;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.plugin.PluginLookingGlass;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * @author Nuklearwurst
 */
public class TileEntityControllerEntityRenderer extends TileEntitySpecialRenderer<TileEntityPortalControllerEntity> {

	@Override
	public void renderTileEntityAt(TileEntityPortalControllerEntity tileEntity, double x, double y, double z, float delta, int destroyStage) {
		if(PluginLookingGlass.isAvailable() && tileEntity != null & tileEntity != null) {
			//			if(te.isActive() && te.renderInfo != null && te.renderInfo.lookingGlass != null && te.renderInfo.lookingGlass.isValid()) {
//				if(te.getMetrics() == null) {
//					te.updateMetrics();
//				}
//				if(te.getMetrics() != null) {
//					PluginLookingGlassImpl.renderPortalEntity(tileEntity, x, y, z, delta, te.getMetrics(), te.renderInfo);
//				}
//			}
			//FIXME lookingglass integration
		}
	}
}
