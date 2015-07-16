package com.fravokados.mindim.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author Nuklearwurst
 */
public class MessagePlayerMotionUpdate implements IMessage, IMessageHandler<MessageGuiElementClicked, IMessage> {

	private double xMotion;
	private double yMotion;
	private double zMotion;

	public MessagePlayerMotionUpdate() {

	}

	public MessagePlayerMotionUpdate(double motionX, double motionY, double motionZ) {
		this.xMotion = motionX;
		this.yMotion = motionY;
		this.zMotion = motionZ;
	}

	public MessagePlayerMotionUpdate(EntityPlayerMP player) {
		this(player.motionX, player.motionY, player.motionZ);
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		xMotion = buf.readDouble();
		yMotion = buf.readDouble();
		zMotion = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(xMotion);
		buf.writeDouble(yMotion);
		buf.writeDouble(zMotion);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageGuiElementClicked message, MessageContext ctx) {
		EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
		if (entityPlayer != null)
		{
			entityPlayer.setVelocity(xMotion, yMotion, zMotion);
			entityPlayer.velocityChanged = true;
		}
		return null;
	}
}
