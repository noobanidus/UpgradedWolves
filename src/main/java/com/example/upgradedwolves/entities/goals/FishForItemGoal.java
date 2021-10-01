package com.example.upgradedwolves.entities.goals;

import java.util.List;

import com.example.upgradedwolves.entities.utilities.AbilityEnhancer;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.server.level.ServerLevel;

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
            ServerLevel world = (ServerLevel)wolf.level;
            LootContext.Builder lootcontext$builder = (new LootContext.Builder(world).withRandom(wolf.getRandom()).withParameter(LootContextParams.ORIGIN, wolf.getPosition(1)).withParameter(LootContextParams.TOOL, new ItemStack(Items.FISHING_ROD)));
            LootTable loottable = wolf.level.getServer().getLootTables().get(BuiltInLootTables.FISHING);
            itemStackList = loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.FISHING));
            return true;
        }
        return false;
    }
    
    @Override
    public void start() {        
        super.start();
        for (ItemStack itemStack : itemStackList) {
            wolf.spawnAtLocation(itemStack);
        }
        startCoolDown(AbilityEnhancer.minMaxIncrease(wolf, 40, 10, 70));
    }
}
