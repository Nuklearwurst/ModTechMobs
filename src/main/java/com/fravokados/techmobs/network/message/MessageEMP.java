package com.fravokados.techmobs.network.message;

import com.fravokados.techmobs.common.EMPExplosion;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class MessageEMP implements IMessage, IMessageHandler<MessageEMP, IMessage> {

	public double x, y, z;
	public float strength;
	public int radius;

	public MessageEMP() {}

	public MessageEMP(EMPExplosion emp) {
		emp.writeMessage(this);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		strength = buf.readFloat();
		radius = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(strength);
		buf.writeInt(radius);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(MessageEMP message, MessageContext ctx) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		EMPExplosion emp = new EMPExplosion(player.getEntityWorld(), message.x,message.y, message.z, message.strength, message.radius);
		emp.doEffects();
		return null;
	}
}
