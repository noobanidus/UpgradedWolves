package com.example.upgradedwolves.entities;

import java.util.ArrayList;

import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;

public class UpgradedWolfModel extends WolfModel<WolfEntity> {
    private final ModelRenderer backPack;
    public UpgradedWolfModel(){
        super();
        this.backPack = new ModelRenderer(this,43,21);
        this.backPack.addBox(-2.0F, -5.0F, -4.0F, 4.0F, 2.0F, 8.0F);
        this.backPack.setRotationPoint(0.0F, 14.0F, 2.0F);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts(){
        ArrayList<ModelRenderer> list = new ArrayList<ModelRenderer>();
        super.getBodyParts().forEach(list::add);
        list.add(this.backPack);
        return list;
    }

    @Override
    public void setLivingAnimations(WolfEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        if(entityIn.isEntitySleeping()){
            this.backPack.setRotationPoint(0.0F, 18.0F, 0.0F);
            this.backPack.rotateAngleX = -((float)Math.PI / 4F);
        } else{
            this.backPack.setRotationPoint(0.0F, 14.0F, 2.0F);
            this.backPack.rotateAngleX = 0;
        }

        this.backPack.rotateAngleZ = entityIn.getShakeAngle(partialTick, -0.16F);
    }
}