package com.fravokados.dangertech.core.common.handler;

import com.fravokados.dangertech.api.core.upgrade.IUpgradable;
import com.fravokados.dangertech.core.client.gui.GuiUpgradeTool;
import com.fravokados.dangertech.core.inventory.ContainerUpgradeTool;
import com.fravokados.dangertech.core.lib.GUIIDs;
import com.fravokados.dangertech.core.lib.util.LogHelperCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class GuiHandler implements IGuiHandler {

	@Override
	@Nullable
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te != null && te.hasCapability(IUpgradable.UPGRADABLE, null)) {
					return new ContainerUpgradeTool(player.inventory, te.getCapability(IUpgradable.UPGRADABLE, null));
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.getDimension() + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}

	@Override
	@Nullable
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.UPGRADE_TOOL:
			{
				TileEntity te = world.getTileEntity(pos);
				if(te != null && te.hasCapability(IUpgradable.UPGRADABLE, null)) {
					return new GuiUpgradeTool(player.inventory, te.getCapability(IUpgradable.UPGRADABLE, null));
				} else {
					LogHelperCore.error("Tried opening upgrade gui on not upgradable tile!\nPlayer: " + player.toString() + "Tile: [" + world.provider.getDimension() + ": " + x + ", " + y + ", " + z + "]");
					return null;
				}
			}
		}
		return null;
	}
}
