package com.example.upgradedwolves.powerup.gui;

import java.util.ArrayList;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.powerup.ExamplePowerUp;
import com.example.upgradedwolves.powerup.PowerUp;
import com.example.upgradedwolves.powerup.PowerUpList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class PowerUpGui extends AbstractGui {
   Minecraft minecraft;
   private double scrollX;
   private double scrollY;
   private int minX = -141;
   private int minY = -93;
   private int maxX = 141;
   private int maxY = 93;
   private boolean centered;
   private float fade;
   private FontRenderer font;
   private CompoundNBT nbt;
   private static ResourceLocation background = new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png");
   private static final ResourceLocation POWERUP = UpgradedWolves.getId("gui/wolf_powerup_gui.png");
   public PowerUp[] powerUps;
   public WolfEntity wolf;


    
   public PowerUpGui(Minecraft minecraft,WolfEntity wolf,CompoundNBT nbt) {
      this.minecraft = minecraft;      
      this.wolf = wolf;
      this.nbt = nbt;
      powerUps = setPowerups();
      font = minecraft.fontRenderer;      
   }

   private PowerUp[] setPowerups(){
      switch(nbt.getInt("wolfType")){
         case 0:
            return PowerUpList.notSet;
         case 1: // Fighter type
            return PowerUpList.StrengthWolf;
         case 2: // Scavenger type
            return PowerUpList.ScavengerWolf;
         default:
            return null;
      }
   }

   public void drawTabBackground(MatrixStack matrixStack) {
      if (!this.centered) {
         this.scrollX = (double)0;
         this.scrollY = (double)0;
         this.centered = true;
      }

      RenderSystem.pushMatrix();
      RenderSystem.enableDepthTest();
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.colorMask(false, false, false, false);
      fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.depthFunc(518);
      fill(matrixStack, 141, 93, 0, 0, -16777216);
      RenderSystem.depthFunc(515);

      int i = MathHelper.floor(this.scrollX);
      int j = MathHelper.floor(this.scrollY);
      int k = i % 16;
      int l = j % 16;   

      this.minecraft.getTextureManager().bindTexture(background);

      for(int i1 = -1; i1 <= 15; ++i1) {
         for(int j1 = -1; j1 <= 8; ++j1) {
            blit(matrixStack, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
         }
      }

      this.minecraft.getTextureManager().bindTexture(POWERUP);
      
      displayPowerUps(matrixStack,i,j);
      
      RenderSystem.depthFunc(518);
      RenderSystem.translatef(0.0F, 0.0F, -950.0F);
      RenderSystem.colorMask(false, false, false, false);
      fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.translatef(0.0F, 0.0F, 950.0F);
      RenderSystem.depthFunc(515);
      RenderSystem.popMatrix();
   }
  
   public void drawTabTooltips(MatrixStack matrixStack, int mouseX, int mouseY, int width, int height,PowerUp powerUp) {
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 200.0F);
      fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0F) << 24);
      boolean flag = false;
      ArrayList<ITextComponent> textBoxInfo = new ArrayList<ITextComponent>();
      Style redStyle = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.RED)).setItalic(true);
      if(levelDistance(powerUp) <= 0){
         textBoxInfo.add(powerUp.getName());
         textBoxInfo.add(powerUp.getDescription(wolf));
      } else if(levelDistance(powerUp) <= 3){
         textBoxInfo.add(powerUp.getName());
         textBoxInfo.add(new StringTextComponent("???").setStyle(Style.EMPTY.setItalic(true)));
         textBoxInfo.add(new TranslationTextComponent("powerup.required.level",powerUp.levelType().toString(),powerUp.requiredLevel()).setStyle(redStyle));
      } else {
         Style style = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.BLUE)).setItalic(true);
         textBoxInfo.add(new StringTextComponent("???").setStyle(style));
         textBoxInfo.add(new StringTextComponent("???").setStyle(Style.EMPTY.setItalic(true)));
         textBoxInfo.add(new TranslationTextComponent("powerup.required.level",powerUp.levelType().toString(),powerUp.requiredLevel()).setStyle(redStyle));
      }
      
      GuiUtils.drawHoveringText(matrixStack, textBoxInfo, mouseX, mouseY, width, height, width, font);
      RenderSystem.popMatrix();
      if (flag) {
         this.fade = MathHelper.clamp(this.fade + 0.02F, 0.0F, 0.3F);
      } else {
         this.fade = MathHelper.clamp(this.fade - 0.04F, 0.0F, 1.0F);
      }

   }
   public void dragSelectedGui(double dragX, double dragY) {
      if (this.maxX - this.minX > 141) {
         this.scrollX = MathHelper.clamp(this.scrollX + dragX, (double)(-(this.maxX - 141)), 0.0D);
      }

      if (this.maxY - this.minY > 93) {
         this.scrollY = MathHelper.clamp(this.scrollY + dragY, (double)(-(this.maxY - 93)), 0.0D);
      }
   }

   public void drawTooltips(MatrixStack matrixStack, int mouseX, int mouseY, int width, int height,int xOffset, int yOffset) {
      if(xOffset < mouseX && mouseX < xOffset + 141 && yOffset < mouseY && mouseY < yOffset + 93){
         for(int i = 0; i < powerUps.length; i++){
            int x = 30 * (i % 4) + 13 + MathHelper.floor(this.scrollX) + xOffset;
            int y = 7 * i + MathHelper.floor(this.scrollY) + yOffset;
            if(levelDistance(powerUps[i]) < 7 &&x< mouseX && mouseX < x + 26 &&
               y < mouseY && mouseY < y + 26)
               drawTabTooltips(matrixStack, mouseX, mouseY, width, height,powerUps[i]);
         }
      }
   }

   private void displayPowerUps(MatrixStack matrixStack,int xOffset,int yOffset){
      for(int i = 0; i < powerUps.length; i++){
         int x = 30 * (i % 4) + 13;
         int y = 7 * i + 3;
         PowerUp powerUp = powerUps[i];
         int id = powerUp.iconType(getNbtData(powerUp.levelType()));
         if(levelDistance(powerUp) < 7){
            displayIcon(matrixStack, id, x + xOffset, y + yOffset);
            if(y > this.maxY - 30){
               this.maxY += 7;
            }
         }
         if(levelDistance(powerUp) < 3)
            blit(matrixStack, x + xOffset + 4, y + yOffset + 5, powerUp.uLocation, powerUp.vLocation, 16, 16);
         
      }
   }

   private void displayIcon(MatrixStack matrixStack,int id,int x,int y){
      //Too lazy to learn the delegate equivalent of JAVA this'll do
      switch(id){
         case 0:
            blit(matrixStack, x, y, 0, 178, 25, 25);
         break;
         case 1:
            blit(matrixStack, x - 1, y, 25, 178, 26, 26);
         break;
         case 2:
            blit(matrixStack, x, y, 52, 178, 26, 26);
         break;
         case 3:
            blit(matrixStack, x, y, 0, 204, 25, 25);
         break;
         case 4:
            blit(matrixStack, x - 1, y, 25, 204, 26, 26);
         break;
         case 5:
            blit(matrixStack, x, y, 52, 204, 26, 26);
         break;
      }
   }

   private int levelDistance(PowerUp powerUp){
      return powerUp.requiredLevel() - getNbtData(powerUp.levelType());
   }

   private int getNbtData(WolfStatsEnum statType){
      switch(statType){
         case Speed:
            return nbt.getInt("spdLevel");
         case Strength:
            return nbt.getInt("strLevel");
         case Intelligence:
            return nbt.getInt("intLevel");
         default:
            return Integer.MAX_VALUE;
      }
   }
}
