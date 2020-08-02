package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class UpgradedWolfRenderer extends WolfRenderer {

    public UpgradedWolfRenderer(EntityRendererManager p_i47187_1_) {
        super(p_i47187_1_);
        // TODO Auto-generated constructor stub
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
  
}