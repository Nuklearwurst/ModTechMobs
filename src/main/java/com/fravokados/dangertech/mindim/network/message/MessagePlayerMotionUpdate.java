package com.fravokados.dangertech.mindim.network.message;


import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
