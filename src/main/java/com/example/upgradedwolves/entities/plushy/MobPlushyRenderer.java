package com.example.upgradedwolves.entities.plushy;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.MobPlushy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MobPlushyRenderer extends EntityRenderer<MobPlushyEntity> {
    protected EntityModel model;  

    public MobPlushyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public static int getPackedOverlay(MobPlushyEntity livingEntityIn, float uIn) {
        return OverlayTexture.getPackedUV(OverlayTexture.getU(uIn), OverlayTexture.getV(false));
     }

    protected float getOverlayProgress(MobPlushyEntity livingEntityIn, float partialTicks) {
        return 0.0F;
     }

    @Override
    public void render(MobPlushyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        if(true){
            MobPlushy plush = (MobPlushy)entityIn.getItem().getItem();
            switch(plush.plushType){
                case ZOMBIE:
                model = new ZombiePlushyModel();
                break;
                case SKELETON:
                model = new SkeletonPlushyModel();
                break;
                case CREEPER:
                model = new CreeperPlushyModel();
                break;
            }
        }
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
        switch(plush.plushType){
            case SKELETON:            
            return UpgradedWolves.getId("textures/entity/skeleton_plush.png");
            case ZOMBIE:         
            return UpgradedWolves.getId("textures/entity/zombie_plush.png");
            case CREEPER:        
            return UpgradedWolves.getId("textures/entity/creeper_plush.png");
        }
        return null;
    }
    
    
}
