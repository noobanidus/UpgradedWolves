package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;

public class EnhanceSpeedPowerUp extends BonusStatPowerUp {


    public EnhanceSpeedPowerUp(int levelRequirement, int effectiveLevel,Double bonus) {
        super(levelRequirement, "enhance_speed", effectiveLevel);
        this.bonus = bonus;
    }

    @Override
    protected void enhanceAttribute(Wolf wolf, IWolfStats handler) {
        handler.addSpeedBonus(bonus);
    }   
    
}
