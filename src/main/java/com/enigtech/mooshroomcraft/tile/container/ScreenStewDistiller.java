package com.enigtech.mooshroomcraft.tile.container;

import com.enigtech.mooshroomcraft.Mooshroomcraft;
import com.enigtech.mooshroomcraft.tile.container.ContainerStewDistiller;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenStewDistiller extends ContainerScreen<ContainerStewDistiller> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Mooshroomcraft.MOD_ID,"textures/gui/stew_distiller_gui.png");

    public ScreenStewDistiller(ContainerStewDistiller screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int relativeX = (this.width - this.xSize) / 2;
        int relativeY = (this.height - this.ySize) / 2;
        this.blit(relativeX, relativeY, 0, 0, this.xSize, this.ySize);
        if (this.container.isLit()) {
            int k = this.container.getBurnLeftScaled();
            this.blit(relativeX + 56, relativeY + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.container.getProgressionScaled();
        this.blit(relativeX + 79, relativeY + 34, 176, 14, l + 1, 16);
    }

}
