package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

public class ContainerProviderWolfInventory implements MenuProvider {
    private Wolf wolf;
    private WolfItemStackHandler wolfHandler ;
    public ContainerProviderWolfInventory(Wolf wolf,WolfItemStackHandler wolfHandler){
        this.wolf = wolf;
        this.wolfHandler = wolfHandler;
    }

    @Override
    public Component getDisplayName(){
        return Component.translatable("chestScreen.header.player");
    }

    @Override
    public WolfContainer createMenu(int windowId, Inventory inventory, Player player){
        
        WolfContainer wolfContainerServerSide = 
            WolfContainer.createContainerServerSide(windowId, inventory, wolfHandler, wolf);
        return wolfContainerServerSide;
    }
}
