package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.plushy.MobPlushyEntity;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MobPlushy extends Item {
    public MobPlushyType plushType;

    public MobPlushy(String registryName, MobPlushyType type) {
        super(new Item.Properties().group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId(registryName));
        plushType = type;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {        
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);              
        if (!worldIn.isRemote) {
            MobPlushyEntity mobPlushyEntity = new MobPlushyEntity(worldIn, playerIn);
            mobPlushyEntity.setItem(itemstack);
            mobPlushyEntity.setShooter(playerIn);
            mobPlushyEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 1.0F);
            worldIn.addEntity(mobPlushyEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        return ActionResult.resultConsume(itemstack);
    }
}
