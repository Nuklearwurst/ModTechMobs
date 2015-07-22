package com.fravokados.dangertech.core.common.handler;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.core.client.gui.GuiUpgradeTool;
import com.fravokados.dangertech.core.inventory.ContainerUpgradeTool;
import com.fravokados.dangertech.core.lib.GUIIDs;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof IUpgradable) {
					return new ContainerUpgradeTool(player.inventory, (IUpgradable) te);
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.dimensionId + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof IUpgradable) {
					return new GuiUpgradeTool(player.inventory, (IUpgradable) te);
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.dimensionId + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}
}
