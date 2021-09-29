package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.entities.goals.WolfFindAndPickUpItemGoal;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.TrainingItemMessage;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.level.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class TrainingEventHandler {
    @SubscribeEvent
    public void BlockBreak(BreakEvent event){        
        LogManager.getLogger().info("Block Broken" + event.getState().getBlock());
        ItemStack foodItem = getFoodStack(event.getPlayer());
        Block block = event.getState().getBlock();
        if(foodItem == null)
            return;
        //Checks if the player broke a block or harvested food
        // if(block instanceof CropsBlock){
        //     ITraining handler = TrainingHandler.getHandler(foodItem);
        //     handler.setAttribute(3);
        //     if(Thread.currentThread().getName() == "Server thread"){                
        //         PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getPlayer()), new TrainingItemMessage(3, event.getPlayer()getId()));
        //     }
        // }
        else if(block instanceof OreBlock){
            LogManager.getLogger().info("Deep Bug");
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(2);
            if(Thread.currentThread().getName() == "Server thread"){
                PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getPlayer()), new TrainingItemMessage(2, event.getPlayer()getId()));
            }
        }
    }

    @SubscribeEvent
    public void MobKill(LivingDeathEvent event){
        
        if(event.getSource().getTrueSource() instanceof Player){
            Player player = (Player)event.getSource().getTrueSource();
            ItemStack foodItem = getFoodStack(player);
            if(foodItem == null)
                return;
            if(event.getEntity() instanceof MonsterEntity){
                LogManager.getLogger().info("Killed");
                ITraining handler = TrainingHandler.getHandler(foodItem);
                handler.setAttribute(1);                         
                if(Thread.currentThread().getName() == "Server thread"){
                    PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new TrainingItemMessage(1, playergetId()));
                }
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void VillagerInteract(EntityInteract event){
        
        if(event.getTarget() instanceof VillagerEntity){
            LogManager.getLogger().info("Spoke");
            Player player = (Player)event.getPlayer();
            ItemStack foodItem = getFoodStack(player);
            if(foodItem == null)
                return;
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(3);
        }
    }
    
    public static ItemStack getFoodStack(Player player){
        //Checks if the player is holding food in either hand.
        if(player.getHeldItemMainhand().isEdible() && player.getHeldItemMainhand().getItem().getFoodProperties().isMeat())
            return player.getHeldItemMainhand();
        else if(player.getHeldItemOffhand().isEdible() && player.getHeldItemOffhand().getItem().getFoodProperties().isMeat())
            return player.getHeldItemOffhand();
        return null;
    }
    public static ItemStack getPlayerHoldingItemStack(Player player, Class<? extends Item> item){
        //Checks if the player is holding food in either hand.
        if(item.isInstance(player.getHeldItemMainhand().getItem()))
            return player.getHeldItemMainhand();
        else if(item.isInstance(player.getHeldItemOffhand().getItem()))
            return player.getHeldItemOffhand();
        return null;
    }

    public static void wolfCollectEntity(Entity entity, Wolf wolf,ItemStack item){
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);        
        int wolfSlot = handler.getInventory().getAvailableSlot(item);
        if(wolfSlot >= 0){
            handler.getInventory().insertItem(wolfSlot, item, false);
            WolfFindAndPickUpItemGoal goal = (WolfFindAndPickUpItemGoal)WolfPlayerInteraction.getWolfGoal(wolf, WolfFindAndPickUpItemGoal.class);
            if(goal != null){
                goal.setEndPoint(wolf.getPositionVec());
            }
            entity.remove();
        }
    }
}