package com.fravokados.dangertech.mindim.network.message;

import com.fravokados.dangertech.mindim.network.IGuiTextUpdateHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nuklearwurst
 */
public class MessageGuiTextUpdate implements IMessage, IMessageHandler<MessageGuiTextUpdate, IMessage> {

	public String elementName;
	public String elementText;

	public MessageGuiTextUpdate() {

	}

	public MessageGuiTextUpdate(String elementName, String elementText) {
		this.elementName = elementName;
		this.elementText = elementText;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int elementNameLength = buf.readInt();
		this.elementName = new String(buf.readBytes(elementNameLength).array());
		int elementTextLength = buf.readInt();
		this.elementText = new String(buf.readBytes(elementTextLength).array());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(elementName.length());
		buf.writeBytes(elementName.getBytes());
		buf.writeInt(elementText.length());
		buf.writeBytes(elementText.getBytes());
	}

	@Override
	public IMessage onMessage(MessageGuiTextUpdate message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;

		if (entityPlayer != null) {
			if (entityPlayer.openContainer instanceof IGuiTextUpdateHandler) {
				((IGuiTextUpdateHandler) entityPlayer.openContainer).handleGuiTextUpdate(message.elementName, message.elementText);
			}
		}

		return null;
	}

}
