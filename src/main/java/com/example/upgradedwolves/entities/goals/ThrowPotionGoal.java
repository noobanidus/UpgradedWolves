package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3d;

public class ThrowPotionGoal extends Goal {
    protected final Wolf wolf;
    protected final IWolfStats handler;
    protected LivingEntity target;
    protected EntityFinder<LivingEntity> allyFinder;
    protected int slot;

    public ThrowPotionGoal(Wolf wolf){
        this.wolf = wolf;
        this.handler = WolfStatsHandler.getHandler(wolf);
        allyFinder = new EntityFinder<LivingEntity>(this.wolf,LivingEntity.class);
    }

    @Override
    public boolean canUse() {
        WolfItemStackHandler wolfItems = handler.getInventory();
        int bonus = AbilityEnhancer.increaseEveryLevel(wolf, 10, 3);
        List<LivingEntity> allyList = allyFinder.findWithPredicate(7+ bonus, 3 + bonus, ally ->
        (ally == wolf.getOwner() || (ally instanceof TameableEntity && ((TameableEntity)ally).getOwner() == wolf.getOwner()) &&
        (ally.isBurning() || ally.getHealth() < 8)));
        for (LivingEntity livingEntity : allyList) {
            int slot = -1;
            if(livingEntity.isBurning() && !livingEntity.isPotionActive(Effects.FIRE_RESISTANCE)){
                slot = burningQuestion(wolfItems,Effects.FIRE_RESISTANCE);
            }
            if(livingEntity.getHealth() < 8){
                slot = burningQuestion(wolfItems, Effects.INSTANT_HEALTH);
                if(slot < 0)
                    slot = burningQuestion(wolfItems, Effects.REGENERATION);
            }
            if(slot >= 0){
                this.slot = slot;
                target = livingEntity;
                break;
            }
        }
        return target != null;
    }

    @Override
    public void startExecuting() {        
        super.startExecuting();
        ItemStack potionStack = handler.getInventory().extractItem(slot, 1, false);
        WolfThrowPotion(potionStack);
        target = null;
    }

    //Gets a potion with a certain effect
    private int burningQuestion(WolfItemStackHandler wolfItems,Effect effectType){
        return wolfItems.getArbitraryItemStack(x -> x.getItem() instanceof ThrowablePotionItem && remainingWithEffect(PotionUtils.getEffectsFromStack(x),effectType));
    }

    private boolean remainingWithEffect(List<EffectInstance> effects, Effect effectType){
        effects.removeIf(y -> y.getPotion() != effectType);
        return effects.size() > 0;
    }

    private void WolfThrowPotion(ItemStack potionStack){
        Vector3d vector3d = target.getDeltaMovement();
        double d0 = target.getX() + vector3d.x - wolf.getX();
        double d1 = target.getPosYEye() - (double)1.1F - wolf.getY();
        double d2 = target.getZ() + vector3d.z - wolf.getZ();
        float f = Mth.sqrt(d0 * d0 + d2 * d2);

        PotionEntity potionentity = new PotionEntity(wolf.world, wolf);
        potionentity.setItem(potionStack);
        potionentity.rotationPitch -= -20.0F;
        potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
        if (!wolf.isSilent()) {
            wolf.world.playSound((Player)null, wolf.getX(), wolf.getY(), wolf.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, wolf.getSoundCategory(), 1.0F, 0.8F + wolf.getRNG().nextFloat() * 0.4F);
        }
        wolf.world.addEntity(potionentity);             
    }
    
}
