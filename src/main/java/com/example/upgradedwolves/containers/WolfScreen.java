package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

public class WolfScreen extends ContainerScreen<WolfContainer> {
    private static final ResourceLocation TEXTURE = UpgradedWolves.getId("gui/wolf_chest_gui.png");
    WolfEntity wolf;
    int strength;
    int speed;
    int intelligence;

    public WolfScreen(WolfContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.wolf = screenContainer.wolf;
        // this.guiLeft = 0;
        // this.guiTop = 0;
        //final IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        //this.strength = handler.getLevel(WolfStatsEnum.Strength);
        //this.speed = handler.getLevel(WolfStatsEnum.Speed);
        //this.intelligence = handler.getLevel(WolfStatsEnum.Intelligence);
        this.xSize = 174;
        this.ySize = 175;
    }    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        final float BAG_LABEL_YPOS = 5;
        //TranslationTextComponent bagLabel = new TranslationTextComponent(StartupCommon.itemFlowerBag.getTranslationKey());
        StringTextComponent bagLabel = new StringTextComponent("Wolf Inventory");
        float BAG_LABEL_XPOS = (xSize * .7F) - this.font.getStringWidth(bagLabel.getString()) / 2.0F;                  // centre the label
        this.font.func_243248_b(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),                              //this.font.drawString;
        PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());   
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
        InventoryScreen.drawEntityOnScreen(edgeSpacingX + 38, edgeSpacingY + 50, 40, (edgeSpacingX + 38) - x,(edgeSpacingY + 30) - y, wolf);
    }

    
    
    
    
}