package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.world.server.ServerWorld;

public class FishForItemGoal extends CoolDownGoal {
    protected final Wolf wolf;
    protected List<ItemStack> itemStackList;
    
    public FishForItemGoal(Wolf wolf){
        this.wolf = wolf;
        setCoolDownInSeconds(60);
    }

    @Override
    public boolean canUse() {
        if(active() && wolf.isInWater()){
            ServerWorld world = (ServerWorld)wolf.world;
            LootContext.Builder lootcontext$builder = (new LootContext.Builder(world).withRandom(wolf.getRNG()).withParameter(LootParameters.field_237457_g_, wolf.getPositionVec()).withParameter(LootParameters.TOOL, new ItemStack(Items.FISHING_ROD)));
            LootTable loottable = wolf.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING);
            itemStackList = loottable.generate(lootcontext$builder.build(LootParameterSets.FISHING));
            return true;
        }
        return false;
    }
    
    @Override
    public void startExecuting() {        
        super.startExecuting();
        for (ItemStack itemStack : itemStackList) {
            wolf.spawnAtLocation(itemStack);
        }
        startCoolDown(AbilityEnhancer.minMaxIncrease(wolf, 40, 10, 70));
    }
}
