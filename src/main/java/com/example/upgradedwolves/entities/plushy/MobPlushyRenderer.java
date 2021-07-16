package com.example.upgradedwolves.entities.plushy;

import java.util.ArrayList;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.MobPlushy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MobPlushyRenderer extends EntityRenderer<MobPlushyEntity> {
    protected ModelRenderer head;
    protected ModelRenderer body;
    protected ArrayList<ModelRenderer> limbs;    

    public MobPlushyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        MobPlushyEntity plushEntity = (MobPlushyEntity)renderManager.pointedEntity;
        this.head = new ModelRenderer(32, 32, 0, 0);
        this.head.addBox(0F, 0.0F, 0F, 4.0F, 4.0F, 4.0F);
        if(plushEntity != null){
            MobPlushy plush = (MobPlushy)plushEntity.getItem().getItem();
            
            switch(plush.plushType){
                case SKELETON:
                BipedalModel();
                break;
                case ZOMBIE:
                BipedalModel();
                break;
                case CREEPER:
                CreeperModel();
                break;
            }
        }
    }

    protected void BipedalModel(){

    }

    protected void CreeperModel(){

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
        matrixStackIn.push();
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);
        int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(getEntityTexture(entityIn)));
        head.render(matrixStackIn, ivertexbuilder, packedLightIn, i);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public boolean shouldRender(MobPlushyEntity livingEntityIn, ClippingHelper camera, double camX, double camY,
            double camZ) {
        // TODO Auto-generated method stub
        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }

    @Override
    public ResourceLocation getEntityTexture(MobPlushyEntity entity) {
        // MobPlushy plush = (MobPlushy)entity.getItem().getItem();
        // switch(plush.plushType){
        //     case SKELETON:            
        //     break;
        //     case ZOMBIE:            
        //     break;
        //     case CREEPER:        
        //     break;
        // }
        return UpgradedWolves.getId("pet_dog");
    }
    
    
}
