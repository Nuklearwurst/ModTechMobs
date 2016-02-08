package com.fravokados.dangertech.mindim.recipes;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyTypes;
import com.fravokados.dangertech.mindim.block.ModBlocks;
import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.item.ItemDestinationCard;
import com.fravokados.dangertech.mindim.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Recipes
 * @author Nuklearwurst
 */
@SuppressWarnings("UnnecessaryBoxing")
public class ModRecipes {

	public static void initRecipes() {
//		ItemStack electronicCircuit = IC2Items.getItem("electronicCircuit");
//		ItemStack copperWire = IC2Items.getItem("copperCableItem");
//		ItemStack machineCase = IC2Items.getItem("machine");
//		ItemStack mfe = IC2Items.getItem("mfeUnit");
//		ItemStack cesu = IC2Items.getItem("cesuUnit");
//		ItemStack evTransformer = IC2Items.getItem("evTransformer");
//		ItemStack advancedCircuit = IC2Items.getItem("advancedCircuit");
//		ItemStack coil = IC2Items.getItem("coil");

		//FIXME ic2 integration
		ItemStack electronicCircuit = new ItemStack(Items.apple);
		ItemStack copperWire = electronicCircuit;
		ItemStack machineCase = electronicCircuit;
		ItemStack mfe = electronicCircuit;
		ItemStack cesu = electronicCircuit;
		ItemStack evTransformer = electronicCircuit;
		ItemStack advancedCircuit = electronicCircuit;
		ItemStack coil = electronicCircuit;
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
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
				"oco",
				"wmw",
				"oco",
				Character.valueOf('m'), machineCase,
				Character.valueOf('o'), Blocks.obsidian,
				Character.valueOf('w'), copperWire,
				Character.valueOf('c'), coil
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
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
		GameRegistry.addRecipe(EnergyManager.createItemStackWithEnergyType(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal(), EnergyTypes.VANILLA),
				"ded",
				"afa",
				"tct",
				Character.valueOf('f'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_FRAME.ordinal()),
				Character.valueOf('a'), advancedCircuit,
				Character.valueOf('d'), Items.diamond,
				Character.valueOf('e'), Items.ender_eye,
				Character.valueOf('t'), evTransformer,
				Character.valueOf('c'), cesu
		);

		/**
		 * DestinationCard - Mining Dimension
		 */
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_MIN_DIM),
				"ede",
				"oco",
				"ege",
				Character.valueOf('c'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
				Character.valueOf('d'), new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				Character.valueOf('e'), Items.ender_eye,
				Character.valueOf('o'), Blocks.obsidian,
				Character.valueOf('g'), Items.diamond_pickaxe
		);
	}
}
