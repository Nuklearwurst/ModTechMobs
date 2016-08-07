package com.fravokados.dangertech.monsters.network.message;

import com.fravokados.dangertech.monsters.network.IContainerIntegerListener;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

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
	@Nullable
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
