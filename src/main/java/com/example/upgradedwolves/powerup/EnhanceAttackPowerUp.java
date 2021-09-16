package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;

public class EnhanceAttackPowerUp extends BonusStatPowerUp {

    public EnhanceAttackPowerUp(int levelRequirement, int effectiveLevel, double bonus) {
        super(levelRequirement, "enhance_attack", effectiveLevel);
        this.bonus = bonus;
    }

    @Override
    protected void enhanceAttribute(WolfEntity wolf, IWolfStats handler) {
        handler.addAttackBonus(bonus);
    }    
    
}
