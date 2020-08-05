package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WolfPlayerInteraction {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityInteract(EntityInteract event){
        //LogManager.getLogger().info(event.getEntity().toString());
        if(event.getTarget() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity) event.getTarget();            
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);            
            LogManager.getLogger().info(handler.getLevel(WolfStatsEnum.Love));
            LogManager.getLogger().info(handler.getWolfType());
            //handler.setWolfType(0);            
            ItemStack foodItem = TrainingTreatHandler.getFoodStack(event.getPlayer());
            if(foodItem != null){
                ITraining tHandler = TrainingHandler.getHandler(foodItem);
                int item = tHandler.getAttribute();                
                if(item == 0)
                    return;
                else /*if (handler.getWolfType() != 0)*/{                
                    handler.setWolfType(item);
                    foodItem.shrink(1);
                    tHandler.resetAttribute();                    
                    
                }
            }
        }
    }
}