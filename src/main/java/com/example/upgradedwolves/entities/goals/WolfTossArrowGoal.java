package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;

import net.minecraft.entity.IAngerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Mth;

public class WolfTossArrowGoal extends CoolDownGoal {
    protected final Wolf wolf;
    protected final EntityFinder<Monster> entityFinder;
    protected Monster target;
    protected int arrowStackSlot;
    int windUpTicks = 10;


    public WolfTossArrowGoal(Wolf wolf){
        this.wolf = wolf;
        this.entityFinder = new EntityFinder<Monster>(wolf,Monster.class);
        setCoolDownInSeconds(300);
    }

    @Override
    public boolean canUse() {
        if(active() && wolf.getAttackTarget() != null){
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            int slot = handler.getInventory().getArbitraryItem(item -> item instanceof ArrowItem);
            if(slot >= 0){
                List<Monster> enemies = entityFinder.findWithPredicate(5, 3,enemy -> (!(enemy instanceof IAngerable) || ((IAngerable)enemy).getAttackTarget() != null) && wolf.getEntitySenses().canSee(enemy));
                if(enemies.size() > 0){
                    target = enemies.get(0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        wolf.getJumpController().setJumping();
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(windUpTicks-- >= 0){
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            ItemStack arrowStack = handler.getInventory().getStackInSlot(arrowStackSlot);
            if(arrowStack.getItem() instanceof ArrowItem)
                attackEntityWithRangedAttack(target, 2 + handler.getWolfStrength(), arrowStack);
            startCoolDown(AbilityEnhancer.minMaxIncrease(wolf, 200, 5, 150));
            return true;
        }
        windUpTicks = 30;
        wolf.rotationYaw += 1;
        return false;
    }

    private void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor,ItemStack arrowStack) {
        AbstractArrowEntity abstractarrowentity = ProjectileHelper.fireArrow(wolf, arrowStack, distanceFactor);
        double d0 = target.getX() - wolf.getX();
        double d1 = target.getPosYHeight(0.3333333333333333D) - abstractarrowentity.getY();
        double d2 = target.getZ() - wolf.getZ();
        double d3 = (double)Mth.sqrt(d0 * d0 + d2 * d2);
        abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - wolf.level.getDifficulty().getId() * 4));
        wolf.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (wolf.getRandom().nextFloat() * 0.4F + 0.8F));
        wolf.level.addEntity(abstractarrowentity);
     }
}
