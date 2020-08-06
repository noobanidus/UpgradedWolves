package com.example.upgradedwolves.common;

import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.TrainingItemMessage;

import org.apache.logging.log4j.LogManager;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class TrainingEventHandler {
    @SubscribeEvent
    public void BlockBreak(BreakEvent event){        
        LogManager.getLogger().info("Block Broken" + event.getState().getBlock());
        ItemStack foodItem = getFoodStack(event.getPlayer());
        Block block = event.getState().getBlock();
        if(foodItem == null)
            return;
        //Checks if the player broke a block or harvested food
        if(block instanceof CropsBlock){
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(3);
            if(Thread.currentThread().getName() == "Server thread"){                
                PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)event.getPlayer()), new TrainingItemMessage(3, event.getPlayer().getEntityId()));
            }
        }
        else if(block instanceof OreBlock){
            LogManager.getLogger().info("Deep Bug");
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(2);
            if(Thread.currentThread().getName() == "Server thread"){
                PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)event.getPlayer()), new TrainingItemMessage(2, event.getPlayer().getEntityId()));
            }
        }
    }

    @SubscribeEvent
    public void MobKill(LivingDeathEvent event){
        
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
            ItemStack foodItem = getFoodStack(player);
            if(foodItem == null)
                return;
            if(event.getEntity() instanceof MonsterEntity){
                LogManager.getLogger().info("Killed");
                ITraining handler = TrainingHandler.getHandler(foodItem);
                handler.setAttribute(1);                
                if(Thread.currentThread().getName() == "Server thread"){
                    PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new TrainingItemMessage(1, player.getEntityId()));
                }
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void VillagerInteract(EntityInteract event){
        
        if(event.getTarget() instanceof VillagerEntity){
            LogManager.getLogger().info("Spoke");
            PlayerEntity player = (PlayerEntity)event.getPlayer();
            ItemStack foodItem = getFoodStack(player);
            if(foodItem == null)
                return;
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(4);
        }
    }
    
    public static ItemStack getFoodStack(PlayerEntity player){
        //Checks if the player is holding food in either hand.
        if(player.getHeldItemMainhand().isFood() && player.getHeldItemMainhand().getItem().getFood().isMeat())
            return player.getHeldItemMainhand();
        else if(player.getHeldItemOffhand().isFood() && player.getHeldItemOffhand().getItem().getFood().isMeat())
            return player.getHeldItemOffhand();
        return null;
    }
}