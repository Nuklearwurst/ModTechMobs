package com.fravokados.dangertech.api.monsters.techdata.values;

import com.fravokados.dangertech.api.core.util.EmptyCapabilityStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Capability for items of TileEntities that provide a techvalue
 * @author Nuklearwurst
 */
public interface ITechdataCapability {

	@CapabilityInject(ITechdataCapability.class)
	Capability<ITechdataCapability> TECHDATA = null;

	int getTechData();

	static void register() {
		CapabilityManager.INSTANCE.register(ITechdataCapability.class, new EmptyCapabilityStorage<>(), Impl::new);
	}

	/**
	 * Basic Implementation of the {@link ITechdataCapability} capability
	 */
	class Impl implements ITechdataCapability {

		private final int value;

		public Impl() {
			this.value = 0;
		}

		public Impl(int value) {
			this.value = value;
		}

		@Override
		public int getTechData() {
			return value;
		}
	}
}
