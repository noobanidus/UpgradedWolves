package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class UpgradedWolfRenderer extends WolfRenderer {

    public UpgradedWolfRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
        this.entityModel = new UpgradedWolfModel();
    }

    @Override
    public ResourceLocation getEntityTexture(WolfEntity wolf){
        if(!wolf.isTamed())
            return super.getEntityTexture(wolf);
        else{
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            switch(handler.getWolfType()){
                case 0:
                    return super.getEntityTexture(wolf);
                case 1:
                    return UpgradedWolves.getId("textures/entity/fighterwolf.png");
                case 2:
                    return UpgradedWolves.getId("textures/entity/scavengerwolf.png");
            }
            return UpgradedWolves.getId("textures/entity/pet_dog.png");
        }
    }

    @Override
    public void render(WolfEntity wolf, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {        
        super.render(wolf, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if(!wolf.getHeldItemMainhand().isEmpty()){
            ItemStack itemStack = wolf.getHeldItemMainhand();
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, -wolf.prevRenderYawOffset, -wolf.renderYawOffset)));
            matrixStackIn.translate(-1 * 0.0625, 0, 7 * 0.0625);
            matrixStackIn.rotate(Vector3f.YN.rotationDegrees(MathHelper.lerp(partialTicks, -wolf.prevRenderYawOffset, -wolf.renderYawOffset)));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, -wolf.prevRotationYawHead, -wolf.rotationYawHead)));
            matrixStackIn.translate(1 * 0.0625, 8.5 * 0.0625, 5.5 * 0.0625);
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(MathHelper.lerp(partialTicks, -wolf.prevRotationPitch, -wolf.rotationPitch)));
            matrixStackIn.translate(-1 * 0.0625, 0, 0);

            matrixStackIn.translate(0, 1.0 * 0.0625, 0);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(75F));
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);

            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90F));
            Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE,packedLightIn,OverlayTexture.NO_OVERLAY,matrixStackIn,bufferIn);
            matrixStackIn.pop();
        }
    }
  
}