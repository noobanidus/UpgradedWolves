package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageHandler {
    //When the entity is attacked by a wolf
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onEntityHurt(LivingHurtEvent event) {
        if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getSource().getTrueSource();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            event.setAmount(handler.getWolfStrength());                                          
            handler.addXp(WolfStatsEnum.Strength, 2);
            if(event.getEntity() instanceof MonsterEntity)
                handler.addXp(WolfStatsEnum.Strength, 2);            
        }
    }
    @SubscribeEvent
    public void onWolfKill(LivingDeathEvent event){
        if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getSource().getTrueSource();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);                                        
            handler.addXp(WolfStatsEnum.Intelligence, 1);
            handler.addXp(WolfStatsEnum.Speed,2);
            wolf.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(handler.getWolfSpeed());
            LogManager.getLogger().info("Wolf intelligence Increased:" + handler.getXp(WolfStatsEnum.Intelligence) + " xp, lvl: " + handler.getLevel(WolfStatsEnum.Intelligence));
            if(wolf.getHeldItemMainhand() != null){
                wolf.getOwner().entityDropItem(wolf.getHeldItemMainhand());
                wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);                
            }
        }
    }
}