package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.RenderMessage;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class WolfPlayerInteraction {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityInteract(final EntityInteract event){
        //LogManager.getLogger().info(event.getEntity().toString());
        if(event.getTarget() instanceof WolfEntity){
            final WolfEntity wolf = (WolfEntity) event.getTarget();            
            final IWolfStats handler = WolfStatsHandler.getHandler(wolf);            
            LogManager.getLogger().info(handler.getLevel(WolfStatsEnum.Love));
            LogManager.getLogger().info(handler.getWolfType());
            handler.InitLove();       
            final ItemStack foodItem = TrainingTreatHandler.getFoodStack(event.getPlayer());
            if(Thread.currentThread().getName() == "Server thread")
                PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),WolfStatsHandler.getHandler(wolf).getWolfType()) );
            if(foodItem != null){
                final ITraining tHandler = TrainingHandler.getHandler(foodItem);
                final int item = tHandler.getAttribute();                
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

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onEntitySpawn(LivingSpawnEvent event) {
        if(event.getEntity() instanceof WolfEntity){                
            final WolfEntity wolf = (WolfEntity)event.getEntity();
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),WolfStatsHandler.getHandler(wolf).getWolfType()) );
        }        
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onStartTracking(PlayerEvent.StartTracking event){        
        event.getTarget().getCapability(WolfStatsHandler.CAPABILITY_WOLF_STATS).ifPresent(capability -> {
            PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)event.getPlayer()), new RenderMessage(event.getTarget().getEntityId(),capability.getWolfType()));
        });
    }
}