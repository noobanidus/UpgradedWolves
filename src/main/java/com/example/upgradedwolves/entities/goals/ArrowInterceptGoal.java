package com.example.upgradedwolves.entities.goals;

import java.util.List;
import java.util.Random;

import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class ArrowInterceptGoal extends CoolDownGoal {
    protected EntityFinder<AbstractArrowEntity> entityFinder;
    protected WolfEntity wolf;
    private AbstractArrowEntity arrow;
    private final Random rand;

    public ArrowInterceptGoal(WolfEntity wolf){
        this.wolf = wolf;
        entityFinder = new EntityFinder<AbstractArrowEntity>(wolf,AbstractArrowEntity.class);
        setCoolDownInSeconds(3600);
        rand = new Random();
    }

    @Override
    public boolean shouldExecute() {
        if(active()){
            List<AbstractArrowEntity> arrows = entityFinder.findWithPredicate(3, 2, arrow -> arrow.func_234616_v_() instanceof MobEntity);
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
            wolf.entityDropItem(new ItemStack(Items.ARROW));
        }
        teleportToLocation(arrow.getPosX(), arrow.getPosY(), arrow.getPosZ());
    }

    private void teleportToLocation(double x, double y, double z) {        
        this.wolf.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, this.wolf.rotationYaw, this.wolf.rotationPitch);
        wolf.getNavigator().clearPath();                
    }
    
    private boolean randomChancePercentage(float chance){
        float next = MathHelper.nextFloat(rand, 0, 100);
        return next < chance;
    }
}
