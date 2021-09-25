package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;

public class EnhanceDetectionPowerUp extends BonusStatPowerUp {

    public EnhanceDetectionPowerUp(int levelRequirement, int effectiveLevel, Double bonus) {
        super(levelRequirement, "enhance_detect", effectiveLevel);
        this.bonus = bonus;
    }

    @Override
    protected void enhanceAttribute(Wolf wolf, IWolfStats handler) {
        handler.addDetectionBonus(bonus);
    }   
    
}
