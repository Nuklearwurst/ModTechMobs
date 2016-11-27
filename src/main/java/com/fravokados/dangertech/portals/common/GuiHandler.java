package com.fravokados.dangertech.portals.common;

import com.fravokados.dangertech.portals.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.portals.client.gui.GuiDestinationCardMinDim;
import com.fravokados.dangertech.portals.client.gui.GuiEntityPortalController;
import com.fravokados.dangertech.portals.inventory.ContainerDestinationCardMinDim;
import com.fravokados.dangertech.portals.inventory.ContainerEntityPortalController;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.common.init.ModItems;
import com.fravokados.dangertech.portals.lib.GUIIDs;
import com.fravokados.dangertech.monsters.lib.util.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
			case GUIIDs.ENTITY_PORTAL_CONTROLLER:
			{
				TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) world.getTileEntity(pos);
				if(te == null) return null;
				return new ContainerEntityPortalController(player.inventory, te);
			}
			case GUIIDs.DESTINATION_CARD_MIN_DIM:
			{
				//noinspection ConstantConditions
				ItemStack item = PlayerUtils.getCurrentUsablePlayerItem(player,(ItemStack o) -> o.getItem() == ModItems.itemDestinationCard && o.getItemDamage() == ItemDestinationCard.META_GENERATING);
				if(item.isEmpty()) return null;
				return new ContainerDestinationCardMinDim(player.inventory, item);
			}
		}
		return null;
	}

	@Override
	@Nullable
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case GUIIDs.ENTITY_PORTAL_CONTROLLER:
			{
				TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) world.getTileEntity(pos);
				if(te == null) return null;
				return new GuiEntityPortalController(player.inventory, te);
			}
			case GUIIDs.DESTINATION_CARD_MIN_DIM:
			{
				//noinspection ConstantConditions
				ItemStack item = PlayerUtils.getCurrentUsablePlayerItem(player, (ItemStack o) -> o.getItem() == ModItems.itemDestinationCard && o.getItemDamage() == ItemDestinationCard.META_GENERATING);
				if(item.isEmpty()) return null;
				return new GuiDestinationCardMinDim(player.inventory, item);
			}
		}
		return null;
	}
}
