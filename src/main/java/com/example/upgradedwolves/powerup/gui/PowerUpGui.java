package com.example.upgradedwolves.powerup.gui;

import com.example.upgradedwolves.containers.WolfScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class PowerUpGui extends AbstractGui {
    Minecraft minecraft;
    private double scrollX;
    private double scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private boolean centered;
    private float fade;
    
    public void drawTabBackground(MatrixStack matrixStack) {
        if (!this.centered) {
           this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
           this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
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
        fill(matrixStack, 141, 93, 0, 0, -255);
        RenderSystem.depthFunc(515);
  
        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;
  
        for(int i1 = -1; i1 <= 15; ++i1) {
           for(int j1 = -1; j1 <= 8; ++j1) {
              blit(matrixStack, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
           }
        }
  
        
        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
     }
  
     public void drawTabTooltips(MatrixStack matrixStack, int mouseX, int mouseY, int width, int height) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 200.0F);
        fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0F) << 24);
        boolean flag = false;
        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
  
        RenderSystem.popMatrix();
        if (flag) {
           this.fade = MathHelper.clamp(this.fade + 0.02F, 0.0F, 0.3F);
        } else {
           this.fade = MathHelper.clamp(this.fade - 0.04F, 0.0F, 1.0F);
        }
  
     }
    public void dragSelectedGui(double dragX, double dragY) {
        if (this.maxX - this.minX > 234) {
            this.scrollX = MathHelper.clamp(this.scrollX + dragX, (double)(-(this.maxX - 234)), 0.0D);
        }

        if (this.maxY - this.minY > 113) {
            this.scrollY = MathHelper.clamp(this.scrollY + dragY, (double)(-(this.maxY - 113)), 0.0D);
        }
    }

}
