package com.fravokados.dangertech.techmobs.network.message;

import com.fravokados.dangertech.techmobs.network.IContainerIntegerListener;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class MessageContainerIntegerUpdateServer implements IMessage, IMessageHandler<MessageContainerIntegerUpdateServer, IMessage> {

	public byte elementId;
	public int value;

	public MessageContainerIntegerUpdateServer() {

	}

	public MessageContainerIntegerUpdateServer(byte elementId, int value) {
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

	@Override
	public IMessage onMessage(MessageContainerIntegerUpdateServer message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
		if (entityPlayer != null) {
			if (entityPlayer.openContainer instanceof IContainerIntegerListener) {
				((IContainerIntegerListener) entityPlayer.openContainer).onIntegerUpdate(message.elementId, message.value);
			}
		}

		return null;
	}

}
