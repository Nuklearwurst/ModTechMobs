package com.fravokados.dangertech.core.common.handler;

import com.fravokados.dangertech.api.upgrade.IUpgradable;
import com.fravokados.dangertech.core.client.gui.GuiUpgradeTool;
import com.fravokados.dangertech.core.inventory.ContainerUpgradeTool;
import com.fravokados.dangertech.core.lib.GUIIDs;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author Nuklearwurst
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te instanceof IUpgradable) {
					return new ContainerUpgradeTool(player.inventory, (IUpgradable) te);
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.getDimensionId() + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te instanceof IUpgradable) {
					return new GuiUpgradeTool(player.inventory, (IUpgradable) te);
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.getDimensionId() + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}
}
