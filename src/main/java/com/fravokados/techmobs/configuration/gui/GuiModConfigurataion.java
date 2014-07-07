package com.fravokados.techmobs.configuration.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.fravokados.techmobs.ModTechMobs;
import com.fravokados.techmobs.lib.Strings.Keys;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class GuiModConfigurataion extends GuiConfig {

	public GuiModConfigurataion(GuiScreen parent) {
		super(parent,
				getConfigElements(),
				"TestMod", false, false, GuiConfig.getAbridgedConfigPath(ModTechMobs.config.config.toString()));
	}

	@SuppressWarnings({ "rawtypes" })
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new ConfigElement<Object>(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_DATA)));
		list.add(new ConfigElement<Object>(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_SCANNING)));
        return list;
	}
}
