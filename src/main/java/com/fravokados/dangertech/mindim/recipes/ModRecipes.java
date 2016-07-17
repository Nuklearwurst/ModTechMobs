package com.fravokados.dangertech.mindim.recipes;

import com.fravokados.dangertech.core.plugin.energy.EnergyManager;
import com.fravokados.dangertech.core.plugin.energy.EnergyType;
import com.fravokados.dangertech.mindim.block.ModBlocks;
import com.fravokados.dangertech.mindim.block.types.PortalFrameType;
import com.fravokados.dangertech.mindim.item.ItemDestinationCard;
import com.fravokados.dangertech.mindim.item.ModItems;
import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Recipes
 * @author Nuklearwurst
 */
@SuppressWarnings({"UnnecessaryBoxing", "ConstantConditions"})
public class ModRecipes {

	public static void initRecipes() {
		if(Loader.isModLoaded("IC2")) {
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
			GameRegistry.addRecipe(new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_MIN_DIM),
					"ede",
					"oco",
					"ege",
					Character.valueOf('c'), new ItemStack(ModBlocks.blockPortalFrame, 1, PortalFrameType.BASIC_CONTROLLER.ordinal()),
					Character.valueOf('d'), new ItemStack(ModItems.itemDestinationCard, 1, ItemDestinationCard.META_NORMAL),
					Character.valueOf('e'), Items.ENDER_EYE,
					Character.valueOf('o'), Blocks.OBSIDIAN,
					Character.valueOf('g'), Items.DIAMOND_PICKAXE
			);
		}
	}
}
