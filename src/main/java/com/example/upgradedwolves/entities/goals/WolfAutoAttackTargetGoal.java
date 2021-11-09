package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;

import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.util.Mth;

public class WolfAutoAttackTargetGoal extends NearestAttackableTargetGoal<Monster> implements IUpdateableGoal {


    

    public WolfAutoAttackTargetGoal(Mob p_i50313_1_, Class<Monster> class1, boolean p_i50313_3_) {
        super(p_i50313_1_, class1, p_i50313_3_);
        targetConditions.selector(entity -> (EntityAllowed(entity)));
        IWolfStats handler = WolfStatsHandler.getHandler((Wolf)mob);
        targetConditions.range(Mth.clamp(10 + handler.getDetectionBonus()/2, 0, 30) );
    }

    private boolean EntityAllowed(LivingEntity entity){
        IWolfStats handler = WolfStatsHandler.getHandler((Wolf)mob);
        if(handler.getWolfType() != WolfType.Fighter.getValue())
            return false;
        int intelligence = handler.getLevel(WolfStatsEnum.Intelligence);
        boolean basicMobs = entity instanceof Zombie || entity instanceof Spider;
        boolean hostileMobs = entity instanceof Monster && !(entity instanceof NeutralMob) &&
         !(entity instanceof Creeper) || entity instanceof Spider;        
        if(intelligence >= 5 && basicMobs)
            return true;
        else if(intelligence >= 10 && hostileMobs)
            return true;
        return false;
    }

    @Override
    public boolean canUse() {
        findTarget();
        // if(target != null)
        //     LogManager.getLogger().info(target);
        return super.canUse();
    }

    @Override
    public void Update(IWolfStats handler, Wolf wolf) {        
        targetConditions.range(Mth.clamp(10 + handler.getDetectionBonus()/2, 0, 30) );
    }
    
}
