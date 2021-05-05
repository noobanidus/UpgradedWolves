package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.containers.ContainerProviderWolfInventory;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.RenderMessage;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

public class WolfPlayerInteraction {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityInteract(final EntityInteract event){
        //LogManager.getLogger().info(event.getEntity().toString());
        if(event.getTarget() instanceof WolfEntity){
            final WolfEntity wolf = (WolfEntity) event.getTarget();            
            final IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            if(wolf.getOwner() == event.getPlayer() && event.getPlayer().isCrouching()){
                if(Thread.currentThread().getName() == "Server thread"){
                    INamedContainerProvider wolfInventory = new ContainerProviderWolfInventory(wolf,handler.getInventory());
                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putInt("strLevel", handler.getLevel(WolfStatsEnum.Strength));                    
                    nbt.putInt("spdLevel", handler.getLevel(WolfStatsEnum.Speed));
                    nbt.putInt("intLevel", handler.getLevel(WolfStatsEnum.Intelligence));
                    nbt.putFloat("strNum", handler.getStatRatio(WolfStatsEnum.Strength));
                    nbt.putFloat("spdNum", handler.getStatRatio(WolfStatsEnum.Speed));
                    nbt.putFloat("intNum", handler.getStatRatio(WolfStatsEnum.Intelligence));
                    NetworkHooks.openGui((ServerPlayerEntity)event.getPlayer(),
                        wolfInventory,
                        (packetBuffer) ->{packetBuffer.writeInt(handler.getInventory().getSlots());packetBuffer.writeInt(wolf.getEntityId());packetBuffer.writeCompoundTag(nbt);}
                    );
                }
                wolf.func_233687_w_(!wolf.isSitting());
            }
            else{
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
                        handler.handleWolfGoals();
                        foodItem.shrink(1);
                        tHandler.resetAttribute();                                        
                    }
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
            ItemStackHandlerWolf wolfInventory = handler.getInventory();

            wolf.setCanPickUpLoot(false);
            for(ItemEntity itementity : wolf.world.getEntitiesWithinAABB(ItemEntity.class, wolf.getBoundingBox().grow(1.0D, 0.0D, 1.0D))) {
                if (wolfInventory.getAvailableSlot(itementity.getItem()) >= 0) {
                    wolf.setCanPickUpLoot(true);
                }
            }

            if(wolf.getHeldItemMainhand() != ItemStack.EMPTY && wolf.getOwner() != null){
                if(handler.getWolfType() == WolfType.Fighter.getValue() && handler.getLevel(WolfStatsEnum.Intelligence) > 4){                
                    LogManager.getLogger().info(wolf.getHeldItemMainhand());
                    wolf.getOwner().entityDropItem(wolf.getHeldItemMainhand());                    
                    wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
                else{                                        
                    int wolfSlot = wolfInventory.getAvailableSlot(wolf.getHeldItemMainhand());
                    if(wolfSlot >= 0){
                        ItemStack remaining = wolfInventory.insertItem(wolfSlot, wolf.getHeldItemMainhand(), false);
                        wolf.setHeldItem(Hand.MAIN_HAND, remaining);
                    }
                    else{
                        wolf.entityDropItem(wolf.getHeldItemMainhand());                    
                        wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void AddWolfGoals(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getEntity();
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);

            handler.handleWolfGoals();          
        }
    }
}