package com.fravokados.dangertech.techmobs.common.init;

import com.fravokados.dangertech.techmobs.lib.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Nuklearwurst
 */
public class ModSounds {

	public static SoundEvent EMP;


	public static void registerSounds() {
		EMP = registerSound("emp");
	}

	private static SoundEvent registerSound(String name) {
		ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent evt = new SoundEvent(loc).setRegistryName(loc);
		GameRegistry.register(evt);
		return evt;
	}
}
