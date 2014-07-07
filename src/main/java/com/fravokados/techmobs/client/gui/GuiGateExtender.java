package com.fravokados.techmobs.client.gui;

import org.lwjgl.opengl.GL11;

import com.fravokados.techmobs.inventory.ContainerGateExtender;
import com.fravokados.techmobs.tileentity.TileEntityGateExtender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiGateExtender extends GuiContainer {

    private TileEntityGateExtender te;
    private static final String __OBFID = "CL_00000758";

    public GuiGateExtender(InventoryPlayer invPlayer, TileEntityGateExtender tile)
    {
        super(new ContainerGateExtender(invPlayer, tile));
        this.te = tile;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.te.hasCustomInventoryName() ? this.te.getInventoryName() : I18n.format(this.te.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

//        if (this.te.isBurning())
//        {
//            int i1 = this.te.getBurnTimeRemainingScaled(13);
//            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
//            i1 = this.te.getCookProgressScaled(24);
//            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
//        }
    }

}
