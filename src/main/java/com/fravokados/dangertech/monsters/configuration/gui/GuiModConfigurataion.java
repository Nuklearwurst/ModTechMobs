package com.fravokados.dangertech.monsters.configuration.gui;

import com.fravokados.dangertech.monsters.ModTechMobs;
import com.fravokados.dangertech.monsters.lib.Reference;
import com.fravokados.dangertech.monsters.lib.Strings.Keys;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiModConfigurataion extends GuiConfig {

	public GuiModConfigurataion(GuiScreen parent) {
		super(parent,
				getConfigElements(),
				Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModTechMobs.config.config.toString()));
	}

	@SuppressWarnings({ "rawtypes" })
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<>();
		list.add(new ConfigElement(ModTechMobs.config.config.getCategory(Configuration.CATEGORY_GENERAL)));
		list.add(new ConfigElement(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_DATA)));
		list.add(new ConfigElement(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_SCANNING)));
        return list;
	}
}
