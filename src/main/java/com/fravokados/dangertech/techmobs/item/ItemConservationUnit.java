package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.techmobs.entity.EntityConservationUnit;
import com.fravokados.dangertech.techmobs.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemConservationUnit extends ItemTM {

	public ItemConservationUnit() {
		super(Strings.Item.CONSERVATION_UNIT);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			EntityConservationUnit entity = new EntityConservationUnit(world);
			entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0);
			world.spawnEntityInWorld(entity);
		}
		return EnumActionResult.SUCCESS;
	}
}
