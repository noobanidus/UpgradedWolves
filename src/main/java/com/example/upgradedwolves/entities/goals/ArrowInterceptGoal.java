package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class ArrowInterceptGoal extends CoolDownGoal {
    protected EntityFinder<AbstractArrow> entityFinder;
    protected Wolf wolf;
    private AbstractArrow arrow;
    private final RandomSource rand;

    public ArrowInterceptGoal(Wolf wolf){
        this.wolf = wolf;
        entityFinder = new EntityFinder<AbstractArrow>(wolf,AbstractArrow.class);
        setCoolDownInSeconds(600);
        rand = RandomSource.create();
    }

    @Override
    public boolean canUse() {
        int bonus = AbilityEnhancer.increaseEveryLevel(wolf, 20, 3);
        if(active()){
            List<AbstractArrow> arrows = entityFinder.findWithPredicate(1 + bonus, 1 + bonus, arrow -> arrow.getOwner() instanceof Mob && arrow.getDeltaMovement().length() > 0);
            if(arrows.size() > 0){
                this.arrow = arrows.get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        startCoolDown();
        if(randomChancePercentage(50)){
            wolf.hurt(DamageSource.arrow(arrow, arrow.getOwner()) , (float)arrow.getBaseDamage());
        }
        if(randomChancePercentage(25)){
            wolf.spawnAtLocation(new ItemStack(Items.ARROW));
        }
        teleportToLocation(arrow.getX(), arrow.getY(), arrow.getZ());
        arrow.kill();
    }

    private void teleportToLocation(double x, double y, double z) {       
        this.wolf.lerpTo((double)x + 0.5D, (double)y, (double)z + 0.5D, this.wolf.getYRot(), this.wolf.getXRot(),1,false);
        wolf.getNavigation().stop();                
    }
    
    private boolean randomChancePercentage(float chance){
        float next = Mth.nextFloat(rand, 0, 100);
        return next < chance;
    }
}
