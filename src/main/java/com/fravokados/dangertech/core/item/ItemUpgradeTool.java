package com.fravokados.dangertech.core.item;

import com.fravokados.dangertech.api.core.upgrade.IUpgradable;
import com.fravokados.dangertech.core.ModNwCore;
import com.fravokados.dangertech.core.lib.GUIIDs;
import com.fravokados.dangertech.core.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Tool used to manipulate machines
 * @author Nuklearwurst
 */
public class ItemUpgradeTool extends ItemNW {

	public ItemUpgradeTool() {
		super(Strings.Item.UPGRADE_TOOL);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te.hasCapability(IUpgradable.UPGRADABLE, null)) {
			playerIn.openGui(ModNwCore.instance, GUIIDs.UPGRADE_TOOL, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return EnumActionResult.PASS;
	}
}
