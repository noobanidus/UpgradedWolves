package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.plushy.CreeperPlushyModel;
import com.example.upgradedwolves.entities.plushy.MobPlushyEntity;
import com.example.upgradedwolves.entities.plushy.SkeletonPlushyModel;
import com.example.upgradedwolves.entities.plushy.TilePlushyEntity;
import com.example.upgradedwolves.entities.plushy.ZombiePlushyModel;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MobPlushy extends Item {
    public MobPlushyType plushType;

    public MobPlushy(String registryName, MobPlushyType type) {
        super(new Item.Properties().stacksTo(1).group(ItemGroup.MISC).setISTER(TilePlushyEntity::new));
        this.setRegistryName(UpgradedWolves.getId(registryName));
        plushType = type;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, InteractionHand handIn) {        
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);              
        if (!worldIn.isRemote) {
            MobPlushyEntity mobPlushyEntity = new MobPlushyEntity(worldIn, playerIn);
            mobPlushyEntity.setItem(itemstack);
            mobPlushyEntity.setShooter(playerIn);
            mobPlushyEntity.func_234612_a_(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(mobPlushyEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        return ActionResult.resultConsume(itemstack);
    }
    public static EntityModel<Entity> getModelByPlushType(MobPlushy plushItem){
        switch(plushItem.plushType){
            case ZOMBIE:
            return new ZombiePlushyModel();
            case SKELETON:
            return new SkeletonPlushyModel();
            case CREEPER:
            return new CreeperPlushyModel();
        }
        return null;
    }
    public static ResourceLocation getPlushTexture(MobPlushy plushItem){
        switch(plushItem.plushType){
            case SKELETON:            
            return UpgradedWolves.getId("textures/entity/skeleton_plush.png");
            case ZOMBIE:         
            return UpgradedWolves.getId("textures/entity/zombie_plush.png");
            case CREEPER:        
            return UpgradedWolves.getId("textures/entity/creeper_plush.png");
        }
        return null;
    }
}
