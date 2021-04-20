package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.WolfEntity;

public class WolfAutoAttackTargetGoal extends NearestAttackableTargetGoal<MonsterEntity> {


    

    public WolfAutoAttackTargetGoal(MobEntity p_i50313_1_, Class<MonsterEntity> class1, boolean p_i50313_3_) {
        super(p_i50313_1_, class1, p_i50313_3_);        
        targetEntitySelector.setCustomPredicate(entity -> (EntityAllowed(entity)));
    }

    private boolean EntityAllowed(LivingEntity entity){
        IWolfStats handler = WolfStatsHandler.getHandler((WolfEntity)goalOwner);
        int intelligence = handler.getLevel(WolfStatsEnum.Intelligence);
        boolean basicMobs = entity instanceof ZombieEntity || entity instanceof SpiderEntity;
        boolean hostileMobs = entity instanceof MonsterEntity && !(entity instanceof IAngerable) &&
         !(entity instanceof CreeperEntity) || entity instanceof SpiderEntity;        
        if(intelligence >= 5 && basicMobs)
            return true;
        else if(intelligence >= 10 && hostileMobs)
            return true;
        return false;
    }

    @Override
    public boolean shouldExecute() {
        findNearestTarget();
        if(target != null)
            LogManager.getLogger().info(target);
        return super.shouldExecute();
    }
    
}
