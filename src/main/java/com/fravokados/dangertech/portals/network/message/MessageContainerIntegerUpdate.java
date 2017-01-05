package com.fravokados.dangertech.portals.network.message;

import com.fravokados.dangertech.portals.network.IContainerIntegerListener;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * @author Nuklearwurst
 */
public class MessageContainerIntegerUpdate implements IMessage, IMessageHandler<MessageContainerIntegerUpdate, IMessage> {

	public byte elementId;
	public int value;

	public MessageContainerIntegerUpdate()
	{

	}

	public MessageContainerIntegerUpdate(byte elementId, int value)
	{
		this.elementId = elementId;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.elementId = buf.readByte();
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(elementId);
		buf.writeInt(value);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@Nullable
	public IMessage onMessage(MessageContainerIntegerUpdate message, MessageContext ctx)
	{
		EntityPlayer entityPlayer = Minecraft.getMinecraft().player;

		if (entityPlayer != null)
		{
			if (entityPlayer.openContainer instanceof IContainerIntegerListener)
			{
				((IContainerIntegerListener) entityPlayer.openContainer).onIntegerUpdate(message.elementId, message.value);
			}
		}

		return null;
	}
}
