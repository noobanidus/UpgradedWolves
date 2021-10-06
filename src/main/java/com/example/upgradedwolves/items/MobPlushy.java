package com.example.upgradedwolves.items;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.plushy.CreeperPlushyModel;
import com.example.upgradedwolves.entities.plushy.MobPlushyEntity;
import com.example.upgradedwolves.entities.plushy.SkeletonPlushyModel;
import com.example.upgradedwolves.entities.plushy.ZombiePlushyModel;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MobPlushy extends Item {
    public MobPlushyType plushType;

    public MobPlushy(String registryName, MobPlushyType type) {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)/*.setISTER(TilePlushyEntity::new)*/);
        this.setRegistryName(UpgradedWolves.getId(registryName));
        plushType = type;
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {        
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);              
        if (!worldIn.isClientSide) {
            MobPlushyEntity mobPlushyEntity = new MobPlushyEntity(worldIn, playerIn);
            mobPlushyEntity.setItem(itemstack);
            mobPlushyEntity.setOwner(playerIn);
            mobPlushyEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(mobPlushyEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.isCreative()) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.consume(itemstack);
    }
    public static EntityModel<Entity> getModelByPlushType(MobPlushy plushItem, EntityRendererProvider.Context rendererManager){
        switch(plushItem.plushType){
            case ZOMBIE:
            return new ZombiePlushyModel(rendererManager.bakeLayer(ModEntities.ZOMBIE_PLUSH));
            case SKELETON:
            return new SkeletonPlushyModel(rendererManager.bakeLayer(ModEntities.SKELETON_PLUSH));
            case CREEPER:
            return new CreeperPlushyModel(rendererManager.bakeLayer(ModEntities.CREEPER_PLUSH));
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
