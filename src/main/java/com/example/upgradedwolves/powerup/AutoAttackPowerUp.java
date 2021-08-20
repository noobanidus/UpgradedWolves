package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.WolfAutoAttackTargetGoal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;

public class AutoAttackPowerUp extends PowerUp {

    public AutoAttackPowerUp(int levelRequirement,WolfEntity wolf){
        super(levelRequirement, UpgradedWolves.getId("powerup/auto_attack.json"),WolfAutoAttackTargetGoal.class);
    }

    @Override
    public void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number) {        
        
    }

    @Override
    protected Goal goalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf,MonsterEntity.class,false);
    }
    
}
