package com.fravokados.dangertech.portals.item;

import com.fravokados.dangertech.core.lib.util.ItemUtils;
import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.common.init.ModItems;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.lib.GUIIDs;
import com.fravokados.dangertech.portals.lib.NBTKeys;
import com.fravokados.dangertech.portals.lib.Strings;
import com.fravokados.dangertech.portals.lib.Textures;
import com.fravokados.dangertech.portals.lib.util.TeleportUtils;
import com.fravokados.dangertech.portals.portal.PortalManager;
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

	public static final int META_INVALID = -1;
	public static final int META_NORMAL = 0;
	public static final int META_GENERATING = 1; //card that generates a portal (doesn't need an existing target portal)

	public ItemDestinationCard() {
		super(Strings.Item.destinationCard);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		{
			ItemStack stack = new ItemStack(item, 1, 1);
			ItemUtils.getNBTTagCompound(stack).setInteger(NBTKeys.DESTINATION_CARD_PORTAL_DIM, Settings.dimensionId);
			EnergyManager.createItemVariantsForEnergyTypes(list, stack, EnergyManager.getAvailableEnergyTypes());
		}
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

		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);

		if (stack.getItemDamage() == META_GENERATING) {
			if (GuiScreen.isShiftKeyDown()) {
				//Dimension
				final int dim = getDimensionFromStack(stack);
				final String dest = TeleportUtils.getNameForDimension(dim);
				if (dest != null) {
					info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_DIMENSION, dest));
				}
			}
			//EnergyType
			EnergyType type = EnergyType.readFromNBT(nbt);
			if (type != EnergyType.INVALID) {
				info.add(type.getColorString() + Strings.translate(type.getUnlocalizedName()) + TextFormatting.RESET);
			}
			//FrameBlock Count
			final int count = nbt.getInteger(NBTKeys.DESTINATION_CARD_FRAME_BLOCKS);
			info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_MINDIM, count));
			if (GuiScreen.isShiftKeyDown()) {
				info.add(Strings.translate(Strings.Tooltip.ITEM_DESTINATION_CARD_MINDIM_INFO_1));
			}
		} else {
			if (nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_NAME) && nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_TYPE)) {
				final String dest = nbt.getString(NBTKeys.DESTINATION_CARD_PORTAL_NAME);
				info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_DESTINATION, dest));
				if (GuiScreen.isShiftKeyDown()) {
					//Portal Type
					final int type = nbt.getInteger(NBTKeys.DESTINATION_CARD_PORTAL_TYPE);
					info.add(Strings.translateWithFormat(Strings.Tooltip.ITEM_DESTINATION_CARD_TYPE, PortalMetrics.Type.getLocalizedName(type)));
				}
			} else {
				info.add(TextFormatting.ITALIC + Strings.translate(Strings.Tooltip.ITEM_DESTINATION_CARD_EMPTY) + TextFormatting.RESET);
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

	@Override
	public void registerModels() {
		registerItemModel(Textures.ITEM_DESTINATION_CARD, META_NORMAL);
		registerItemModel(Textures.ITEM_DESTINATION_CARD_MINDIM, META_GENERATING);
	}

	/**
	 * Creates a new DestinationCard ({@link #META_NORMAL}) with given destination
	 *
	 * @param id   portalid
	 * @param name name
	 * @return the created ItemStack
	 */
	public static ItemStack fromDestination(int id, String name) {
		//noinspection ConstantConditions
		return writeDestinationToStack(new ItemStack(ModItems.itemDestinationCard, 1, 0), id, name);
	}


	/**
	 * writes the given portal id and name to the nbt data of the ItemStack
	 */
	public static ItemStack writeDestinationToStack(ItemStack stack, int id, @Nullable String name) {
		NBTTagCompound nbt = ItemUtils.getNBTTagCompound(stack);
		nbt.setInteger(NBTKeys.DESTINATION_CARD_PORTAL_TYPE, PortalMetrics.Type.ENTITY_PORTAL.ordinal());
		nbt.setInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID, id);
		if (name != null) {
			nbt.setString(NBTKeys.DESTINATION_CARD_PORTAL_NAME, name);
		}
		return stack;
	}


	/**
	 * @return saved portalid (NBT), returns {@link PortalManager#PORTAL_INVALID_ITEM} if not valid
	 */
	@SuppressWarnings("ConstantConditions")
	public static int getPortalIDFromStack(ItemStack stack) {
		if (stack.getItemDamage() == META_GENERATING) {
			return PortalManager.PORTAL_GENERATING;
		}
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID)) {
			return stack.getTagCompound().getInteger(NBTKeys.DESTINATION_CARD_PORTAL_ID);
		}
		return PortalManager.PORTAL_INVALID_ITEM;
	}

	/**
	 * @return saved portaltype (NBT), returns -1 if not valid
	 */
	@SuppressWarnings("ConstantConditions")
	public static int getTypeFromStack(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTKeys.DESTINATION_CARD_PORTAL_TYPE)) {
			return stack.getTagCompound().getInteger(NBTKeys.DESTINATION_CARD_PORTAL_TYPE);
		}
		return -1;
	}

	@SuppressWarnings("ConstantConditions")
	public static boolean hasDestination(ItemStack stack) {
		if (stack.getItemDamage() == META_GENERATING) {
			return true;
		}
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			return nbt.hasKey(NBTKeys.DESTINATION_CARD_PORTAL_ID);
		}
		return false;
	}

	/**
	 * note: undefined behaviour when passing a stack that is no  {@link #META_GENERATING} DestinationCard
	 *
	 * @return the dimension of an {@link #META_GENERATING} DestinationCard. Returns 0 if nbt is invalid
	 */
	@SuppressWarnings("ConstantConditions")
	public static int getDimensionFromStack(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTKeys.DESTINATION_CARD_PORTAL_DIM)) {
			return stack.getTagCompound().getInteger(NBTKeys.DESTINATION_CARD_PORTAL_DIM);
		}
		return 0;
	}

	/**
	 * writes a dimensionid to the given stack
	 *
	 * @param stack stack to be updated
	 * @param dim   dinmension to be written
	 * @return the ItemStack to allow chaining
	 */
	@SuppressWarnings("ConstantConditions")
	public static ItemStack writeDimensionToStack(ItemStack stack, int dim) {
		ItemUtils.getNBTTagCompound(stack).setInteger(NBTKeys.DESTINATION_CARD_PORTAL_DIM, dim);
		return stack;
	}

	@SuppressWarnings("ConstantConditions")
	public static ItemStack createGeneratingCard(int stacksize, int dimension) {
		return new ItemStack(ModItems.itemDestinationCard, stacksize, META_GENERATING);
	}
}
