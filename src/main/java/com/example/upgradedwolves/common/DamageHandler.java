package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageHandler {
    //When the entity is attacked by a wolf
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onEntityHurt(LivingHurtEvent event) {
        if(event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity() instanceof Wolf){
            Wolf wolf = (Wolf)event.getSource().getDirectEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            event.setAmount(handler.getWolfStrength());                                          
            handler.addXp(WolfStatsEnum.Strength, handler.getWolfType() == WolfType.Fighter.getValue() ?
            3 :
            2);
            if(event.getEntity() instanceof Monster)
                handler.addXp(WolfStatsEnum.Strength, 2);           
        }
    }
    @SubscribeEvent
    public void onWolfKill(LivingDeathEvent event){
        if(event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity() instanceof Wolf){
            Wolf wolf = (Wolf)event.getSource().getDirectEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);                                        
            handler.addXp(WolfStatsEnum.Intelligence, 1);
            handler.addXp(WolfStatsEnum.Speed,2);
            wolf.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(handler.getWolfSpeed());
            LogManager.getLogger().info("Wolf intelligence Increased:" + handler.getXp(WolfStatsEnum.Intelligence) + " xp, lvl: " + handler.getLevel(WolfStatsEnum.Intelligence));
            if(wolf.getMainHandItem() != null){
                if(wolf.getOwner() != null){
                    wolf.getOwner().spawnAtLocation(wolf.getMainHandItem());
                } else {
                    wolf.spawnAtLocation(wolf.getMainHandItem());
                }
                wolf.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);                
            }
        }
        //If the wolf dies
        else if(event.getEntity() instanceof Wolf){
            Wolf wolf = (Wolf)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            WolfItemStackHandler wolfInventory = handler.getInventory();
            for(int i = 0; i < wolfInventory.getSlots(); i++){
                wolf.spawnAtLocation(wolfInventory.getStackInSlot(i));
            }
        }
    }
}