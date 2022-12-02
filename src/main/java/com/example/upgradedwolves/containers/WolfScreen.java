package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.powerup.gui.PowerUpGui;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class WolfScreen extends AbstractContainerScreen<WolfContainer> {
    private static final ResourceLocation INVENTORY = UpgradedWolves.getId("gui/wolf_chest_gui.png");
    private static final ResourceLocation TABS = UpgradedWolves.getId("gui/wolf_tabs_gui.png");
    private static final ResourceLocation POWERUP = UpgradedWolves.getId("gui/wolf_powerup_gui.png");
    Wolf wolf;
    PowerUpGui powerUpGui;
    String strength,speed,intelligence;
    float strNum,spdNum,intNum;
    int slots;
    boolean inventoryTab = true;
    CompoundTag nbt;

    public WolfScreen(WolfContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.wolf = screenContainer.wolf;
        this.slots = screenContainer.wolfItemHandler.getSlots();
        this.nbt = screenContainer.nbt;
        // this.leftPos = 0;
        // this.topPos = 0;
        int strength = nbt.getInt("strLevel");
        int speed = nbt.getInt("spdLevel");
        int intelligence = nbt.getInt("intLevel");
        this.strNum = nbt.getFloat("strNum");
        this.spdNum = nbt.getFloat("spdNum");
        this.intNum = nbt.getFloat("intNum");
        this.strength = "STR: " + strength;
        this.speed = "SPD: " + speed;
        this.intelligence = "INT: " + intelligence;
        this.imageWidth = 174;
        this.imageHeight = 175;
    }    
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        final float BAG_LABEL_YPOS = 5;
        //TranslatableComponent bagLabel = Component.translatable(StartupCommon.itemFlowerBag.getTranslationKey());
        Component bagLabel = Component.translatable("chestScreen.header.wolf");
        if(wolf.hasCustomName())
            bagLabel = wolf.getCustomName();
        float BAG_LABEL_XPOS = (imageWidth * .7F) - this.font.width(bagLabel.getString()) / 2.0F;                  // centre the label
        this.font.draw(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;

        if(inventoryTab){
            drawInventoryForeground(matrixStack, x, y);
        } else {
            drawPowerUpForeground(matrixStack, x, y);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0){
            if((171 + leftPos) < mouseX && mouseX < (207 + leftPos)
                && (7 + topPos) < mouseY &&  mouseY < (31 + topPos) ){
                inventoryTab = true;
                this.menu.setupContainer();
            }
            else if((171 + leftPos) < mouseX && mouseX < (207 + leftPos)
                && (31 + topPos) < mouseY && mouseY < (57 + topPos)){
                inventoryTab = false;
                this.menu.clearContainer();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        powerUpGui.dragSelectedGui(dragX, dragY);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int edgeSpacingX = (this.width - this.imageWidth) / 2;
        int edgeSpacingY = (this.height - this.imageHeight) / 2;
        if(inventoryTab){
            RenderSystem.setShaderTexture(0,INVENTORY);
            this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.imageWidth, this.imageHeight);
            drawInventoryBackground(matrixStack, partialTicks, x, y, edgeSpacingX, edgeSpacingY);
        } else {
            RenderSystem.setShaderTexture(0,POWERUP);
            this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.imageWidth, this.imageHeight);
            drawPowerUpBackground(matrixStack, partialTicks, x, y);
        }
        

        
        drawTabs(matrixStack);
        
        InventoryScreen.renderEntityInInventory(edgeSpacingX + 38, edgeSpacingY + 50, 40, (edgeSpacingX + 38) - x,(edgeSpacingY + 30) - y, wolf);
    }

    @Override
    public void init() {
        super.init();
        powerUpGui = new PowerUpGui(minecraft,wolf,nbt);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int x, int y) {        
        super.renderTooltip(matrixStack, x, y);
        if(!inventoryTab){
            powerUpGui.drawTooltips(matrixStack, x, y, width, height,leftPos + 17,topPos + 68);
        }
    }

    void drawInventoryForeground(PoseStack matrixStack, int x, int y){
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        float PLAYER_LABEL_YPOS = imageHeight - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.draw(matrixStack, this.playerInventoryTitle,                              //this.font.drawString;
        PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());

        this.font.draw(matrixStack, Component.literal(strength), 76, 29, Color.darkGray.getRGB());
        this.font.draw(matrixStack, Component.literal(speed), 76, 39, Color.darkGray.getRGB());
        this.font.draw(matrixStack, Component.literal(intelligence), 76, 49, Color.darkGray.getRGB());
    }

    void drawPowerUpForeground(PoseStack matrixStack, int x, int y){

    }
    
    void drawInventoryBackground(PoseStack matrixStack, float partialTicks, int x, int y,int edgeSpacingX, int edgeSpacingY){
        
        //Draw hearts
        int totalHearts = (int)wolf.getHealth();
        int index = 0;
        while(totalHearts > 1){            
            this.blit(matrixStack, leftPos + 76 + (index * 9), topPos + 14, 29, 182, 9, 9);
            index++;
            totalHearts -= 2;
        }
        if(totalHearts == 1)
            this.blit(matrixStack, leftPos + 76 + (index * 9), topPos + 14, 19, 182, 9, 9);

        buildXpBar(matrixStack, leftPos + 113, topPos + 31, 39, 185, (int)(strNum * 52));
        buildXpBar(matrixStack, leftPos + 113, topPos + 41, 39, 179, (int)(spdNum * 52));
        buildXpBar(matrixStack, leftPos + 113, topPos + 51, 94, 179, (int)(intNum * 52));
        extraSlots(matrixStack, slots - 1);
    }

    void drawPowerUpBackground(PoseStack matrixStack, float partialTicks, int x, int y){
        matrixStack.pushPose();
        matrixStack.translate((float)(leftPos + 17), (float)(topPos + 68), 0.0F);
        powerUpGui.drawTabBackground(matrixStack);
        matrixStack.popPose();
    }

    void buildXpBar(PoseStack matrixStack, int x,int y,int u,int v, int amount){
        this.blit(matrixStack, x, y, u, v, amount, 5);
    }
    
    void extraSlots(PoseStack matrixStack,int amount){
        for(int i = 0; i < amount; i++){
            this.blit(matrixStack, leftPos + 24 + (i * 18), topPos + 62, 0, 177, 18, 18);
        }
    }

    void drawTabs(PoseStack matrixStack){
        RenderSystem.setShaderTexture(0,TABS);
        if(inventoryTab){
            this.blit(matrixStack, leftPos + 171, topPos + 7, 0, 27, 36, 26);
            this.blit(matrixStack, leftPos + 170, topPos + 34, 0, 0, 36, 26);
        } else {
            this.blit(matrixStack, leftPos + 170, topPos + 7, 0, 0, 36, 26);
            this.blit(matrixStack, leftPos + 171, topPos + 33, 0, 27, 36, 26);
        }
    }
    
    
}