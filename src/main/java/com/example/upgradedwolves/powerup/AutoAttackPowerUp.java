package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.entities.goals.WolfAutoAttackTargetGoal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Wolf;

public class AutoAttackPowerUp extends PowerUp {

    public AutoAttackPowerUp(int levelRequirement){
        super(levelRequirement, "auto_attack",WolfAutoAttackTargetGoal.class);
        this.active = false;
        this.uLocation = 102;
        this.vLocation = 182;
        this.statType = WolfStatsEnum.values()[1];
        this.defaultPriority = 4;
    }

    @Override
    protected Goal goalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf,Monster.class,false);
    }
    
}
