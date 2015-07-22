package com.fravokados.dangertech.techmobs.common.handler;

import com.fravokados.dangertech.techmobs.block.tileentity.TileEntityCreativeTechnology;
import com.fravokados.dangertech.techmobs.client.gui.GuiCreativeTechnology;
import com.fravokados.dangertech.techmobs.inventory.ContainerCreativeTechnology;
import com.fravokados.dangertech.techmobs.lib.GUIIDs;
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
			case GUIIDs.CREATIVE_TECHNOLOGY:
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof TileEntityCreativeTechnology) {
					return new ContainerCreativeTechnology((TileEntityCreativeTechnology) te);
				}
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIIDs.CREATIVE_TECHNOLOGY:
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof TileEntityCreativeTechnology) {
					return new GuiCreativeTechnology((TileEntityCreativeTechnology) te, new ContainerCreativeTechnology((TileEntityCreativeTechnology) te));
				}
				break;
			}
		}
		return null;
	}
}
