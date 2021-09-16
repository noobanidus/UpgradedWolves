package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.loot_table.LootLoaders;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;

public class DigForItemGoal extends CoolDownGoal {
    public WolfEntity wolf;
    protected BlockState type;
    protected final int timer = 40;
    protected int currentTime;
    protected ItemStack itemToDrop;

    public DigForItemGoal (WolfEntity wolf){
        this.wolf = wolf;
        setCoolDownInSeconds(1800);
        currentTime = 0;
    }

    @Override
    public boolean shouldExecute() {
        //Gets block "type" at the position below the wolf
        BlockState blockStandingOn = wolf.world.getBlockState(wolf.getPosition().add(0, -1, 0));   
        if(active() && !wolf.isSitting() && (isGrassBlock(blockStandingOn) || isSandBlock(blockStandingOn))){
            type = blockStandingOn;
            wolf.getNavigator().clearPath();
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
    public boolean shouldContinueExecuting() {
        if(currentTime++ < timer){
            wolf.playSound(type.getBlock().getSoundType(null, null, null, null).getPlaceSound(), 0.5F, (1.0F + (wolf.getRNG().nextFloat() - wolf.getRNG().nextFloat()) * 0.2F) * 0.7F);            
            Minecraft mc = Minecraft.getInstance();
            if(mc.world != null)
                mc.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, type),false, wolf.getPosX(), wolf.getPosY(), wolf.getPosZ(),wolf.getRNG().nextDouble()/5, wolf.getRNG().nextDouble()/5, wolf.getRNG().nextDouble()/5);
            return true;
        }
        currentTime = 0;
        wolf.entityDropItem(itemToDrop);
        startCoolDown();
        return false;
    }
    
    private boolean isGrassBlock(BlockState blockStateIn){
        return blockStateIn.isIn(Blocks.GRASS_BLOCK) || net.minecraftforge.common.Tags.Blocks.DIRT.contains(blockStateIn.getBlock());
    }

    private boolean isSandBlock(BlockState blockStateIn){
        return blockStateIn.isIn(Blocks.SAND) ||blockStateIn.isIn(Blocks.RED_SAND);
    }
}
