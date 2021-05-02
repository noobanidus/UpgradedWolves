package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.UpgradedWolves;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

public class WolfScreen extends ContainerScreen<WolfContainer> {
    private static final ResourceLocation TEXTURE = UpgradedWolves.getId("gui/wolf_chest_gui.png");
    WolfEntity wolf;
    String strength,speed,intelligence;
    float strNum,spdNum,intNum;
    int slots;

    public WolfScreen(WolfContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.wolf = screenContainer.wolf;
        this.slots = screenContainer.wolfItemHandler.getSlots();
        CompoundNBT nbt = screenContainer.nbt;
        // this.guiLeft = 0;
        // this.guiTop = 0;
        int strength = nbt.getInt("strLevel");
        int speed = nbt.getInt("spdLevel");
        int intelligence = nbt.getInt("intLevel");
        this.strNum = nbt.getFloat("strNum");
        this.spdNum = nbt.getFloat("spdNum");
        this.intNum = nbt.getFloat("intNum");
        this.strength = "STR: " + strength;
        this.speed = "SPD: " + speed;
        this.intelligence = "INT: " + intelligence;
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
        ITextComponent bagLabel = new StringTextComponent("Wolf Inventory");
        if(wolf.hasCustomName())
            bagLabel = wolf.getCustomName();
        float BAG_LABEL_XPOS = (xSize * .7F) - this.font.getStringWidth(bagLabel.getString()) / 2.0F;                  // centre the label
        this.font.func_243248_b(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),                              //this.font.drawString;
        PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());

        this.font.func_243248_b(matrixStack, new StringTextComponent(strength), 76, 29, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(speed), 76, 39, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(intelligence), 76, 49, Color.darkGray.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
        
        
        //Draw hearts
        int totalHearts = (int)wolf.getHealth();
        int index = 0;
        while(totalHearts > 1){
            //(76,14) 9
            this.blit(matrixStack, guiLeft + 76 + (index * 9), guiTop + 14, 29, 182, 9, 9);
            index++;
            totalHearts -= 2;
        }
        if(totalHearts == 1)
            this.blit(matrixStack, guiLeft + 76 + (index * 9), guiTop + 14, 19, 182, 9, 9);

        buildXpBar(matrixStack, guiLeft + 113, guiTop + 31, 39, 179, (int)(spdNum * 52));
        buildXpBar(matrixStack, guiLeft + 113, guiTop + 41, 39, 185, (int)(strNum * 52));
        buildXpBar(matrixStack, guiLeft + 113, guiTop + 51, 94, 179, (int)(intNum * 52));
        extraSlots(matrixStack, slots - 1);
        
        InventoryScreen.drawEntityOnScreen(edgeSpacingX + 38, edgeSpacingY + 50, 40, (edgeSpacingX + 38) - x,(edgeSpacingY + 30) - y, wolf);
    }

    void buildXpBar(MatrixStack matrixStack, int x,int y,int u,int v, int amount){
        this.blit(matrixStack, x, y, u, v, amount, 5);
    }
    
    void extraSlots(MatrixStack matrixStack,int amount){
        for(int i = 0; i < amount; i++){
            this.blit(matrixStack, guiLeft + 24 + (i * 18), guiTop + 62, 0, 177, 18, 18);
        }
    }
    
    
}