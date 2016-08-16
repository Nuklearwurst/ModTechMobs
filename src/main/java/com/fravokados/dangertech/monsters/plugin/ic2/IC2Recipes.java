package com.fravokados.dangertech.monsters.plugin.ic2;

import com.fravokados.dangertech.monsters.common.init.ModItems;
import ic2.api.item.IC2Items;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * @author Nuklearwurst
 */
@SuppressWarnings("ConstantConditions")
public class IC2Recipes {

	public static void init() {
		//Crafting
		final ItemStack motor = IC2Items.getItem("crafting", "electric_motor");
		final ItemStack circuit = IC2Items.getItem("crafting", "circuit");

		//misc resource
		final ItemStack iridiumOre = IC2Items.getItem("misc_resource", "iridium_ore");

		//Plates
		final ItemStack plate_iron = IC2Items.getItem("plate", "iron");

		//TileEntity
		final ItemStack cesu = IC2Items.getItem("te", "cesu");

		//Tools
		final ItemStack oreScanner_OD = new ItemStack(IC2Items.getItemAPI().getItem("scanner"), 1, OreDictionary.WILDCARD_VALUE);
		final ItemStack frequencyTransmitter = IC2Items.getItem("frequency_transmitter");
		final ItemStack euReader = IC2Items.getItem("meter");

		//cable
		final ItemStack cableCopper = IC2Items.getItem("cable", "type:copper,insulation:1");

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.conservationUnit),
				"MEM",
				"GDG",
				"MCM",
				'M', motor,
				'C', cesu,
				'E', Items.ENDER_EYE,
				'D', "blockDiamond",
				'G', "glowstone"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.monsterDetector),
				"FFF",
				"GSG",
				" C ",
				'S', oreScanner_OD,
				'F', Items.ROTTEN_FLESH,
				'G', Items.GLOWSTONE_DUST,
				'C', circuit));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.quantumSword),
				"SPS",
				" I ",
				" W ",
				'S', Items.SHEARS,
				'P', plate_iron,
				'I', iridiumOre,
				'W', "stickWood"));


		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tdAnalyzer),
				"FEF",
				"WCW",
				'F', frequencyTransmitter,
				'E', euReader,
				'W', cableCopper,
				'C', circuit));

	}

}
