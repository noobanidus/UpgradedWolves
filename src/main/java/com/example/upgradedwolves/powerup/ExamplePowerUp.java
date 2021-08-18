package com.example.upgradedwolves.powerup;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class ExamplePowerUp extends PowerUp{

    public ExamplePowerUp(int levelRequirement) {
        super(levelRequirement, UpgradedWolves.getId("powerups/example_powerup.json"));        
    }

    @Override
    public void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number) {
                
    }
    
}
