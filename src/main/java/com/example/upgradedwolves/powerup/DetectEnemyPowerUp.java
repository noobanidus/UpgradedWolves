package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.DetectEnemiesGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

public class DetectEnemyPowerUp extends PowerUp {

    public DetectEnemyPowerUp(int levelRequirement,WolfEntity wolf) {
        super(levelRequirement, UpgradedWolves.getId("powerup/detect_enemy.json"),DetectEnemiesGoal.class);
    }

    @Override
    public void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {        
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf,5D);
    }
    
}
