package com.fravokados.mindim.recipes;

import com.fravokados.mindim.block.BlockPortalFrame;
import com.fravokados.mindim.block.ModBlocks;
import com.fravokados.mindim.item.ItemDestinationCard;
import com.fravokados.mindim.item.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Recipes
 * @author Nuklearwurst
 */
@SuppressWarnings("UnnecessaryBoxing")
public class RecipeManager {

	public static void initRecipes() {
		ItemStack electronicCircuit = IC2Items.getItem("electronicCircuit");
		ItemStack copperWire = IC2Items.getItem("copperCableItem");
		ItemStack machineCase = IC2Items.getItem("machine");
		ItemStack mfe = IC2Items.getItem("mfeUnit");
		ItemStack cesu = IC2Items.getItem("cesuUnit");
		ItemStack evTransformer = IC2Items.getItem("evTransformer");
		ItemStack advancedCircuit = IC2Items.getItem("advancedCircuit");
		ItemStack coil = IC2Items.getItem("coil");

		/**
		 * Destination Card (normal)
		 */
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				" p ",
				"wcw",
				"ppp",
				Character.valueOf('c'), electronicCircuit,
				Character.valueOf('w'), copperWire,
				Character.valueOf('p'), Items.paper
		);

		/**
		 * Portal Frame (+rotated)
		 */
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_FRAME_ENTITY),
				"oco",
				"wmw",
				"oco",
				Character.valueOf('m'), machineCase,
				Character.valueOf('o'), Blocks.obsidian,
				Character.valueOf('w'), copperWire,
				Character.valueOf('c'), coil
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_FRAME_ENTITY),
				"owo",
				"cmc",
				"owo",
				Character.valueOf('m'), machineCase,
				Character.valueOf('o'), Blocks.obsidian,
				Character.valueOf('e'), Items.ender_pearl,
				Character.valueOf('w'), copperWire,
				Character.valueOf('c'), coil
		);

		/**
		 * PortalController
		 */
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_CONTROLLER_ENTITY),
				"ded",
				"afa",
				"tct",
				Character.valueOf('f'), new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_FRAME_ENTITY),
				Character.valueOf('a'), advancedCircuit,
				Character.valueOf('d'), Items.diamond,
				Character.valueOf('e'), Items.ender_eye,
				Character.valueOf('t'), evTransformer,
				Character.valueOf('c'), cesu
		);
		//OLD Recipe
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_CONTROLLER_ENTITY),
//				"ded",
//				"ama",
//				"sfs",
//				Character.valueOf('f'), new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_FRAME_ENTITY),
//				Character.valueOf('m'), mfe,
//				Character.valueOf('a'), advancedCircuit,
//				Character.valueOf('d'), Items.diamond,
//				Character.valueOf('e'), Items.ender_eye,
//				Character.valueOf('s'), Blocks.stone
//		);

		/**
		 * DestinationCard - Mining Dimension
		 */
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_MIN_DIM),
				"ede",
				"oco",
				"ege",
				Character.valueOf('c'), new ItemStack(ModBlocks.blockPortalFrame, 1, BlockPortalFrame.META_CONTROLLER_ENTITY),
				Character.valueOf('d'), new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				Character.valueOf('e'), Items.ender_eye,
				Character.valueOf('o'), Blocks.obsidian,
				Character.valueOf('g'), Items.diamond_pickaxe
		);
	}
}
