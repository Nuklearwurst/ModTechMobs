package com.fravokados.dangertech.portals.plugin.ic2;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.core.recipes.ShapedRecipeEnergyType;
import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.common.init.ModItems;
import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * registers recipes with ic2 items
 */
@SuppressWarnings("ConstantConditions")
public class IC2Recipes {

	/**
	 * registers recipes with ic2 items
	 */
	public static void init() {
		final ItemStack electronicCircuit = IC2Items.getItem("crafting", "circuit");
		final ItemStack copperWire = IC2Items.getItem("cable", "type:copper,insulation:0");
		final ItemStack machineCase = IC2Items.getItem("resource", "machine");
		final ItemStack mfe = IC2Items.getItem("te", "mfe");
		final ItemStack cesu = IC2Items.getItem("te", "cesu");
		final ItemStack evTransformer = IC2Items.getItem("te", "ev_transformer");
		final ItemStack advancedCircuit = IC2Items.getItem("crafting", "advanced_circuit");
		final ItemStack coil = IC2Items.getItem("crafting", "coil");

		// Destination Card (normal)
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				" p ",
				"wcw",
				"ppp",
				'c', electronicCircuit,
				'w', copperWire,
				'p', Items.PAPER
		);

		// Portal Frame (+rotated)
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
				"oco",
				"wmw",
				"oco",
				'm', machineCase,
				'o', Blocks.OBSIDIAN,
				'w', copperWire,
				'c', coil
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
				"owo",
				"cmc",
				"owo",
				'm', machineCase,
				'o', Blocks.OBSIDIAN,
				'e', Items.ENDER_PEARL,
				'w', copperWire,
				'c', coil
		);

		// PortalController
		GameRegistry.addRecipe(EnergyManager.createItemStackWithEnergyType(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal(), EnergyType.VANILLA),
				"ded",
				"afa",
				"tct",
				'f', new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_FRAME.ordinal()),
				'a', advancedCircuit,
				'd', Items.DIAMOND,
				'e', Items.ENDER_EYE,
				't', evTransformer,
				'c', cesu
		);

		// DestinationCard - Mining Dimension
		GameRegistry.addRecipe(new ShapedRecipeEnergyType(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_GENERATING),
				"ede",
				"oco",
				"ege",
				'c', new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
				'd', new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				'e', Items.ENDER_EYE,
				'o', Blocks.OBSIDIAN,
				'g', Items.DIAMOND_PICKAXE
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
