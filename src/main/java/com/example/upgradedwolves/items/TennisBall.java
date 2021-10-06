package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.TennisBallEntity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

public class TennisBall extends Item {

    static private final int TENNIS_BALL_STACK_SIZE = 8;

    public TennisBall() {
        super(new Item.Properties().stacksTo(TENNIS_BALL_STACK_SIZE).tab(CreativeModeTab.TAB_MISC));
        this.setRegistryName(UpgradedWolves.getId("tennis_ball"));
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
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, InteractionHand handIn) {        
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        Player playerIn = (Player)entityLiving;
        int time = getUseDuration(stack) - timeLeft;
        float bonus = Math.max(0,Math.min(15,time/3)) / 10.0F;
        worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            TennisBallEntity tennisBallEntity = new TennisBallEntity(worldIn, playerIn);
            tennisBallEntity.setItem(stack);
            tennisBallEntity.setShooter(playerIn);
            tennisBallEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.5F + bonus, 1.0F);
            worldIn.addFreshEntity(tennisBallEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
    }
}
