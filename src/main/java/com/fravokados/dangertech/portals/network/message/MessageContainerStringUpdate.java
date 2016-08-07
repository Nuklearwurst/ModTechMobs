package com.fravokados.dangertech.portals.network.message;

import com.fravokados.dangertech.portals.network.IGuiTextUpdateHandler;
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
public class MessageContainerStringUpdate implements IMessage, IMessageHandler<MessageContainerStringUpdate, IMessage> {
	public String elementName;
	public String elementText;

	public MessageContainerStringUpdate() {

	}

	public MessageContainerStringUpdate(String elementName, String elementText) {
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
	@SideOnly(Side.CLIENT)
	@Nullable
	public IMessage onMessage(MessageContainerStringUpdate message, MessageContext ctx) {
		EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;

		if (entityPlayer != null)
		{
			if (entityPlayer.openContainer instanceof IGuiTextUpdateHandler)
			{
				((IGuiTextUpdateHandler) entityPlayer.openContainer).handleGuiTextUpdate(message.elementName, message.elementText);
			}
		}

		return null;
	}
}
