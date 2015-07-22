package com.fravokados.dangertech.techmobs.configuration.gui;

import com.fravokados.dangertech.techmobs.ModTechMobs;
import com.fravokados.dangertech.techmobs.lib.Reference;
import com.fravokados.dangertech.techmobs.lib.Strings.Keys;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

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
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new ConfigElement<Object>(ModTechMobs.config.config.getCategory(Configuration.CATEGORY_GENERAL)));
		list.add(new ConfigElement<Object>(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_DATA)));
		list.add(new ConfigElement<Object>(ModTechMobs.config.config.getCategory(Keys.CATEGORY_TECH_SCANNING)));
        return list;
	}
}
