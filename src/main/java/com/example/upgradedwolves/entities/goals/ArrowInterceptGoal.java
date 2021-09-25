package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Mth;

public class ArrowInterceptGoal extends CoolDownGoal {
    protected EntityFinder<AbstractArrowEntity> entityFinder;
    protected Wolf wolf;
    private AbstractArrowEntity arrow;
    private final Random rand;

    public ArrowInterceptGoal(Wolf wolf){
        this.wolf = wolf;
        entityFinder = new EntityFinder<AbstractArrowEntity>(wolf,AbstractArrowEntity.class);
        setCoolDownInSeconds(600);
        rand = new Random();
    }

    @Override
    public boolean canUse() {
        int bonus = AbilityEnhancer.increaseEveryLevel(wolf, 20, 3);
        if(active()){
            List<AbstractArrowEntity> arrows = entityFinder.findWithPredicate(1 + bonus, 1 + bonus, arrow -> arrow.func_234616_v_() instanceof Mob && arrow.getDeltaMovement().length() > 0);
            if(arrows.size() > 0){
                this.arrow = arrows.get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        startCoolDown();
        if(randomChancePercentage(50)){
            wolf.attackEntityFrom(DamageSource.causeArrowDamage(arrow, arrow.func_234616_v_()) , (float)arrow.getDamage());
        }
        if(randomChancePercentage(25)){
            wolf.spawnAtLocation(new ItemStack(Items.ARROW));
        }
        teleportToLocation(arrow.getX(), arrow.getY(), arrow.getZ());
        arrow.remove();
    }

    private void teleportToLocation(double x, double y, double z) {        
        this.wolf.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, this.wolf.rotationYaw, this.wolf.rotationPitch);
        wolf.getNavigation().clearPath();                
    }
    
    private boolean randomChancePercentage(float chance){
        float next = Mth.nextFloat(rand, 0, 100);
        return next < chance;
    }
}
