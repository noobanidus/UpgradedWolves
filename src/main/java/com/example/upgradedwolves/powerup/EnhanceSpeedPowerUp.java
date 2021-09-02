package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;

public class EnhanceSpeedPowerUp extends BonusStatPowerUp {


    public EnhanceSpeedPowerUp(int levelRequirement, int effectiveLevel,Double bonus) {
        super(levelRequirement, "enhance_speed", effectiveLevel);
        this.bonus = bonus;
    }

    @Override
    protected void enhanceAttribute(WolfEntity wolf, IWolfStats handler) {
        handler.addSpeedBonus(bonus);
    }   
    
}
