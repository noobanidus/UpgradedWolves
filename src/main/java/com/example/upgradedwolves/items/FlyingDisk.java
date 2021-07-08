package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.FlyingDiskEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class FlyingDisk extends Item {

    static private final int TENNIS_BALL_STACK_SIZE = 8;

    public FlyingDisk() {
        super(new Item.Properties().maxStackSize(TENNIS_BALL_STACK_SIZE).group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId("flying_disk"));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {        
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        PlayerEntity playerIn = (PlayerEntity)entityLiving;
        int time = getUseDuration(stack) - timeLeft;
        float bonus = Math.max(0,Math.min(8,time/3)) / 10.0F;
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            FlyingDiskEntity flyingDiskEntity = new FlyingDiskEntity(worldIn, playerIn);
            flyingDiskEntity.setItem(stack);
            flyingDiskEntity.setShooter(playerIn);
            flyingDiskEntity.setNoGravity(true);
            flyingDiskEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.2F + bonus, 1.0F);
            worldIn.addEntity(flyingDiskEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
    }
}
