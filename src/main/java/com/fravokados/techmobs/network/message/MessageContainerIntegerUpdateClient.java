package com.fravokados.techmobs.network.message;

import com.fravokados.techmobs.network.IContainerIntegerListener;
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
public class MessageContainerIntegerUpdateClient implements IMessage, IMessageHandler<MessageContainerIntegerUpdateClient, IMessage> {

	public byte elementId;
	public int value;

	public MessageContainerIntegerUpdateClient() {

	}

	public MessageContainerIntegerUpdateClient(byte elementId, int value) {
		this.elementId = elementId;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.elementId = buf.readByte();
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(elementId);
		buf.writeInt(value);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageContainerIntegerUpdateClient message, MessageContext ctx) {
		EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;

		if (entityPlayer != null) {
			if (entityPlayer.openContainer instanceof IContainerIntegerListener) {
				((IContainerIntegerListener) entityPlayer.openContainer).onIntegerUpdate(message.elementId, message.value);
			}
		}

		return null;
	}
}
