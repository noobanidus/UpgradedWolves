package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ContainerProviderWolfInventory implements INamedContainerProvider {
    private Wolf wolf;
    private WolfItemStackHandler wolfHandler ;
    public ContainerProviderWolfInventory(Wolf wolf,WolfItemStackHandler wolfHandler){
        this.wolf = wolf;
        this.wolfHandler = wolfHandler;
    }

    @Override
    public ITextComponent getDisplayName(){
        return new StringTextComponent("Inventory");
    }

    @Override
    public WolfContainer createMenu(int windowId, PlayerInventory inventory, Player player){
        
        WolfContainer wolfContainerServerSide = 
            WolfContainer.createContainerServerSide(windowId, inventory, wolfHandler, wolf);
        return wolfContainerServerSide;
    }
}
