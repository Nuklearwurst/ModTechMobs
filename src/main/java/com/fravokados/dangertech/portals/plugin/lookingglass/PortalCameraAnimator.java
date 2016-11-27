package com.fravokados.dangertech.portals.plugin.lookingglass;

import net.minecraft.entity.Entity;

/**
 * based on {@link com.xcompwiz.lookingglass.api.animator.CameraAnimatorPlayer]
 * FIXME LookingGlass integration
 * @author Nuklearwurst
 */
public class PortalCameraAnimator /*implements ICameraAnimator*/ {
//	private final IViewCamera camera;

	private double x, y, z;
	private int dimId;
	private Entity player;
//	private ChunkCoordinates target;

	private boolean updateY;

	private float accum;

//	public PortalCameraAnimator(IViewCamera camera, double x, double y, double z, int dimId, Entity player) {
//		this.camera = camera;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.dimId = dimId;
//		this.player = player;
//	}

	/*
	@Override
	public void setTarget(ChunkCoordinates target) {
		this.target = new ChunkCoordinates(target);
	}
	*/

//	@Override
	public void refresh() {
		updateY = true;
	}

//	@Override
	public void update(float dt) {
		// This animator is incomplete and broken. It's a rough approximation I made at 4AM one night.
		// However, it's also pretty cool looking, so I'm not going to bother fixing it. :P
		// Note: Needs base yaw and pitch of view
		if (dimId != player.world.provider.getDimension()) return;

		// A standard accumulator trick to force periodic rechecks of the y position. Probably superfluous.
		if ((accum += dt) >= 1000) {
			updateY = true;
			accum -= 1000;
		}
		if (updateY) updateTargetPosition();
		double dx = player.posX - x;
		double dy = player.posY - (y + player.getYOffset());
		double dz = player.posZ - z;
		double length = Math.sqrt(dx * dx + dz * dz + dy * dy);
		float yaw = -(float) Math.atan2(dx, dz);
		yaw *= 180 / Math.PI;
		float pitch = (float) Math.asin(dy / length);
		pitch *= 180 / Math.PI;
//		camera.setLocation(target.posX, target.posY, target.posZ);
//		camera.setYaw(yaw);
//		camera.setPitch(pitch);
	}

	private void updateTargetPosition() {
		updateY = false;
		/*
		int x = target.posX;
		int y = target.posY;
		int z = target.posZ;
		if (!camera.chunkExists(x, z)) {
			if (camera.getBlockData().getBlock(x, y, z).getBlocksMovement(camera.getBlockData(), x, y, z)) {
				while (y > 0 && camera.getBlockData().getBlock(x, --y, z).getBlocksMovement(camera.getBlockData(), x, y, z))
					;
				if (y == 0) y = target.posY;
				else y += 2;
			} else {
				while (y < 256 && !camera.getBlockData().getBlock(x, ++y, z).getBlocksMovement(camera.getBlockData(), x, y, z))
					;
				if (y == 256) y = target.posY;
				else ++y;
			}
			target.posY = y;
		}
		*/
	}
}
