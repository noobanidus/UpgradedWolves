package com.example.upgradedwolves.screens;

import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.init.ModContainers;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WolfContainer extends Container {

    public WolfEntity wolf;
    public IItemHandler wolfItemHandler;
    private IWorldPosCallable canInteractWithCallable;    

    public WolfContainer(int id, PlayerInventory playerInventory,WolfEntity wolfEntity) {
        super(ModContainers.WOLF_CONTAINER.get(),id);
        wolf = wolfEntity;
        wolfItemHandler = wolf.getCapability(WolfStatsHandler.CAPABILITY_WOLF_STATS).orElse(null).getInventory();
        //canInteractWithCallable = IWorldPosCallable.of(wolf.getEntityWorld(),wolf.getPositionVec())
        int startX = 16;
        int startY = 36;
        //The delta X and delta Y are the same
        int delta = 36;
        //Wolf Inventory
        for(int i = 0; i < 27; i++){
            this.addSlot(new SlotItemHandler(wolfItemHandler,i,startX + delta * (i % 9),startY + delta * (i/9)));
        }
        startY = 170;
        //Player Inventory
        for(int i = 0; i < 27; i++){
            this.addSlot(new Slot(playerInventory,9 + i,startX + delta * (i % 9),startY + delta * (i/9)));
        }
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory,i,startX + delta * (i % 9),286));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        
        return false;
    }
    
}