package com.fravokados.dangertech.portals.event;

import com.fravokados.dangertech.portals.ModMiningDimension;
import com.fravokados.dangertech.portals.configuration.Settings;
import com.fravokados.dangertech.portals.lib.Textures;
import com.fravokados.dangertech.portals.lib.util.LogHelperMD;
import com.fravokados.dangertech.portals.portal.PortalManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Nuklearwurst
 */
public class ModEventHandler {

	@SubscribeEvent
	public void registerOtherTextures(TextureStitchEvent.Pre evt) {
		evt.getMap().registerSprite(Textures.GUI_SLOT_DESTINATION_CARD);
	}

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
