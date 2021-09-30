package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.powerup.gui.PowerUpGui;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(PoseStack matrixStack, int x, int y) {
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
                && (7 + guiTop) < mouseY &&  mouseY < (31 + guiTop) ){
                inventoryTab = true;
                this.container.setupContainer();
            }
            else if((171 + guiLeft) < mouseX && mouseX < (207 + guiLeft)
                && (31 + guiTop) < mouseY && mouseY < (57 + guiTop)){
                inventoryTab = false;
                this.container.clearContainer();
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
    protected void drawGuiContainerBackgroundLayer(PoseStack matrixStack, float partialTicks, int x, int y) {
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

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        powerUpGui = new PowerUpGui(minecraft,wolf,nbt);
    }

    @Override
    protected void renderHoveredTooltip(PoseStack matrixStack, int x, int y) {        
        super.renderHoveredTooltip(matrixStack, x, y);
        if(!inventoryTab){
            powerUpGui.drawTooltips(matrixStack, x, y, width, height,guiLeft + 17,guiTop + 68);
        }
    }

    void drawInventoryForeground(PoseStack matrixStack, int x, int y){
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),                              //this.font.drawString;
        PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());

        this.font.func_243248_b(matrixStack, new StringTextComponent(strength), 76, 29, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(speed), 76, 39, Color.darkGray.getRGB());
        this.font.func_243248_b(matrixStack, new StringTextComponent(intelligence), 76, 49, Color.darkGray.getRGB());
    }

    void drawPowerUpForeground(PoseStack matrixStack, int x, int y){

    }
    
    void drawInventoryBackground(PoseStack matrixStack, float partialTicks, int x, int y,int edgeSpacingX, int edgeSpacingY){
        
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

    void drawPowerUpBackground(PoseStack matrixStack, float partialTicks, int x, int y){
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)(guiLeft + 17), (float)(guiTop + 68), 0.0F);
        powerUpGui.drawTabBackground(matrixStack);
        RenderSystem.popMatrix();
    }

    void buildXpBar(PoseStack matrixStack, int x,int y,int u,int v, int amount){
        this.blit(matrixStack, x, y, u, v, amount, 5);
    }
    
    void extraSlots(PoseStack matrixStack,int amount){
        for(int i = 0; i < amount; i++){
            this.blit(matrixStack, guiLeft + 24 + (i * 18), guiTop + 62, 0, 177, 18, 18);
        }
    }

    void drawTabs(PoseStack matrixStack){
        this.minecraft.getTextureManager().bindTexture(TABS);
        if(inventoryTab){
            this.blit(matrixStack, guiLeft + 171, guiTop + 7, 0, 27, 36, 26);
            this.blit(matrixStack, guiLeft + 170, guiTop + 34, 0, 0, 36, 26);
        } else {
            this.blit(matrixStack, guiLeft + 170, guiTop + 7, 0, 0, 36, 26);
            this.blit(matrixStack, guiLeft + 171, guiTop + 33, 0, 27, 36, 26);
        }
    }
    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        // TODO Auto-generated method stub
        
    }
    
    
}