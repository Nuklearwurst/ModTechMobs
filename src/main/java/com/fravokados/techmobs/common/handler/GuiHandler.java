package com.fravokados.techmobs.common.handler;

import com.fravokados.techmobs.client.gui.GuiUpgradeTool;
import com.fravokados.techmobs.inventory.ContainerUpgradeTool;
import com.fravokados.techmobs.lib.GUIIDs;
import com.fravokados.techmobs.lib.util.LogHelper;
import com.fravokados.techmobs.api.upgrade.IUpgradable;
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
					LogHelper.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.dimensionId + ": " + x + ", " + y + ", " + z + "]");
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
					LogHelper.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.dimensionId + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}
}
