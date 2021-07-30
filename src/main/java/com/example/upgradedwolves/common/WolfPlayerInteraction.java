package com.example.upgradedwolves.common;

import java.util.Optional;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.containers.ContainerProviderWolfInventory;
import com.example.upgradedwolves.entities.goals.FollowOwnerVariableGoal;
import com.example.upgradedwolves.entities.goals.TugOfWarGaol;
import com.example.upgradedwolves.entities.goals.WolfBiasRoamGoal;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;
import com.example.upgradedwolves.items.TugOfWarRopeItem;
import com.example.upgradedwolves.items.GoldenBone.GoldenBoneAbstract;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.RenderMessage;
import com.example.upgradedwolves.items.MobPlushy;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
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
            if(handler.getTugOfWarStatus()){
                wolf.func_233687_w_(true);
                return;
            }
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
                final ItemStack goldenBoneItem = TrainingEventHandler.getPlayerHoldingItemStack(event.getPlayer(), GoldenBoneAbstract.class);
                final ItemStack tugOfWarRopeItem = TrainingEventHandler.getPlayerHoldingItemStack(event.getPlayer(), TugOfWarRopeItem.class);
                if(Thread.currentThread().getName() == "Server thread")
                    PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),WolfStatsHandler.getHandler(wolf).getWolfType()) );
                if(foodItem != null){
                    final ITraining tHandler = TrainingHandler.getHandler(foodItem);
                    final int item = tHandler.getAttribute();                
                    if(item == 0)
                        return;
                    else /*if (handler.getWolfType() != 0)*/{                                  
                        handler.setWolfType(item);
                        handler.addGoals();
                        handler.handleWolfGoals();
                        foodItem.shrink(1);
                        tHandler.resetAttribute();                                        
                    }
                } else if (goldenBoneItem != null){
                    GoldenBoneAbstract goldenBone = (GoldenBoneAbstract)goldenBoneItem.getItem();
                    if(Thread.currentThread().getName() == "Server thread")
                        goldenBone.rightClickWolf(wolf,handler);
                    if(!event.getPlayer().isCreative())
                        goldenBoneItem.shrink(1);
                } else if (tugOfWarRopeItem != null){
                    handler.setRopeHolder(event.getPlayer());
                    wolf.func_233687_w_(true);
                    tugOfWarRopeItem.shrink(1);
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
            handler.addXp(WolfStatsEnum.Speed,(handler.getWolfType() == 2 ? 1 : 0));
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

            Optional<PrioritizedGoal> optGoal = wolf.goalSelector.getRunningGoals().filter((goal) -> {
                return goal.getGoal().getClass() == FollowOwnerGoal.class;
            }).findFirst();
            if(optGoal.isPresent()){
                wolf.goalSelector.removeGoal(optGoal.get().getGoal());
            }
            removeWolfGoal(wolf, WaterAvoidingRandomWalkingGoal.class);

            wolf.setCanPickUpLoot(false);
            for(ItemEntity itementity : wolf.world.getEntitiesWithinAABB(ItemEntity.class, wolf.getBoundingBox().grow(1.0D, 0.0D, 1.0D))) {
                if (wolfInventory.getAvailableSlot(itementity.getItem()) >= 0) {
                    wolf.setCanPickUpLoot(true);
                }
            }

            if(wolf.getHeldItemMainhand() != ItemStack.EMPTY && wolf.getOwner() != null && !handler.getTugOfWarStatus()){
                ItemStack wolfHeldItem = wolf.getHeldItemMainhand();
                if(wolfHeldItem.getItem() instanceof MobPlushy){                    
                    //Nothing happens as this code is left to an entity goal
                } else if(handler.getWolfType() == WolfType.Fighter.getValue() && handler.getLevel(WolfStatsEnum.Intelligence) > 4){                
                    LogManager.getLogger().info(wolfHeldItem);
                    wolf.getOwner().entityDropItem(wolfHeldItem);                    
                    wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
                else{                                        
                    int wolfSlot = wolfInventory.getAvailableSlot(wolfHeldItem);
                    if(wolfSlot >= 0){
                        ItemStack remaining = wolfInventory.insertItem(wolfSlot, wolfHeldItem, false);
                        wolf.setHeldItem(Hand.MAIN_HAND, remaining);
                    }
                    else{
                        wolf.entityDropItem(wolfHeldItem);                    
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
            
            FollowOwnerVariableGoal followOwnerVariableGoal = new FollowOwnerVariableGoal(wolf, 1.0D, 30.0F, 2.0F, false);           
            wolf.goalSelector.addGoal(6, followOwnerVariableGoal);            
            wolf.goalSelector.addGoal(8, new WolfBiasRoamGoal(wolf, 1.0, 30, 5));
            wolf.goalSelector.addGoal(2, new TugOfWarGaol(wolf));
            
            handler.handleWolfGoals();          
        }
    }

    public static Goal getWolfGoal(WolfEntity wolf, Class<?> goalType){
        Optional<PrioritizedGoal> optGoal = wolf.goalSelector.getRunningGoals().filter((goal) -> {
            return goal.getGoal().getClass() == goalType;
        }).findFirst();
        if(optGoal.isPresent()){
            return optGoal.get().getGoal();
        }
        return null;
    }
    public static void removeWolfGoal(WolfEntity wolf, Class<?> goalType){
        Optional<PrioritizedGoal> optGoal = wolf.goalSelector.getRunningGoals().filter((goal) -> {
            return goal.getGoal().getClass() == goalType;
        }).findFirst();
        if(optGoal.isPresent()){
            wolf.goalSelector.removeGoal(optGoal.get().getGoal());
        }
    }
}