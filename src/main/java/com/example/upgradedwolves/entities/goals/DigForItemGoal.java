package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;
import com.example.upgradedwolves.loot_table.LootLoaders;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;

public class DigForItemGoal extends CoolDownGoal {
    public Wolf wolf;
    protected BlockState type;
    protected final int timer = 40;
    protected int currentTime;
    protected ItemStack itemToDrop;

    public DigForItemGoal (Wolf wolf){
        this.wolf = wolf;
        setCoolDownInSeconds(1800);
        currentTime = 0;
    }

    @Override
    public boolean canUse() {
        //Gets block "type" at the position below the wolf
        BlockState blockStandingOn = wolf.level.getBlockState(new BlockPos(wolf.getPosition(1).add(0, -1, 0)));   
        if(active() && !wolf.isInSittingPose() && (isGrassBlock(blockStandingOn) || isSandBlock(blockStandingOn))){
            type = blockStandingOn;
            wolf.getNavigation().stop();
            if(isSandBlock(blockStandingOn)){
                itemToDrop = LootLoaders.DigSand.getRandomItem();
            } else if(isGrassBlock(blockStandingOn)){
                itemToDrop = LootLoaders.DigGrass.getRandomItem();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if(currentTime++ < timer){
            wolf.playSound(type.getBlock().getSoundType(null, null, null, null).getPlaceSound(), 0.5F, (1.0F + (wolf.getRandom().nextFloat() - wolf.getRandom().nextFloat()) * 0.2F) * 0.7F);            
            Minecraft mc = Minecraft.getInstance();
            if(mc.level != null)
                mc.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, type),false, wolf.getX(), wolf.getY(), wolf.getZ(),wolf.getRandom().nextDouble()/5, wolf.getRandom().nextDouble()/5, wolf.getRandom().nextDouble()/5);
            return true;
        }
        currentTime = 0;
        wolf.spawnAtLocation(itemToDrop);
        startCoolDown(AbilityEnhancer.increaseMin(wolf, 10) * 10);
        return false;
    }
    
    private boolean isGrassBlock(BlockState blockStateIn){
        return blockStateIn.is(Blocks.GRASS_BLOCK) || net.minecraftforge.common.Tags.Blocks.DIRT.contains(blockStateIn.getBlock());
    }

    private boolean isSandBlock(BlockState blockStateIn){
        return blockStateIn.is(Blocks.SAND) ||blockStateIn.is(Blocks.RED_SAND);
    }
}
