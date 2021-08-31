package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
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

    public IWolfStats getCapability(WolfEntity wolf){
        return WolfStatsHandler.getHandler(wolf);
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
            if(wolf.getHeldItemMainhand().getItem() instanceof SwordItem){
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

    private <E extends Entity> void renderLeash(WolfEntity entityLivingIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, E leashHolder){
        matrixStackIn.push();
        Vector3d vector3d = leashHolder.getLeashPosition(partialTicks);
        double d0 = (double)(MathHelper.lerp(partialTicks, entityLivingIn.renderYawOffset, entityLivingIn.prevRenderYawOffset) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vector3d vector3d1 = entityLivingIn.func_241205_ce_();
        double d1 = Math.cos(d0) * vector3d1.z + Math.sin(d0) * vector3d1.x;
        double d2 = Math.sin(d0) * vector3d1.z - Math.cos(d0) * vector3d1.x;
        double d3 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosX, entityLivingIn.getPosX()) + d1;
        double d4 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosY, entityLivingIn.getPosY()) + vector3d1.y;
        double d5 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosZ, entityLivingIn.getPosZ()) + d2;
        matrixStackIn.translate(d1, vector3d1.y, d2);
        float f = (float)(vector3d.x - d3);
        float f1 = (float)(vector3d.y - d4);
        float f2 = (float)(vector3d.z - d5);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getLeash());
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = new BlockPos(entityLivingIn.getEyePosition(partialTicks));
        BlockPos blockpos1 = new BlockPos(leashHolder.getEyePosition(partialTicks));
        int i = this.getBlockLight(entityLivingIn, blockpos);
        int j = leashHolder.isBurning() ? 15 : leashHolder.world.getLightFor(LightType.BLOCK, blockpos);
        int k = entityLivingIn.world.getLightFor(LightType.SKY, blockpos);
        int l = entityLivingIn.world.getLightFor(LightType.SKY, blockpos1);
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.pop();
    }
}