package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;

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
import net.minecraft.util.math.MathHelper;

public class WolfAutoAttackTargetGoal extends NearestAttackableTargetGoal<MonsterEntity> implements IUpdateableGoal {


    

    public WolfAutoAttackTargetGoal(MobEntity p_i50313_1_, Class<MonsterEntity> class1, boolean p_i50313_3_) {
        super(p_i50313_1_, class1, p_i50313_3_);        
        targetEntitySelector.setCustomPredicate(entity -> (EntityAllowed(entity)));
        IWolfStats handler = WolfStatsHandler.getHandler((WolfEntity)goalOwner);
        targetEntitySelector.setDistance(MathHelper.clamp(10 + handler.getDetectionBonus()/2, 0, 30) );
    }

    private boolean EntityAllowed(LivingEntity entity){
        IWolfStats handler = WolfStatsHandler.getHandler((WolfEntity)goalOwner);
        if(handler.getWolfType() != WolfType.Fighter.getValue())
            return false;
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

    @Override
    public void Update(IWolfStats handler, WolfEntity wolf) {        
        targetEntitySelector.setDistance(MathHelper.clamp(10 + handler.getDetectionBonus()/2, 0, 30) );
    }
    
}
