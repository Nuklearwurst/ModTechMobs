package com.fravokados.dangertech.portals.plugin.ic2;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.core.recipes.ShapedRecipeEnergyType;
import com.fravokados.dangertech.portals.block.types.PortalFrameType;
import com.fravokados.dangertech.portals.common.init.ModBlocks;
import com.fravokados.dangertech.portals.common.init.ModItems;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.item.ItemDestinationCard;
import com.fravokados.dangertech.portals.item.ItemMindDimUpgrade;
import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * registers recipes with ic2 items
 */
@SuppressWarnings("ConstantConditions")
public class IC2Recipes {

	/**
	 * registers recipes with ic2 items
	 */
	public static void init() {
		//Wire
		final ItemStack copperWire_noInsulation = IC2Items.getItem("cable", "type:copper,insulation:0");
		final ItemStack goldWire_noInsulation = IC2Items.getItem("cable", "type:gold,insulation:0");

		//Resource
		final ItemStack machineCase = IC2Items.getItem("resource", "machine");

		//TileEntities
		final ItemStack mfe = IC2Items.getItem("te", "mfe");
		final ItemStack cesu = IC2Items.getItem("te", "cesu");
		final ItemStack evTransformer = IC2Items.getItem("te", "ev_transformer");

		//Crafting
		final ItemStack electronicCircuit = IC2Items.getItem("crafting", "circuit");
		final ItemStack advancedCircuit = IC2Items.getItem("crafting", "advanced_circuit");
		final ItemStack coil = IC2Items.getItem("crafting", "coil");

		//Reactor
		final ItemStack neutronReflector = new ItemStack(IC2Items.getItemAPI().getItem("neutron_reflector"), 1, OreDictionary.WILDCARD_VALUE);

		// Destination Card (normal)
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				" p ",
				"wcw",
				"ppp",
				'c', electronicCircuit,
				'w', copperWire_noInsulation,
				'p', Items.PAPER
		);

		// Portal Frame (+rotated)
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
				"oco",
				"wmw",
				"oco",
				'm', machineCase,
				'o', Blocks.OBSIDIAN,
				'w', copperWire_noInsulation,
				'c', coil
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPortalFrame, 4, PortalFrameType.BASIC_FRAME.ordinal()),
				"owo",
				"cmc",
				"owo",
				'm', machineCase,
				'o', Blocks.OBSIDIAN,
				'e', Items.ENDER_PEARL,
				'w', copperWire_noInsulation,
				'c', coil
		);

		// PortalController
		GameRegistry.addRecipe(EnergyManager.createItemStackWithEnergyType(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal(), EnergyType.IC2),
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
		GameRegistry.addRecipe(new ShapedRecipeEnergyType(ItemDestinationCard.writeDimensionToStack(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_GENERATING), Settings.dimensionId),
				"ede",
				"oco",
				"ege",
				'c', new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
				'd', new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
				'e', Items.ENDER_EYE,
				'o', Blocks.OBSIDIAN,
				'g', Items.DIAMOND_PICKAXE
		));

		//Upgrades

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemUpgrade, 1, ItemMindDimUpgrade.META_EMPTY),
				"w w",
				" c ",
				"w w",
				'w', copperWire_noInsulation,
				'c', electronicCircuit));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemUpgrade, 1, ItemMindDimUpgrade.META_CLOSE_INCOMING_PORTAL),
				"cnc",
				"nun",
				"cnc",
				'n', neutronReflector,
				'c', goldWire_noInsulation,
				'u', new ItemStack(ModItems.itemUpgrade, 1, ItemMindDimUpgrade.META_EMPTY)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemUpgrade, 1, ItemMindDimUpgrade.META_REVERSE_DIRECTION),
				"cnc",
				"nun",
				"cnc",
				'n', neutronReflector,
				'c', coil,
				'u', new ItemStack(ModItems.itemUpgrade, 1, ItemMindDimUpgrade.META_CLOSE_INCOMING_PORTAL)));
	}
}
