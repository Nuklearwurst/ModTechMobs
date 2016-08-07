package com.fravokados.dangertech.portals.plugin.lookingglass;

import com.fravokados.dangertech.portals.client.ClientPortalInfo;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Nuklearwurst
 */
public class PluginLookingGlassImpl {
//	public static WorldViewAPI2 lookingGlassAPI;

	public static boolean load(Object api) {
//		if (api == null || !(api instanceof WorldViewAPI2)) {
//			LogHelperMD.error("Error loading LookingGlass Integration!");
//			return false;
//		} else {
//			lookingGlassAPI = (WorldViewAPI2) api;
//			return true;
//		}
		return false;
	}

	public static void renderPortalEntity(TileEntity tileEntity, double x, double y, double z, float delta, PortalMetrics pos, ClientPortalInfo renderInfo) {
		/*
		if (renderInfo.lookingGlass != null && renderInfo.lookingGlass.isValid()) {
			int texture = renderInfo.lookingGlass.worldView.getTexture();
			if (texture != 0) {
				double width = pos.getMaxSide() - pos.getMinSide() - 1;
				double height = pos.getMaxUp() - pos.getMinUp() - 1;
				double left = -width / 2.0D;
				double top = 0;
				renderInfo.lookingGlass.worldView.markDirty();
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glPushMatrix();
				double ox = pos.originX - tileEntity.xCoord + 0.5 * pos.front.offsetX;
				double oy = pos.originY - tileEntity.yCoord + 0.5 * pos.front.offsetY;
				double oz = pos.originZ - tileEntity.zCoord + 0.5 * pos.front.offsetZ;
				GL11.glTranslated(x + ox, y + oy, z + oz);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				Tessellator tessellator = Tessellator.instance;
				tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
				tessellator.addVertexWithUV(width + left, top, 0.0D, 1.0D, 0.0D);
				tessellator.addVertexWithUV(width + left, height + top, 0.0D, 1.0D, 1.0D);
				tessellator.addVertexWithUV(left, height + top, 0.0D, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				tessellator.setColorRGBA_F(0.0F, 0.0F, 1.0F, 1.0F);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(left, height + top, 0.0D, 0.0D, 1.0D);
				tessellator.addVertexWithUV(width + left, height + top, 0.0D, 1.0D, 1.0D);
				tessellator.addVertexWithUV(width + left, top, 0.0D, 1.0D, 0.0D);
				tessellator.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
				tessellator.draw();

//				tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
//				tessellator.startDrawingQuads();
//				tessellator.addVertexWithUV(0, 0, 0, 0, 0);
//				tessellator.addVertexWithUV(0, 1, 0, 0, 1);
//				tessellator.addVertexWithUV(1, 1, 0, 1, 1);
//				tessellator.addVertexWithUV(1, 0, 0, 1, 0);
//
//				tessellator.addVertexWithUV(0, 0, 0, 0, 0);
//				tessellator.addVertexWithUV(1, 0, 0, 1, 0);
//				tessellator.addVertexWithUV(1, 1, 0, 1, 1);
//				tessellator.addVertexWithUV(0, 1, 0, 0, 1);
//				tessellator.draw();
				GL11.glPopMatrix();
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}
		}
		*/
	}
}
