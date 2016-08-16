package com.fravokados.dangertech.portals.item;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.common.init.ModItems;
import com.fravokados.dangertech.portals.lib.GUIIDs;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.Textures;
import com.fravokados.dangertech.portals.portal.PortalMetrics;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Nuklearwurst
 */
public class ItemDestinationCard extends ItemMDMultiType {

	public static final int META_NORMAL = 0;
	public static final int META_GENERATING = 1; //card that generates a portal (doesn't need an existing target portal)

	public ItemDestinationCard() {
		super(Strings.Item.destinationCard);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		EnergyManager.createItemVariantsForEnergyTypes(list, item, 1, EnergyManager.getAvailableEnergyTypes());
	}

	@Override
	protected String getNameForItemStack(ItemStack s) {
		if (s.getItemDamage() == META_GENERATING) {
			return Strings.Item.destinationCardMinDim;
		}
		return Strings.Item.destinationCard;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> info, boolean b) {
		super.addInformation(stack, player, info, b);
		if (stack.getItemDamage() != META_GENERATING) {
			if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("destinationPortalType") && stack.getTagCompound().hasKey("destinationPortalName")) {
				int type = stack.getTagCompound().getInteger("destinationPortalType");
				String dest = stack.getTagCompound().getString("destinationPortalName");
				info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_TYPE, PortalMetrics.Type.getLocalizedName(type)));
				info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_DESTINATION, dest));
			} else {
				info.add(TextFormatting.ITALIC + Strings.translate(Strings.Tooltip.ITEM_DESTINATION_CARD_EMPTY) + TextFormatting.RESET);
			}
		} else {
			NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
			EnergyType type = EnergyType.readFromNBT(nbt);
			if (type != EnergyType.INVALID) {
				info.add(Strings.translate(type.getUnlocalizedName()));
			}
			int count = nbt.getInteger("frame_blocks");
			info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_MINDIM, count));
			if (GuiScreen.isShiftKeyDown()) {
				info.add(Strings.translate(Strings.Tooltip.ITEM_DESTINATION_CARD_MINDIM_INFO_1));
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && itemStack.getItemDamage() == META_GENERATING && player.isSneaking()) {
			player.openGui(ModMiningDimension.instance, GUIIDs.DESTINATION_CARD_MIN_DIM, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.getItemDamage() == META_GENERATING ? 1 : 64;
	}

	public static ItemStack fromDestination(int id, String name) {
		//noinspection ConstantConditions
		return writeDestination(new ItemStack(ModItems.itemDestinationCard, 1, 0), id, name);
	}

	public static ItemStack writeDestination(ItemStack stack, int id, @Nullable String name) {
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
		nbt.setInteger("destinationPortalType", PortalMetrics.Type.ENTITY_PORTAL.ordinal());
		nbt.setInteger("destinationPortal", id);
		if (name != null) {
			nbt.setString("destinationPortalName", name);
		}
		return stack;
	}

	@Override
	public void registerModels() {
		registerItemModel(Textures.ITEM_DESTINATION_CARD, META_NORMAL);
		registerItemModel(Textures.ITEM_DESTINATION_CARD_MINDIM, META_GENERATING);
	}
}
