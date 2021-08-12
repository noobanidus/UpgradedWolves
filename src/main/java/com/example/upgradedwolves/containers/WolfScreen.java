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
    private static final ResourceLocation INVENTORY = UpgradedWolves.getId("gui/wolf_chest_gui.png");
    private static final ResourceLocation TABS = UpgradedWolves.getId("gui/wolf_tabs_gui.png");
    private static final ResourceLocation POWERUP = UpgradedWolves.getId("gui/wolf_powerup_gui.png");
    WolfEntity wolf;
    String strength,speed,intelligence;
    float strNum,spdNum,intNum;
    int slots;
    boolean inventoryTab = true;

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
        final float BAG_LABEL_YPOS = 5;
        //TranslationTextComponent bagLabel = new TranslationTextComponent(StartupCommon.itemFlowerBag.getTranslationKey());
        ITextComponent bagLabel = new StringTextComponent("Wolf Inventory");
        if(wolf.hasCustomName())
            bagLabel = wolf.getCustomName();
        float BAG_LABEL_XPOS = (xSize * .7F) - this.font.getStringWidth(bagLabel.getString()) / 2.0F;                  // centre the label
        this.font.func_243248_b(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;

        if(inventoryTab){
            drawInventoryForeground(matrixStack, x, y);
        } else {
            drawPowerUpForeground(matrixStack, x, y);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0){
            if((171 + guiLeft) < mouseX && mouseX < (207 + guiLeft)
                && (7 + guiTop) < mouseY &&  mouseY < (31 + guiTop) )
                inventoryTab = true;
            else if((171 + guiLeft) < mouseX && mouseX < (207 + guiLeft)
                && (31 + guiTop) < mouseY && mouseY < (57 + guiTop))
                inventoryTab = false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;
        if(inventoryTab){
            this.minecraft.getTextureManager().bindTexture(INVENTORY);
            this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
            drawInventoryBackground(matrixStack, partialTicks, x, y, edgeSpacingX, edgeSpacingY);
        } else {
            this.minecraft.getTextureManager().bindTexture(POWERUP);
            this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
            drawPowerUpBackground(matrixStack, partialTicks, x, y);
        }
        

        
        drawTabs(matrixStack);
        
        InventoryScreen.drawEntityOnScreen(edgeSpacingX + 38, edgeSpacingY + 50, 40, (edgeSpacingX + 38) - x,(edgeSpacingY + 30) - y, wolf);
    }

    void drawInventoryForeground(MatrixStack matrixStack, int x, int y){
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),                              //this.font.drawString;
        PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());

        this.font.func_243248_b(matrixStack, new StringTextComponent(strength), 76, 29, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(speed), 76, 39, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(intelligence), 76, 49, Color.darkGray.getRGB());
    }

    void drawPowerUpForeground(MatrixStack matrixStack, int x, int y){

    }
    
    void drawInventoryBackground(MatrixStack matrixStack, float partialTicks, int x, int y,int edgeSpacingX, int edgeSpacingY){
        
        //Draw hearts
        int totalHearts = (int)wolf.getHealth();
        int index = 0;
        while(totalHearts > 1){            
            this.blit(matrixStack, guiLeft + 76 + (index * 9), guiTop + 14, 29, 182, 9, 9);
            index++;
            totalHearts -= 2;
        }
        if(totalHearts == 1)
            this.blit(matrixStack, guiLeft + 76 + (index * 9), guiTop + 14, 19, 182, 9, 9);

        buildXpBar(matrixStack, guiLeft + 113, guiTop + 31, 39, 185, (int)(strNum * 52));
        buildXpBar(matrixStack, guiLeft + 113, guiTop + 41, 39, 179, (int)(spdNum * 52));
        buildXpBar(matrixStack, guiLeft + 113, guiTop + 51, 94, 179, (int)(intNum * 52));
        extraSlots(matrixStack, slots - 1);
    }

    void drawPowerUpBackground(MatrixStack matrixStack, float partialTicks, int x, int y){

    }

    void buildXpBar(MatrixStack matrixStack, int x,int y,int u,int v, int amount){
        this.blit(matrixStack, x, y, u, v, amount, 5);
    }
    
    void extraSlots(MatrixStack matrixStack,int amount){
        for(int i = 0; i < amount; i++){
            this.blit(matrixStack, guiLeft + 24 + (i * 18), guiTop + 62, 0, 177, 18, 18);
        }
    }

    void drawTabs(MatrixStack matrixStack){
        this.minecraft.getTextureManager().bindTexture(TABS);
        if(inventoryTab){
            this.blit(matrixStack, guiLeft + 171, guiTop + 7, 0, 27, 36, 26);
            this.blit(matrixStack, guiLeft + 170, guiTop + 34, 0, 0, 36, 26);
        } else {
            this.blit(matrixStack, guiLeft + 170, guiTop + 7, 0, 0, 36, 26);
            this.blit(matrixStack, guiLeft + 171, guiTop + 33, 0, 27, 36, 26);
        }
    }
    
    
}