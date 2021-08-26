package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.entity.IAngerable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;

public class BarkStunGoal extends CoolDownGoal {
    protected final EntityFinder<MobEntity> entityFinder;
    protected final WolfEntity wof;
    private List<MobEntity> enemies;
    private final Random rand;

    public BarkStunGoal(WolfEntity wolf){
        this.wof = wolf;
        this.entityFinder = new EntityFinder<>(wof, MobEntity.class);
        setCoolDownInSeconds(600);
        rand = new Random();
    }

    @Override
    public boolean shouldExecute() {
        if(active()){
            List<MobEntity> entityList = entityFinder.findWithPredicate(5, 2, enemy -> !(enemy instanceof IAngerable) || ((IAngerable)enemy).getAttackTarget() != null);
            if(entityList.size() > 0){
                enemies = entityList;
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        wof.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 3, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        for (MobEntity mobEntity : enemies) {
            mobEntity.attackEntityFrom(DamageSource.causeMobDamage(wof), 0);            
        }
    }
    
}
