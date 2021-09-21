package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class DetectEnemiesGoal extends Goal implements IUpdateableGoal {

    int currentTicks = 0;
    int attemptTicks = 300;
    int coolDownTicks = 3600;
    int lookAt = 100;
    double range;
    boolean coolDown = false;
    WolfEntity wolf;
    MonsterEntity detectedEntity;
    EntityFinder<MonsterEntity> entityFinder;

    public DetectEnemiesGoal(WolfEntity wolf){
        this.wolf = wolf;
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        this.range = 5 + handler.getDetectionBonus();
        entityFinder = new EntityFinder<MonsterEntity>(wolf,MonsterEntity.class);
    }

    @Override
    public boolean shouldExecute() {
        if(coolDown)
            if(currentTicks++ < coolDownTicks)
                return false;
            else{
                coolDown = false;
                currentTicks = 0;
                LogManager.getLogger().debug("Cool down has ended");

            }
        if(currentTicks++ < attemptTicks)
            return false;
        List<MonsterEntity> foundEntities = entityFinder.findWithPredicate(range, 0, x -> wolf.getEntitySenses().canSee(x));
        foundEntities.addAll(entityFinder.findWithinRange(range/5, 0));
        for(MonsterEntity monsterEntity : foundEntities) {
            monsterEntity.addPotionEffect(new EffectInstance(Effect.get(24),120 + (10 * AbilityEnhancer.detectionSkill(wolf))));
            this.wolf.getLookController().setLookPosition(monsterEntity.getPositionVec());
            detectedEntity = monsterEntity;
            currentTicks = 0;
            coolDown = true;
            LogManager.getLogger().debug("I have found an enemy. Going on coolDown");
            return true;            
        }            
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {        
        if(currentTicks++ < lookAt)
            return true;
        currentTicks = 0;
        return false;
    }
    

    @Override
    public void tick(){
        this.wolf.getLookController().setLookPosition(detectedEntity.getEyePosition(1.0F));
    }

    @Override
    public void Update(IWolfStats handler, WolfEntity wolf) {
        range = 5 + handler.getDetectionBonus();
    }
}
