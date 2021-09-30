package com.example.upgradedwolves.entities.plushy;

import com.example.upgradedwolves.items.MobPlushy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class MobPlushyRenderer extends EntityRenderer<MobPlushyEntity> {
    protected EntityModel<Entity> model;  

    public MobPlushyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    public static int getPackedOverlay(MobPlushyEntity livingEntityIn, float uIn) {
        return OverlayTexture.getPackedUV(OverlayTexture.getU(uIn), OverlayTexture.getV(false));
     }

    protected float getOverlayProgress(MobPlushyEntity livingEntityIn, float partialTicks) {
        return 0.0F;
     }

    @Override
    public void render(MobPlushyEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {        
        MobPlushy plush = (MobPlushy)entityIn.getItem().getItem();
        model = MobPlushy.getModelByPlushType(plush);        
        matrixStackIn.push();
        int i = OverlayTexture.NO_OVERLAY;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(getEntityTexture(entityIn)));
        model.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1f, 1f, 1f, 1f);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public boolean shouldRender(MobPlushyEntity livingEntityIn, ClippingHelper camera, double camX, double camY,
            double camZ) {
        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }

    @Override
    public ResourceLocation getEntityTexture(MobPlushyEntity entity) {
        MobPlushy plush = (MobPlushy)entity.getItem().getItem();
        return MobPlushy.getPlushTexture(plush);
    }
    
    
}
