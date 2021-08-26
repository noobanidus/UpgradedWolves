package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;

public class EnhanceDetectionPowerUp extends BonusStatPowerUp {

    public EnhanceDetectionPowerUp(int levelRequirement, int effectiveLevel, Double bonus) {
        super(levelRequirement, UpgradedWolves.getId("powerups/enhance_detect.json"), effectiveLevel);
        this.bonus = bonus;
    }

    @Override
    protected void enhanceAttribute(WolfEntity wolf, IWolfStats handler) {
        handler.addDetectionBonus(bonus);
    }   
    
}
