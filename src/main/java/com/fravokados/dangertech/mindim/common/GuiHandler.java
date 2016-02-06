package com.fravokados.dangertech.mindim.common;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.client.gui.GuiDestinationCardMinDim;
import com.fravokados.dangertech.mindim.client.gui.GuiEntityPortalController;
import com.fravokados.dangertech.mindim.inventory.ContainerDestinationCardMinDim;
import com.fravokados.dangertech.mindim.inventory.ContainerEntityPortalController;
import com.fravokados.dangertech.mindim.lib.GUIIDs;
import net.minecraft.entity.player.EntityPlayer;
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
			case GUIIDs.ENTITY_PORTAL_CONTROLLER:
			{
				TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) world.getTileEntity(pos);
				return new ContainerEntityPortalController(player.inventory, te);
			}
			case GUIIDs.DESTINATION_CARD_MIN_DIM:
			{
				return new ContainerDestinationCardMinDim(player.inventory, player.getCurrentEquippedItem());
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.ENTITY_PORTAL_CONTROLLER:
			{
				TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) world.getTileEntity(pos);
				return new GuiEntityPortalController(player.inventory, te);
			}
			case GUIIDs.DESTINATION_CARD_MIN_DIM:
			{
				return new GuiDestinationCardMinDim(player.inventory, player.getCurrentEquippedItem());
			}
		}
		return null;
	}
}
