package com.fravokados.dangertech.mindim.item;

import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortal;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalControllerEntity;
import com.fravokados.dangertech.mindim.block.tileentity.TileEntityPortalFrame;
import com.fravokados.dangertech.mindim.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 *
 */
public class ItemMDDebugger extends ItemMD {

	public ItemMDDebugger() {
		super(Strings.Item.debugger);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity != null) {
			final String side = world.isRemote ? "CLIENT: " : "SERVER: ";
			if(tileEntity instanceof TileEntityPortalControllerEntity) {
				final TileEntityPortalControllerEntity te = (TileEntityPortalControllerEntity) tileEntity;
				player.addChatComponentMessage(new TextComponentString(side + "facing: " + te.getFacing() + ", state: " + te.getState() + ", portalState: " + te.getPortalFrameState()));
			} else if(tileEntity instanceof TileEntityPortalFrame) {
				final TileEntityPortalFrame te = (TileEntityPortalFrame) tileEntity;
				player.addChatComponentMessage(new TextComponentString(side + "facing: " + te.getFacing() + ", active: " + te.isActive() + ", portalState: " + te.getPortalFrameState()));

			} else if(tileEntity instanceof TileEntityPortal && !world.isRemote) {
				final TileEntityPortal te = (TileEntityPortal) tileEntity;
				player.addChatComponentMessage(new TextComponentString(side + "controller: " + te.getController()));

			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
}
