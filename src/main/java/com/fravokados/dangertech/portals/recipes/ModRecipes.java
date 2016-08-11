package com.fravokados.dangertech.portals.recipes;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.portals.block.ModBlocks;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.item.ModItems;
import com.google.common.collect.Maps;
import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Recipes
 *
 * @author Nuklearwurst
 */
@SuppressWarnings({"UnnecessaryBoxing", "ConstantConditions"})
public class ModRecipes {

	public static void initRecipes() {
		if (Loader.isModLoaded("IC2")) {
			ItemStack electronicCircuit = IC2Items.getItem("crafting", "circuit");
			ItemStack copperWire = IC2Items.getItem("cable", "type:copper,insulation:0");
			ItemStack machineCase = IC2Items.getItem("resource", "machine");
			ItemStack mfe = IC2Items.getItem("te", "mfe");
			ItemStack cesu = IC2Items.getItem("te", "cesu");
			ItemStack evTransformer = IC2Items.getItem("te", "ev_transformer");
			ItemStack advancedCircuit = IC2Items.getItem("crafting", "advanced_circuit");
			ItemStack coil = IC2Items.getItem("crafting", "coil");

			// Destination Card (normal)
			GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
					" p ",
					"wcw",
					"ppp",
					Character.valueOf('c'), electronicCircuit,
					Character.valueOf('w'), copperWire,
					Character.valueOf('p'), Items.PAPER
			);

			// Portal Frame (+rotated)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
					"oco",
					"wmw",
					"oco",
					Character.valueOf('m'), machineCase,
					Character.valueOf('o'), Blocks.OBSIDIAN,
					Character.valueOf('w'), copperWire,
					Character.valueOf('c'), coil
			);
			GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
					"owo",
					"cmc",
					"owo",
					Character.valueOf('m'), machineCase,
					Character.valueOf('o'), Blocks.OBSIDIAN,
					Character.valueOf('e'), Items.ENDER_PEARL,
					Character.valueOf('w'), copperWire,
					Character.valueOf('c'), coil
			);

			// PortalController
			GameRegistry.addRecipe(EnergyManager.createItemStackWithEnergyType(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal(), EnergyType.VANILLA),
					"ded",
					"afa",
					"tct",
					Character.valueOf('f'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_FRAME.ordinal()),
					Character.valueOf('a'), advancedCircuit,
					Character.valueOf('d'), Items.DIAMOND,
					Character.valueOf('e'), Items.ENDER_EYE,
					Character.valueOf('t'), evTransformer,
					Character.valueOf('c'), cesu
			);

			// DestinationCard - Mining Dimension
			GameRegistry.addRecipe(new ShapedRecipeEnergyType(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_GENERATING),
					"ede",
					"oco",
					"ege",
					Character.valueOf('c'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
					Character.valueOf('d'), new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
					Character.valueOf('e'), Items.ENDER_EYE,
					Character.valueOf('o'), Blocks.OBSIDIAN,
					Character.valueOf('g'), Items.DIAMOND_PICKAXE
			));
//			GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_GENERATING),
//					"ede",
//					"oco",
//					"ege",
//					Character.valueOf('c'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
//					Character.valueOf('d'), new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
//					Character.valueOf('e'), Items.ENDER_EYE,
//					Character.valueOf('o'), Blocks.OBSIDIAN,
//					Character.valueOf('g'), Items.DIAMOND_PICKAXE
//			);
		}
	}

	private static ItemStack[] createInputs(int width, int height, Object... recipeComponents) {
		String inputList = "";
		int index = 0;

		if (recipeComponents[index] instanceof String[]) {
			String[] recipeLayout = (String[]) recipeComponents[index];
			index++;

			assert recipeLayout.length == height;

			for (String line : recipeLayout) {
				assert width == line.length();
				inputList = inputList + line;
			}
		} else {
			for(int i = 0; i < 3; i++) {
				String line = (String) recipeComponents[i];
				inputList = inputList + line;
			}
			index = 3;
		}

		Map<Character, ItemStack> map = Maps.<Character, ItemStack>newHashMap();

		while (index < recipeComponents.length) {
			Character character = (Character) recipeComponents[index];
			ItemStack itemstack = null;

			if (recipeComponents[index + 1] instanceof Item) {
				itemstack = new ItemStack((Item) recipeComponents[index + 1]);
			} else if (recipeComponents[index + 1] instanceof Block) {
				itemstack = new ItemStack((Block) recipeComponents[index + 1], 1, 32767);
			} else if (recipeComponents[index + 1] instanceof ItemStack) {
				itemstack = (ItemStack) recipeComponents[index + 1];
			}

			map.put(character, itemstack);
			index += 2;
		}

		ItemStack[] outputStacks = new ItemStack[width * height];

		for (int i = 0; i < width * height; ++i) {
			char character = inputList.charAt(i);

			if (map.containsKey(Character.valueOf(character))) {
				outputStacks[i] = map.get(Character.valueOf(character)).copy();
			} else {
				outputStacks[i] = null;
			}
		}

		return outputStacks;
	}

	private static class ShapedRecipeEnergyType implements IRecipe {

		private ItemStack[] inputs;
		private ItemStack output;

		private ShapedRecipeEnergyType(ItemStack output, Object ... inputs) {
			this(output, createInputs(3, 3, inputs));
		}

		private ShapedRecipeEnergyType(ItemStack output, ItemStack[] inputs) {
			assert inputs.length == 9 : "Recipe Input array must be of length 9!";
			this.output = output;
			this.inputs = inputs;
		}


		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			EnergyType typeFound = null;
			for (int i = 0; i < 9; i++) {
				ItemStack expectedStack = inputs[i];
				ItemStack craftingStack = inv.getStackInSlot(i);
				if (expectedStack != null || craftingStack != null) {
					if (expectedStack == null && craftingStack != null || expectedStack != null && craftingStack == null) {
						return false;
					}

					if (craftingStack.getItem() != expectedStack.getItem()) {
						return false;
					}

					if (expectedStack.getMetadata() != 32767 && craftingStack.getMetadata() != expectedStack.getMetadata()) {
						return false;
					}

					//make sure energy types line up
					if (craftingStack.hasTagCompound()) {
						EnergyType type = EnergyType.readFromNBT(craftingStack.getTagCompound());
						if (type != EnergyType.INVALID) {
							if (typeFound == null) {
								typeFound = type;
							} else if (typeFound != type) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}

		@Nullable
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack stackOut = getRecipeOutput().copy();
			for (int i = 0; i < 9; i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.hasTagCompound()) {
					EnergyType type = EnergyType.readFromNBT(stack.getTagCompound());
					if (type != EnergyType.INVALID) {
						type.writeToNBT(stack.getTagCompound());
						break;
					}
				}

			}
			return stackOut;
		}

		@Override
		public int getRecipeSize() {
			return 9;
		}

		@Nullable
		@Override
		public ItemStack getRecipeOutput() {
			return output;
		}

		@Override
		public ItemStack[] getRemainingItems(InventoryCrafting inv) {
			ItemStack[] itemStacks = new ItemStack[9];

			for (int i = 0; i < 9; ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				itemStacks[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
			}

			return itemStacks;
		}
	}
}
