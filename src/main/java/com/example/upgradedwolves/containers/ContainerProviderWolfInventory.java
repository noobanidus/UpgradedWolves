package com.example.upgradedwolves.containers;

import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ContainerProviderWolfInventory implements INamedContainerProvider {
    private WolfEntity wolf;
    private ItemStackHandlerWolf wolfHandler ;
    public ContainerProviderWolfInventory(WolfEntity wolf,ItemStackHandlerWolf wolfHandler){
        this.wolf = wolf;
        this.wolfHandler = wolfHandler;
    }

    @Override
    public ITextComponent getDisplayName(){
        return new StringTextComponent("Inventory");
    }

    @Override
    public WolfContainer createMenu(int windowId, PlayerInventory inventory, PlayerEntity player){
        
        WolfContainer wolfContainerServerSide = 
            WolfContainer.createContainerServerSide(windowId, inventory, wolfHandler, wolf);
        return wolfContainerServerSide;
    }
}
