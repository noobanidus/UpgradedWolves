package com.example.upgradedwolves.entities.goals;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import com.example.upgradedwolves.entities.utilities.EntityFinder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.server.ServerWorld;

public class ImpressVillagerGoal extends CoolDownGoal {
    public final WolfEntity wolf;
    public final EntityFinder<AbstractVillagerEntity> entityFinder;
    public AbstractVillagerEntity target;
    int jumpTime;
    private static final Map<VillagerProfession, ResourceLocation> GIFTS = Util.make(Maps.newHashMap(), (giftMap) -> {
        giftMap.put(VillagerProfession.ARMORER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_ARMORER_GIFT);
        giftMap.put(VillagerProfession.BUTCHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_BUTCHER_GIFT);
        giftMap.put(VillagerProfession.CARTOGRAPHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT);
        giftMap.put(VillagerProfession.CLERIC, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT);
        giftMap.put(VillagerProfession.FARMER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT);
        giftMap.put(VillagerProfession.FISHERMAN, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FISHERMAN_GIFT);
        giftMap.put(VillagerProfession.FLETCHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FLETCHER_GIFT);
        giftMap.put(VillagerProfession.LEATHERWORKER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT);
        giftMap.put(VillagerProfession.LIBRARIAN, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT);
        giftMap.put(VillagerProfession.MASON, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_MASON_GIFT);
        giftMap.put(VillagerProfession.SHEPHERD, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_SHEPHERD_GIFT);
        giftMap.put(VillagerProfession.TOOLSMITH, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT);
        giftMap.put(VillagerProfession.WEAPONSMITH, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT);
     });

    public ImpressVillagerGoal(WolfEntity wolf){
        this.wolf = wolf;
        entityFinder = new EntityFinder<AbstractVillagerEntity>(wolf,AbstractVillagerEntity.class);
        setCoolDownInSeconds(1800);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE,Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if(active()){
            List<AbstractVillagerEntity> villagers = entityFinder.findWithinRange(5, 5);
            if(villagers.size() > 0){
                target = villagers.get(0);
                wolf.getNavigator().tryMoveToEntityLiving(target, 1);
                jumpTime = wolf.getRNG().nextInt(140) + 60;
                startCoolDown();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(jumpTime-- > 0){
            wolf.getJumpController().setJumping();;
            return true;
        }
        giveItemToWolf();
        target = null;
        return false; 
    }

    private void giveItemToWolf(){
        List<ItemStack> stack = getItem(target);
        for (ItemStack itemStack : stack) {
            wolf.entityDropItem(itemStack);
        }
    }

    private List<ItemStack> getItem(AbstractVillagerEntity villagerEntity){
        if (villagerEntity.isChild()) {
            return ImmutableList.of(new ItemStack(Items.POPPY));
        } else {
            if(villagerEntity instanceof VillagerEntity){
                VillagerEntity villager = (VillagerEntity)villagerEntity;
                VillagerProfession villagerprofession = villager.getVillagerData().getProfession();
                if (GIFTS.containsKey(villagerprofession)) {
                    LootTable loottable = villager.world.getServer().getLootTableManager().getLootTableFromLocation(GIFTS.get(villagerprofession));
                    LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)villager.world)).withParameter(LootParameters.field_237457_g_, villager.getPositionVec()).withParameter(LootParameters.THIS_ENTITY, villager).withRandom(villager.getRNG());
                    return loottable.generate(lootcontext$builder.build(LootParameterSets.GIFT));
                } else {
                    return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
                }
            } else {
                return ImmutableList.of(new ItemStack(Items.BONE));
            }
        }
    }
    
}
