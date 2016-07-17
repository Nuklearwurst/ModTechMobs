package com.fravokados.dangertech.mindim.event;

import com.fravokados.dangertech.mindim.ModMiningDimension;
import com.fravokados.dangertech.mindim.configuration.Settings;
import com.fravokados.dangertech.mindim.lib.util.LogHelperMD;
import com.fravokados.dangertech.mindim.portal.PortalManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Nuklearwurst
 */
public class ModEventHandler {

	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.PlaceEvent evt) {
		if (evt.getPlayer().dimension != Settings.dimensionId) {
//			GameRegistry.UniqueIdentifier block = GameRegistry.findUniqueIdentifierFor(evt.block);
//			if (block != null && block.modId.contains("BuildCraft")) {
//				if (block.name.equals("machineBlock") || block.name.equals("miningWellBlock")) {
//					evt.player.addChatComponentMessage(new ChatComponentTranslation(Strings.Chat.BLOCK_PLACING_CANCELLED));
//					evt.setCanceled(true);
//				}
//			}
			ResourceLocation id = evt.getPlacedBlock().getBlock().getRegistryName();
			//TODO reimplement block detection
		}
	}

	@SubscribeEvent
	public void loadWorld(WorldEvent.Load evt) {
		if (!evt.getWorld().isRemote && evt.getWorld().provider.getDimension() == 0) {
			WorldServer world = (WorldServer) evt.getWorld();
			PortalManager saveData = (PortalManager) world.getPerWorldStorage().getOrLoadData(PortalManager.class, "PortalManager");

			if (saveData == null) {
				saveData = new PortalManager("PortalManager");
				world.getPerWorldStorage().setData("PortalManager", saveData);
			}

			if (ModMiningDimension.instance.portalManager != null) {
				throw new IllegalStateException("PortalManager already loaded! (This is a programming Error)");
			}
			ModMiningDimension.instance.portalManager = saveData;
		}
	}

	@SubscribeEvent
	public void unloadWorld(WorldEvent.Unload evt) {
		if (!evt.getWorld().isRemote && evt.getWorld().provider.getDimension() == 0) {
			LogHelperMD.trace("Unloading PortalManager...");
			ModMiningDimension.instance.portalManager = null;
		}
	}
}
