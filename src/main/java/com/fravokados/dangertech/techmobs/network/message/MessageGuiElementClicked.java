package com.fravokados.dangertech.techmobs.network.message;

import com.fravokados.dangertech.techmobs.network.IElementButtonHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

/**
 * adapted from pahimars Equivalent Exchannge
 * https://github.com/pahimar/Equivalent-Exchange-3/
 */
public class MessageGuiElementClicked implements IMessage, IMessageHandler<MessageGuiElementClicked, IMessage> {
	public String elementName;
	public int buttonPressed;

	public MessageGuiElementClicked() {

	}

	public MessageGuiElementClicked(String elementName, int buttonPressed) {
		this.elementName = elementName;
		this.buttonPressed = buttonPressed;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int elementNameLength = buf.readInt();
		this.elementName = new String(buf.readBytes(elementNameLength).array());
		this.buttonPressed = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(elementName.length());
		buf.writeBytes(elementName.getBytes());
		buf.writeInt(buttonPressed);
	}

	@Override
	@Nullable
	public IMessage onMessage(MessageGuiElementClicked message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;

		if (entityPlayer != null) {
			if (entityPlayer.openContainer instanceof IElementButtonHandler) {
				((IElementButtonHandler) entityPlayer.openContainer).handleElementButtonClick(message.elementName, message.buttonPressed);
			}
		}

		return null;
	}
}