package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.entities.goals.WolfAutoAttackTargetGoal;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.RenderMessage;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
            final ItemStack foodItem = TrainingEventHandler.getFoodStack(event.getPlayer());
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
            WolfEntity wolf = (WolfEntity)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            wolf.setCanPickUpLoot(true);
            wolf.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(handler.getWolfSpeed());
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),handler.getWolfType()) );
        }        
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onStartTracking(PlayerEvent.StartTracking event){        
        event.getTarget().getCapability(WolfStatsHandler.CAPABILITY_WOLF_STATS).ifPresent(capability -> {
            WolfEntity wolf = (WolfEntity)event.getTarget();
            PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)event.getPlayer()), new RenderMessage(wolf.getEntityId(),capability.getWolfType()));
            wolf.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(capability.getWolfSpeed());
        });
    }

    @SubscribeEvent
    public void onWolfJump(LivingJumpEvent event){
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            //Scavenger Wolf Bonus
            handler.addXp(WolfStatsEnum.Speed,(handler.getWolfType() == 2 ? 2 : 1));
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),handler.getWolfType()) );
        }
    }
    @SubscribeEvent
    public void onWolfDestroyBlock(LivingDestroyBlockEvent event){
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            handler.addXp(WolfStatsEnum.Strength,1);
            handler.addXp(WolfStatsEnum.Intelligence,(handler.getWolfType() == 3 ? 2 : 1));
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),handler.getWolfType()) );
        }
    }
    @SubscribeEvent
    public void onWolfPickUp(LivingUpdateEvent event){        
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);            
            if(handler.getLevel(WolfStatsEnum.Intelligence) > 4){
                if(wolf.getHeldItemMainhand() != ItemStack.EMPTY && wolf.getOwner() != null){
                    LogManager.getLogger().info(wolf.getHeldItemMainhand());
                    wolf.getOwner().entityDropItem(wolf.getHeldItemMainhand());
                    wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);                
                }
            }
        }
    }
    @SubscribeEvent
    public void AddWolfGoals(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getEntity();
            wolf.targetSelector.addGoal(4, new WolfAutoAttackTargetGoal(wolf,MonsterEntity.class,false));
        }
    }
}