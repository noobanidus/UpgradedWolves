package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class UpgradedWolfRenderer extends WolfRenderer {

    public UpgradedWolfRenderer(EntityRendererManager p_i47187_1_) {
        super(p_i47187_1_);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ResourceLocation getEntityTexture(WolfEntity wolf){
        
        return UpgradedWolves.getId("textures/entity/pet_dog.png");
    }
  
}