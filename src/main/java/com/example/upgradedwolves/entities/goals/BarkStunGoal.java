package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;

public class BarkStunGoal extends CoolDownGoal {
    protected final EntityFinder<MonsterEntity> entityFinder;
    protected final WolfEntity wof;
    private List<MonsterEntity> enemies;
    private final Random rand;
    private int stunDuration = 50;

    public BarkStunGoal(WolfEntity wolf){
        this.wof = wolf;
        this.entityFinder = new EntityFinder<>(wof, MonsterEntity.class);
        setCoolDownInSeconds(20);
        rand = new Random();
    }

    @Override
    public boolean shouldExecute() {
        if(active()){
            List<MonsterEntity> entityList = entityFinder.findWithPredicate(5, 2, enemy -> !(enemy instanceof IAngerable) || ((IAngerable)enemy).getAttackTarget() != null);
            if(entityList.size() > 0){
                enemies = entityList;
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        wof.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 20, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.02F + .7F);
        IWolfStats handler = WolfStatsHandler.getHandler(wof);
        for (MonsterEntity mobEntity : enemies) {
            Random r = new Random();
            mobEntity.setNoAI(true);
            for(int i = 0; i < 10; i++)
                Minecraft.getInstance().world.addParticle(ParticleTypes.FLAME, false, mobEntity.getPosition().getX() + r.nextDouble(), mobEntity.getPosition().getY() + r.nextDouble(), mobEntity.getPosition().getZ() + r.nextDouble(), r.nextDouble()/5, r.nextDouble()/5, r.nextDouble()/5);
        }
        startCoolDown();
    }
    
    @Override
    public boolean shouldContinueExecuting() {
        if(stunDuration-- <= 0){
            for (MonsterEntity mobEntity : enemies) {
                mobEntity.setNoAI(false);
            }
            stunDuration = 50;
            return false;
        }
        return true;
    }
}
