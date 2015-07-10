package com.fravokados.techmobs.common;

import com.fravokados.techmobs.lib.Textures;
import com.fravokados.techmobs.network.ModNetworkManager;
import com.fravokados.techmobs.network.message.MessageEMP;
import com.fravokados.techmobs.plugin.EMPHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Nuklearwurst
 */
public class EMPExplosion {

	private Entity exploder;
	private World world;
	private double x;
	private double y;
	private double z;
	private float strength;
	private int radius;

	public EMPExplosion(Entity exploder, World world, double x, double y, double z, float strength, int radius) {
		this.exploder = exploder;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.strength = strength;
		this.radius = radius;
	}

	public EMPExplosion(World world, double x, double y, double z, float strength, int radius) {
		this(null, world, x, y, z, strength, radius);
	}

	public void writeMessage(MessageEMP msg) {
		msg.x = x;
		msg.y = y;
		msg.z = z;
		msg.strength = strength;
		msg.radius = radius;
	}

	@SuppressWarnings("unchecked")
	public void doExplosion() {
		for (int posX = 1 - radius; posX < radius; posX++) {
			for (int posY = 1 - radius; posY < radius; posY++) {
				for (int posZ = 1 - radius; posZ < radius; posZ++) {
					applyEMP((int) Math.ceil(x + posX), (int) Math.ceil(y + posY), (int) Math.ceil(z + posZ));
				}
			}
		}
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		for (Entity entity : list) {
			EMPHandler.applyEMPOnEntity(entity, x, y, z, strength, radius);
		}
	}

	public void doEffects() {
		double rad = Math.PI / 32;
		for (int i = 0; i < 64; i++) {
			//TODO better particle effects
			world.spawnParticle("explode", x, y, z, Math.sin(i * rad) * strength * 0.2, (world.rand.nextDouble() - 0.5) * 0.5, Math.cos(i * rad) * strength * 0.2);
			world.spawnParticle("portal", x, y, z, Math.sin(i * rad) * strength * 0.3, (world.rand.nextDouble() - 0.5) * 0.5, Math.cos(i * rad) * strength * 0.3);
		}
		world.playSoundEffect(x, y, z, Textures.MOD_ASSET_DOMAIN + "emp", strength, 1);
	}

	public void doExplosionWithEffects() {
		doExplosion();
		doEffectsOnClientAndServer();
	}

	public void doEffectsOnClientsAround() {
		ModNetworkManager.INSTANCE.sendToAllAround(new MessageEMP(this), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 100));
	}

	public void doEffectsOnClientAndServer() {
		doEffects();
		doEffectsOnClientsAround();
	}

	private void applyEMP(int x, int y, int z) {
		double deltaX = this.x - x;
		double deltaY = this.y - y;
		double deltaZ = this.z - z;
		if (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ <= radius * radius) {
			EMPHandler.applyEMP(world, x, y, z, this.x, this.y, this.z, strength, radius);
		}
	}

	public static void createExplosion(World world, double x, double y, double z, float strength, int radius) {
		new EMPExplosion(world, x, y, z, strength, radius).doExplosionWithEffects();
	}

	public static void createExplosion(Entity exploder, double x, double y, double z, float strength, int radius) {
		new EMPExplosion(exploder, exploder.worldObj, x, y, z, strength, radius).doExplosionWithEffects();
	}

	public static void createExplosion(Entity exploder, float strength, int radius) {
		createExplosion(exploder, exploder.posX, exploder.posY + exploder.yOffset, exploder.posZ, strength, radius);
	}

	public static void createExplosion(Entity exploder, int radius) {
		createExplosion(exploder, radius, radius);
	}

	public static void createExplosionWithYOffset(Entity exploder, float offset, int radius) {
		createExplosion(exploder, exploder.posX, exploder.posY + offset, exploder.posZ, radius, radius);
	}
}
