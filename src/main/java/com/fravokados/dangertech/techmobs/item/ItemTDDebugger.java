package com.fravokados.dangertech.techmobs.item;

import com.fravokados.dangertech.api.block.IFacingSix;
import com.fravokados.dangertech.techmobs.lib.Strings;
import com.fravokados.dangertech.techmobs.techdata.TDManager;
import com.fravokados.dangertech.techmobs.techdata.values.TDValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

/**
 * @author Nuklearwurst
 */
public class ItemTDDebugger extends ItemTM {

	public ItemTDDebugger() {
		super(Strings.Item.TD_DEBUGGER);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntity te = world.getTileEntity(pos);
		if(te != null) {
			if(te instanceof IFacingSix) {
				player.addChatComponentMessage(new TextComponentString("IFacingSixTile. Facing: " + ((IFacingSix) te).getFacing() + ", isClient=" + world.isRemote));
			}
			if(!world.isRemote) {
				player.addChatComponentMessage(new TextComponentTranslation(Strings.Chat.debuggerTileEntity, TDValues.getInstance().getTechDataForTileEntity(te)));
			}
		} else {
			if(!world.isRemote) {
				player.addChatMessage(new TextComponentTranslation(Strings.Chat.debuggerTileEntityNotFound));
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) {
			player.addChatComponentMessage(new TextComponentTranslation(Strings.Chat.debuggerPlayerTechLevel, TDManager.getPlayerTechLevel(player)));
			player.addChatComponentMessage(new TextComponentTranslation(Strings.Chat.debuggerPlayerScouted, TDManager.getPlayerScoutedTechLevel(player)));
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<>(EnumActionResult.PASS, stack);
	}
}
