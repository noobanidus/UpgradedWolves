package com.example.upgradedwolves.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class UpgradedWolfRenderer extends WolfRenderer {    
    protected List<ResourceLocation> showWolfTextures = Util.make(new ArrayList<ResourceLocation>(), (textureList) -> {
        textureList.add(UpgradedWolves.getId("textures/entity/silver_wolf.png"));
        textureList.add(UpgradedWolves.getId("textures/entity/dark_wolf.png"));
        textureList.add(UpgradedWolves.getId("textures/entity/spotted_wolf.png"));
    });


    public UpgradedWolfRenderer(EntityRendererProvider.Context rendererManager) {
        super(rendererManager);
        this.entityModel = new UpgradedWolfModel();
        
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf){
        if(!wolf.isTamed())
            return super.getTextureLocation(wolf);
        else{
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            switch(handler.getWolfType()){
                case 0:
                    return super.getTextureLocation(wolf);
                case 1:
                    return UpgradedWolves.getId("textures/entity/fighterwolf.png");
                case 2:
                    return UpgradedWolves.getId("textures/entity/scavengerwolf.png");
                case 3:                    
                    if(wolf.hasCustomName() && wolf.getCustomName().getString().equals("Strobe"))
                        return showWolfTextures.get(wolf.getRandom().nextInt(3));
                    return showWolfTextures.get(handler.getWolfFur());
                    
            }
            return UpgradedWolves.getId("textures/entity/pet_dog.png");
        }
    }

    public IWolfStats getCapability(Wolf wolf){
        return WolfStatsHandler.getHandler(wolf);
    }

    @Override
    public void render(Wolf wolf, float entityYaw, float partialTicks, PoseStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn) {        
        super.render(wolf, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if(!wolf.getMainHandItem().isEmpty()){
            ItemStack itemStack = wolf.getMainHandItem();
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, -wolf.prevRenderYawOffset, -wolf.renderYawOffset)));
            matrixStackIn.translate(-1 * 0.0625, 0, 7 * 0.0625);
            matrixStackIn.rotate(Vector3f.YN.rotationDegrees(Mth.lerp(partialTicks, -wolf.prevRenderYawOffset, -wolf.renderYawOffset)));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, -wolf.prevRotationYawHead, -wolf.rotationYawHead)));
            matrixStackIn.translate(1 * 0.0625, 8.5 * 0.0625, 5.5 * 0.0625);
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(Mth.lerp(partialTicks, -wolf.prevRotationPitch, -wolf.rotationPitch)));
            matrixStackIn.translate(-1 * 0.0625, 0, 0);

            matrixStackIn.translate(0, 1.0 * 0.0625, 0);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(75F));
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);

            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90F));        
            if(wolf.getMainHandItem().getItem() instanceof SwordItem){
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(45F));
                matrixStackIn.translate(6 * 0.0625, 6 * .0625, 0);
            }
            Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE,packedLightIn,OverlayTexture.NO_OVERLAY,matrixStackIn,bufferIn);
            matrixStackIn.pop();
        }
        Entity tugRopeHolder = getCapability(wolf).getRopeHolder();
        if(tugRopeHolder != null)
            renderLeash(wolf, partialTicks, matrixStackIn, bufferIn, tugRopeHolder);
    }

    private <E extends Entity> void renderLeash(Wolf entityLivingIn, float partialTicks, PoseStack matrixStackIn, IRenderTypeBuffer bufferIn, E leashHolder){
        matrixStackIn.push();
        Vector3d vector3d = leashHolder.getLeashPosition(partialTicks);
        double d0 = (double)(Mth.lerp(partialTicks, entityLivingIn.renderYawOffset, entityLivingIn.prevRenderYawOffset) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vector3d vector3d1 = entityLivingIn.func_241205_ce_();
        double d1 = Math.cos(d0) * vector3d1.z + Math.sin(d0) * vector3d1.x;
        double d2 = Math.sin(d0) * vector3d1.z - Math.cos(d0) * vector3d1.x;
        double d3 = Mth.lerp((double)partialTicks, entityLivingIn.prevPosX, entityLivingIn.getX()) + d1;
        double d4 = Mth.lerp((double)partialTicks, entityLivingIn.prevPosY, entityLivingIn.getY()) + vector3d1.y;
        double d5 = Mth.lerp((double)partialTicks, entityLivingIn.prevPosZ, entityLivingIn.getZ()) + d2;
        matrixStackIn.translate(d1, vector3d1.y, d2);
        float f = (float)(vector3d.x - d3);
        float f1 = (float)(vector3d.y - d4);
        float f2 = (float)(vector3d.z - d5);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getLeash());
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f4 = Mth.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        Vec3 blockpos = new Vec3(entityLivingIn.getEyePosition(partialTicks));
        Vec3 blockpos1 = new Vec3(leashHolder.getEyePosition(partialTicks));
        int i = this.getBlockLight(entityLivingIn, blockpos);
        int j = leashHolder.isOnFire() ? 15 : leashHolder.world.getLightFor(LightType.BLOCK, blockpos);
        int k = entityLivingIn.world.getLightFor(LightType.SKY, blockpos);
        int l = entityLivingIn.world.getLightFor(LightType.SKY, blockpos1);
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.pop();
    }
}